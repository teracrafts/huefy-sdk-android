package com.huefy.models

data class SendEmailRequest(
    val templateKey: String,
    val recipient: String,
    val data: Map<String, String>,
    val provider: EmailProvider? = null,
)

data class SendEmailResponse(
    val success: Boolean,
    val message: String,
    val messageId: String,
    val provider: String,
)

data class BulkEmailResult(
    val email: String,
    val success: Boolean,
    val result: SendEmailResponse? = null,
    val error: BulkEmailError? = null,
)

data class BulkEmailError(val message: String, val code: String)

data class HealthResponse(
    val status: String,
    val timestamp: String,
    val version: String,
) {
    fun isHealthy(): Boolean = status.equals("ok", ignoreCase = true)
}
