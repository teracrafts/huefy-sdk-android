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
 * val response = client.sendEmail("welcome", mapOf("name" to "John"), "john@example.com")
 *
 * // Send with provider
 * val response = client.sendEmail("welcome", mapOf("name" to "John"), "john@example.com", EmailProvider.SENDGRID)
 *
 * // Bulk emails
 * val requests = listOf(
 *     SendEmailRequest("welcome", "alice@example.com", mapOf("name" to "Alice")),
 *     SendEmailRequest("welcome", "bob@example.com", mapOf("name" to "Bob"))
 * )
 * val results = client.sendBulkEmails(requests)
 *
 * client.close()
 * ```
 */
class HuefyEmailClient(config: HuefyConfig) : HuefyClient(config) {

    companion object {
        private const val EMAILS_SEND_PATH = "/emails/send"
        private val logger = Logger.getLogger(HuefyEmailClient::class.java.name)
    }

    /**
     * Sends an email using the default provider (SES).
     *
     * @param templateKey the template key to use
     * @param data the template data variables
     * @param recipient the recipient email address
     * @return the send email response
     * @throws HuefyException if validation fails or the request fails
     */
    suspend fun sendEmail(
        templateKey: String,
        data: Map<String, String>,
        recipient: String
    ): SendEmailResponse {
        return sendEmail(templateKey, data, recipient, null)
    }

    /**
     * Sends an email using the specified provider.
     *
     * @param templateKey the template key to use
     * @param data the template data variables
     * @param recipient the recipient email address
     * @param provider the email provider (null for default SES)
     * @return the send email response
     * @throws HuefyException if validation fails or the request fails
     */
    suspend fun sendEmail(
        templateKey: String,
        data: Map<String, String>,
        recipient: String,
        provider: EmailProvider? = null
    ): SendEmailResponse {
        // Validate input
        val errors = EmailValidators.validateSendEmailInput(templateKey, data, recipient)
        if (errors.isNotEmpty()) {
            throw HuefyException(
                message = "Validation failed: ${errors.joinToString("; ")}",
                errorCode = ErrorCode.VALIDATION_ERROR,
                recoverable = false
            )
        }

        // Check template data for PII and warn (matching Go SDK behavior)
        for ((key, value) in data) {
            val piiTypes = Security.detectPii(value)
            if (piiTypes.isNotEmpty()) {
                logger.warning("Potential PII detected in template data field '$key': $piiTypes. " +
                    "Consider removing or encrypting these fields.")
            }
        }

        return try {
            // Build request body
            val body = buildJsonObject {
                put("template_key", templateKey.trim())
                put("recipient", recipient.trim())
                putJsonObject("data") {
                    data.forEach { (key, value) -> put(key, value) }
                }
                provider?.let { put("provider", it.value) }
            }

            val response = request("POST", EMAILS_SEND_PATH, body)

            SendEmailResponse(
                success = response["success"]?.jsonPrimitive?.booleanOrNull ?: false,
                message = response["message"]?.jsonPrimitive?.contentOrNull ?: "",
                messageId = response["message_id"]?.jsonPrimitive?.contentOrNull ?: "",
                provider = response["provider"]?.jsonPrimitive?.contentOrNull ?: ""
            )
        } catch (e: HuefyException) {
            throw e
        } catch (e: Exception) {
            throw HuefyException.networkError("Failed to send email: ${e.message}", e)
        }
    }

    /**
     * Sends multiple emails in bulk.
     *
     * Each request is sent independently. Failures for individual emails
     * do not prevent remaining emails from being sent.
     *
     * @param requests the list of email requests to send
     * @return a list of results for each email
     * @throws HuefyException if the bulk count validation fails
     */
    suspend fun sendBulkEmails(requests: List<SendEmailRequest>): List<BulkEmailResult> {
        val countError = EmailValidators.validateBulkCount(requests.size)
        if (countError != null) {
            throw HuefyException(
                message = countError,
                errorCode = ErrorCode.VALIDATION_ERROR,
                recoverable = false
            )
        }

        val results = mutableListOf<BulkEmailResult>()

        for (emailRequest in requests) {
            try {
                val response = sendEmail(
                    templateKey = emailRequest.templateKey,
                    data = emailRequest.data,
                    recipient = emailRequest.recipient,
                    provider = emailRequest.provider
                )
                results.add(
                    BulkEmailResult(
                        email = emailRequest.recipient,
                        success = true,
                        result = response
                    )
                )
            } catch (e: HuefyException) {
                results.add(
                    BulkEmailResult(
                        email = emailRequest.recipient,
                        success = false,
                        error = BulkEmailError(
                            message = e.message ?: "Unknown error",
                            code = e.errorCode.name
                        )
                    )
                )
            }
        }

        return results
    }
}
