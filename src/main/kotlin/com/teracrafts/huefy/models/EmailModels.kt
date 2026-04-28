package com.teracrafts.huefy.models

import kotlinx.serialization.json.JsonElement

data class SendEmailRecipient(
    val email: String,
    val type: String? = null,
    val data: Map<String, Any>? = null,
)

data class SendEmailRequest(
    val templateKey: String,
    val data: Map<String, Any>,
    val recipient: String? = null,
    val recipientObject: SendEmailRecipient? = null,
    val provider: EmailProvider? = null,
) {
    constructor(
        templateKey: String,
        data: Map<String, Any>,
        recipient: SendEmailRecipient,
        provider: EmailProvider? = null,
    ) : this(
        templateKey = templateKey,
        data = data,
        recipient = null,
        recipientObject = recipient,
        provider = provider,
    )
}

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
    val data: Map<String, Any>? = null,
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
    val templateVersion: Int = 0,
    val senderUsed: String = "",
    val senderVerified: Boolean = false,
    val totalRecipients: Int,
    val processedCount: Int = 0,
    val successCount: Int,
    val failureCount: Int,
    val suppressedCount: Int,
    val startedAt: String,
    val completedAt: String? = null,
    val recipients: List<RecipientStatus>,
    val errors: List<EmailError> = emptyList(),
    val metadata: Map<String, JsonElement>? = null,
)

data class EmailError(
    val code: String,
    val message: String,
    val recipient: String? = null,
    val details: Map<String, JsonElement>? = null,
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
