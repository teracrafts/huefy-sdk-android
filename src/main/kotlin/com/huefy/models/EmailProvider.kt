package com.huefy.models

enum class EmailProvider(val value: String) {
    SES("ses"),
    SENDGRID("sendgrid"),
    MAILGUN("mailgun"),
    MAILCHIMP("mailchimp");

    companion object {
        fun fromValue(value: String): EmailProvider =
            entries.firstOrNull { it.value.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown provider: $value")
    }
}
