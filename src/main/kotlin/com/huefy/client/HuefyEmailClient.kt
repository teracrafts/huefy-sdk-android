package com.huefy.client

import com.huefy.config.HuefyConfig
import com.huefy.errors.ErrorCode
import com.huefy.errors.HuefyException
import com.huefy.models.*
import com.huefy.security.Security
import com.huefy.validators.EmailValidators
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
    }

    /**
     * Sends an email using a template.
     *
     * @param request the email request containing templateKey, data, recipient, and optional provider
     * @return the send email response
     */
    suspend fun sendEmail(request: SendEmailRequest): SendEmailResponse {
        val errors = EmailValidators.validateSendEmailInput(request.templateKey, request.data, request.recipient)
        if (errors.isNotEmpty()) {
            throw HuefyException(
                message = "Validation failed: ${errors.joinToString("; ")}",
                errorCode = ErrorCode.VALIDATION_ERROR,
                recoverable = false
            )
        }

        for ((key, value) in request.data) {
            val piiTypes = Security.detectPii(value)
            if (piiTypes.isNotEmpty()) {
                logger.warning("Potential PII detected in template data field '$key': $piiTypes.")
            }
        }

        return try {
            val body = buildJsonObject {
                put("templateKey", request.templateKey.trim())
                put("recipient", request.recipient.trim())
                putJsonObject("data") {
                    request.data.forEach { (key, value) -> put(key, value) }
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

        request.recipients.forEachIndexed { i, r ->
            val emailErr = EmailValidators.validateEmail(r.email)
            if (emailErr != null) {
                throw HuefyException(
                    message = "recipients[$i]: $emailErr",
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
                            put("email", r.email)
                            r.type?.let { put("type", it) }
                            r.data?.let { d ->
                                putJsonObject("data") {
                                    d.forEach { (k, v) -> put(k, v) }
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

            SendBulkEmailsResponse(
                success = response["success"]?.jsonPrimitive?.booleanOrNull ?: false,
                data = SendBulkEmailsResponseData(
                    batchId = dataNode["batchId"]?.jsonPrimitive?.contentOrNull ?: "",
                    status = dataNode["status"]?.jsonPrimitive?.contentOrNull ?: "",
                    templateKey = dataNode["templateKey"]?.jsonPrimitive?.contentOrNull ?: "",
                    totalRecipients = dataNode["totalRecipients"]?.jsonPrimitive?.intOrNull ?: 0,
                    processedCount = dataNode["processedCount"]?.jsonPrimitive?.intOrNull ?: 0,
                    successCount = dataNode["successCount"]?.jsonPrimitive?.intOrNull ?: 0,
                    failureCount = dataNode["failureCount"]?.jsonPrimitive?.intOrNull ?: 0,
                    suppressedCount = dataNode["suppressedCount"]?.jsonPrimitive?.intOrNull ?: 0,
                    startedAt = dataNode["startedAt"]?.jsonPrimitive?.contentOrNull ?: "",
                    completedAt = dataNode["completedAt"]?.jsonPrimitive?.contentOrNull,
                    recipients = recipientsList,
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
