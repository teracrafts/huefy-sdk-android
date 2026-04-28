package com.teracrafts.huefy.client

import com.teracrafts.huefy.config.HuefyConfig
import com.teracrafts.huefy.errors.ErrorCode
import com.teracrafts.huefy.errors.HuefyException
import com.teracrafts.huefy.models.*
import com.teracrafts.huefy.security.Security
import com.teracrafts.huefy.validators.EmailValidators
import kotlinx.serialization.json.*
import java.util.logging.Logger

/**
 * Email-focused client for the Huefy SDK.
 *
 * Extends [HuefyClient] with email-specific operations including
 * single and bulk email sending with input validation.
 *
 * Usage:
 * ```kotlin
 * val client = HuefyEmailClient(HuefyConfig(apiKey = "your-api-key"))
 *
 * // Send single email
 * val response = client.sendEmail(SendEmailRequest(
 *     templateKey = "welcome",
 *     data = mapOf("name" to "John"),
 *     recipient = "john@example.com",
 * ))
 *
 * // Bulk emails with shared template
 * val result = client.sendBulkEmails(SendBulkEmailsRequest(
 *     templateKey = "welcome",
 *     recipients = listOf(
 *         BulkRecipient(email = "alice@example.com", data = mapOf("name" to "Alice")),
 *         BulkRecipient(email = "bob@example.com", data = mapOf("name" to "Bob")),
 *     ),
 * ))
 *
 * client.close()
 * ```
 */
class HuefyEmailClient(config: HuefyConfig) : HuefyClient(config) {

    companion object {
        private const val EMAILS_SEND_PATH = "/emails/send"
        private const val EMAILS_SEND_BULK_PATH = "/emails/send-bulk"
        private val logger = Logger.getLogger(HuefyEmailClient::class.java.name)

        private fun JsonObjectBuilder.putDynamic(key: String, value: Any?) {
            put(key, value.toJsonElement())
        }

        private fun Any?.toJsonElement(): JsonElement = when (this) {
            null -> JsonNull
            is JsonElement -> this
            is String -> JsonPrimitive(this)
            is Number -> JsonPrimitive(this)
            is Boolean -> JsonPrimitive(this)
            is Map<*, *> -> buildJsonObject {
                this@toJsonElement.forEach { (nestedKey, nestedValue) ->
                    if (nestedKey != null) {
                        put(nestedKey.toString(), nestedValue.toJsonElement())
                    }
                }
            }
            is Iterable<*> -> buildJsonArray {
                this@toJsonElement.forEach { add(it.toJsonElement()) }
            }
            is Array<*> -> buildJsonArray {
                this@toJsonElement.forEach { add(it.toJsonElement()) }
            }
            else -> JsonPrimitive(this.toString())
        }
    }

