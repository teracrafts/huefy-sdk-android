package com.huefy.models

data class SendEmailRequest(
    val templateKey: String,
    val data: Map<String, String>,
    val recipient: String,
    val provider: EmailProvider? = null,
)

data class RecipientStatus(
    val email: String,
    val status: String,
    val messageId: String? = null,
    val error: String? = null,
    val sentAt: String? = null,
)

data class SendEmailResponseData(
    val emailId: String,
    val status: String,
    val recipients: List<RecipientStatus>,
    val scheduledAt: String? = null,
    val sentAt: String? = null,
)

data class SendEmailResponse(
    val success: Boolean,
    val data: SendEmailResponseData,
    val correlationId: String,
)

data class BulkRecipient(
    val email: String,
    val type: String? = null,
    val data: Map<String, String>? = null,
)

data class SendBulkEmailsRequest(
    val templateKey: String,
    val recipients: List<BulkRecipient>,
    val provider: EmailProvider? = null,
)

data class SendBulkEmailsResponseData(
    val batchId: String,
    val status: String,
    val templateKey: String,
    val totalRecipients: Int,
    val processedCount: Int = 0,
    val successCount: Int,
    val failureCount: Int,
    val suppressedCount: Int,
    val startedAt: String,
    val completedAt: String? = null,
    val recipients: List<RecipientStatus>,
)

data class SendBulkEmailsResponse(
    val success: Boolean,
    val data: SendBulkEmailsResponseData,
    val correlationId: String,
)

data class HealthResponseData(
    val status: String,
    val timestamp: String,
    val version: String,
)

data class HealthResponse(
    val success: Boolean,
    val data: HealthResponseData,
    val correlationId: String,
) {
    fun isHealthy(): Boolean = data.status.equals("healthy", ignoreCase = true) || data.status.equals("ok", ignoreCase = true)
}
