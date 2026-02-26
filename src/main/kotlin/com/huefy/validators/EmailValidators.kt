package com.huefy.validators

/**
 * Validators for email-related inputs.
 *
 * Provides static validation methods for email addresses, template keys,
 * template data, and bulk email counts.
 */
object EmailValidators {

    private val EMAIL_REGEX = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
    private const val MAX_EMAIL_LENGTH = 254
    private const val MAX_TEMPLATE_KEY_LENGTH = 100
    private const val MAX_BULK_EMAILS = 100

    /**
     * Validates a recipient email address.
     *
     * @param email the email address to validate
     * @return an error message if invalid, or null if valid
     */
    fun validateEmail(email: String?): String? {
        if (email.isNullOrBlank()) return "Recipient email is required"
        val trimmed = email.trim()
        if (trimmed.length > MAX_EMAIL_LENGTH) return "Email exceeds maximum length of $MAX_EMAIL_LENGTH characters"
        if (!EMAIL_REGEX.matches(trimmed)) return "Invalid email address: $trimmed"
        return null
    }

    /**
     * Validates a template key.
     *
     * @param key the template key to validate
     * @return an error message if invalid, or null if valid
     */
    fun validateTemplateKey(key: String?): String? {
        if (key.isNullOrBlank()) return "Template key is required"
        if (key.trim().length > MAX_TEMPLATE_KEY_LENGTH) return "Template key exceeds maximum length of $MAX_TEMPLATE_KEY_LENGTH characters"
        return null
    }

    /**
     * Validates template data.
     *
     * @param data the template data map to validate
     * @return an error message if invalid, or null if valid
     */
    fun validateEmailData(data: Map<String, String>?): String? {
        if (data == null) return "Template data is required"
        return null
    }

    /**
     * Validates the count for a bulk email request.
     *
     * @param count the number of emails in the bulk request
     * @return an error message if invalid, or null if valid
     */
    fun validateBulkCount(count: Int): String? {
        if (count <= 0) return "At least one email is required"
        if (count > MAX_BULK_EMAILS) return "Maximum of $MAX_BULK_EMAILS emails per bulk request"
        return null
    }

    /**
     * Validates all inputs for sending a single email.
     *
     * @param templateKey the template key
     * @param data the template data
     * @param recipient the recipient email address
     * @return a list of error messages (empty if all valid)
     */
    fun validateSendEmailInput(
        templateKey: String?,
        data: Map<String, String>?,
        recipient: String?
    ): List<String> {
        return listOfNotNull(
            validateTemplateKey(templateKey),
            validateEmailData(data),
            validateEmail(recipient)
        )
    }
}