    /**
     * Sends an email using a template.
     *
     * @param request the email request containing templateKey, data, recipient, and optional provider
     * @return the send email response
     */
    suspend fun sendEmail(request: SendEmailRequest): SendEmailResponse {
        val errors = when {
            request.recipientObject != null ->
                EmailValidators.validateSendEmailRecipientInput(request.templateKey, request.data, request.recipientObject)
            request.recipient != null ->
                EmailValidators.validateSendEmailInput(request.templateKey, request.data, request.recipient)
            else -> listOf("Recipient email is required")
        }
        if (errors.isNotEmpty()) {
            throw HuefyException(
                message = "Validation failed: ${errors.joinToString("; ")}",
                errorCode = ErrorCode.VALIDATION_ERROR,
                recoverable = false
            )
        }

        for ((key, value) in request.data) {
            val piiTypes = Security.detectPii(
                if (value is String) value else Json.encodeToString(JsonElement.serializer(), value.toJsonElement())
            )
            if (piiTypes.isNotEmpty()) {
                logger.warning("Potential PII detected in template data field '$key': $piiTypes.")
            }
        }
        request.recipientObject?.data?.forEach { (key, value) ->
            val piiTypes = Security.detectPii(
                if (value is String) value else Json.encodeToString(JsonElement.serializer(), value.toJsonElement())
            )
            if (piiTypes.isNotEmpty()) {
                logger.warning("Potential PII detected in recipient data field '$key': $piiTypes.")
            }
        }

        return try {
            val body = buildJsonObject {
                put("templateKey", request.templateKey.trim())
                val recipientObject = request.recipientObject
                if (recipientObject != null) {
                    putJsonObject("recipient") {
                        put("email", recipientObject.email.trim())
                        recipientObject.type?.takeIf { it.isNotBlank() }?.let { put("type", it.trim().lowercase()) }
                        recipientObject.data?.let { recipientData ->
                            putJsonObject("data") {
                                recipientData.forEach { (key, value) -> putDynamic(key, value) }
                            }
                        }
                    }
                } else {
                    val recipient = request.recipient
                        ?: throw HuefyException(
                            message = "Recipient email is required",
                            errorCode = ErrorCode.VALIDATION_ERROR,
                            recoverable = false
                        )
                    put("recipient", recipient.trim())
                }
                putJsonObject("data") {
                    request.data.forEach { (key, value) -> putDynamic(key, value) }
                }
                request.provider?.let { put("providerType", it.value) }
            }

            val response = request("POST", EMAILS_SEND_PATH, body)

            val dataNode = response["data"]?.jsonObject
                ?: throw HuefyException.networkError("Missing data in response", null)

            val recipientsList = dataNode["recipients"]?.jsonArray?.map { r ->
                val rObj = r.jsonObject
                RecipientStatus(
                    email = rObj["email"]?.jsonPrimitive?.contentOrNull ?: "",
                    status = rObj["status"]?.jsonPrimitive?.contentOrNull ?: "",
                    messageId = rObj["messageId"]?.jsonPrimitive?.contentOrNull,
                    error = rObj["error"]?.jsonPrimitive?.contentOrNull,
                    sentAt = rObj["sentAt"]?.jsonPrimitive?.contentOrNull,
                )
            } ?: emptyList()

            SendEmailResponse(
                success = response["success"]?.jsonPrimitive?.booleanOrNull ?: false,
                data = SendEmailResponseData(
                    emailId = dataNode["emailId"]?.jsonPrimitive?.contentOrNull ?: "",
                    status = dataNode["status"]?.jsonPrimitive?.contentOrNull ?: "",
                    recipients = recipientsList,
                    scheduledAt = dataNode["scheduledAt"]?.jsonPrimitive?.contentOrNull,
                    sentAt = dataNode["sentAt"]?.jsonPrimitive?.contentOrNull,
                ),
                correlationId = response["correlationId"]?.jsonPrimitive?.contentOrNull ?: "",
            )
        } catch (e: HuefyException) {
            throw e
        } catch (e: Exception) {
            throw HuefyException.networkError("Failed to send email: ${e.message}", e)
        }
    }

    /**
     * Sends multiple emails in bulk using a shared template.
     *
     * @param request the bulk email request containing templateKey, recipients, and optional provider
     * @return the bulk send response
     */
    suspend fun sendBulkEmails(request: SendBulkEmailsRequest): SendBulkEmailsResponse {
        val countError = EmailValidators.validateBulkCount(request.recipients.size)
        if (countError != null) {
            throw HuefyException(
                message = countError,
                errorCode = ErrorCode.VALIDATION_ERROR,
                recoverable = false
            )
        }

        val templateError = EmailValidators.validateTemplateKey(request.templateKey)
        if (templateError != null) {
            throw HuefyException(
                message = templateError,
                errorCode = ErrorCode.VALIDATION_ERROR,
                recoverable = false
            )
        }

        request.recipients.forEachIndexed { i, r ->
            val recipientErr = EmailValidators.validateBulkRecipient(r)
            if (recipientErr != null) {
                throw HuefyException(
                    message = "recipients[$i]: $recipientErr",
                    errorCode = ErrorCode.VALIDATION_ERROR,
                    recoverable = false
                )
            }
        }

        return try {
            val body = buildJsonObject {
                put("templateKey", request.templateKey.trim())
                putJsonArray("recipients") {
                    request.recipients.forEach { r ->
                        addJsonObject {
                            put("email", r.email.trim())
                            r.type?.trim()?.takeIf(String::isNotEmpty)?.lowercase()?.let { put("type", it) }
                            r.data?.let { d ->
                                putJsonObject("data") {
                                    d.forEach { (k, v) -> putDynamic(k, v) }
                                }
                            }
                        }
                    }
                }
                request.provider?.let { put("providerType", it.value) }
            }

            val response = request("POST", EMAILS_SEND_BULK_PATH, body)

            val dataNode = response["data"]?.jsonObject
                ?: throw HuefyException.networkError("Missing data in response", null)

            val recipientsList = dataNode["recipients"]?.jsonArray?.map { r ->
                val rObj = r.jsonObject
                RecipientStatus(
                    email = rObj["email"]?.jsonPrimitive?.contentOrNull ?: "",
                    status = rObj["status"]?.jsonPrimitive?.contentOrNull ?: "",
                    messageId = rObj["messageId"]?.jsonPrimitive?.contentOrNull,
                    error = rObj["error"]?.jsonPrimitive?.contentOrNull,
                    sentAt = rObj["sentAt"]?.jsonPrimitive?.contentOrNull,
                )
            } ?: emptyList()
            val errors = dataNode["errors"]?.jsonArray?.map { errorNode ->
                val errorObj = errorNode.jsonObject
                EmailError(
                    code = errorObj["code"]?.jsonPrimitive?.contentOrNull ?: "",
                    message = errorObj["message"]?.jsonPrimitive?.contentOrNull ?: "",
                    recipient = errorObj["recipient"]?.jsonPrimitive?.contentOrNull,
                    details = errorObj["details"]?.jsonObject?.toMap(),
                )
            } ?: emptyList()

            SendBulkEmailsResponse(
                success = response["success"]?.jsonPrimitive?.booleanOrNull ?: false,
                data = SendBulkEmailsResponseData(
                    batchId = dataNode["batchId"]?.jsonPrimitive?.contentOrNull ?: "",
                    status = dataNode["status"]?.jsonPrimitive?.contentOrNull ?: "",
                    templateKey = dataNode["templateKey"]?.jsonPrimitive?.contentOrNull ?: "",
                    templateVersion = dataNode["templateVersion"]?.jsonPrimitive?.intOrNull ?: 0,
                    senderUsed = dataNode["senderUsed"]?.jsonPrimitive?.contentOrNull ?: "",
                    senderVerified = dataNode["senderVerified"]?.jsonPrimitive?.booleanOrNull ?: false,
                    totalRecipients = dataNode["totalRecipients"]?.jsonPrimitive?.intOrNull ?: 0,
                    processedCount = dataNode["processedCount"]?.jsonPrimitive?.intOrNull ?: 0,
                    successCount = dataNode["successCount"]?.jsonPrimitive?.intOrNull ?: 0,
                    failureCount = dataNode["failureCount"]?.jsonPrimitive?.intOrNull ?: 0,
                    suppressedCount = dataNode["suppressedCount"]?.jsonPrimitive?.intOrNull ?: 0,
                    startedAt = dataNode["startedAt"]?.jsonPrimitive?.contentOrNull ?: "",
                    completedAt = dataNode["completedAt"]?.jsonPrimitive?.contentOrNull,
                    recipients = recipientsList,
                    errors = errors,
                    metadata = dataNode["metadata"]?.jsonObject?.toMap(),
                ),
                correlationId = response["correlationId"]?.jsonPrimitive?.contentOrNull ?: "",
            )
        } catch (e: HuefyException) {
            throw e
        } catch (e: Exception) {
            throw HuefyException.networkError("Failed to send bulk emails: ${e.message}", e)
        }
    }
}
