package com.huefy

import com.huefy.models.SendEmailRecipient
import com.huefy.validators.EmailValidators
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EmailValidatorsTest {

    // --- validateEmail ---

    @Test
    fun `validateEmail accepts a valid email`() {
        assertNull(EmailValidators.validateEmail("user@example.com"))
    }

    @Test
    fun `validateEmail accepts email with subdomain`() {
        assertNull(EmailValidators.validateEmail("user@mail.example.com"))
    }

    @Test
    fun `validateEmail accepts email with plus addressing`() {
        assertNull(EmailValidators.validateEmail("user+tag@example.com"))
    }

    @Test
    fun `validateEmail rejects null`() {
        assertEquals("Recipient email is required", EmailValidators.validateEmail(null))
    }

    @Test
    fun `validateEmail rejects empty string`() {
        assertEquals("Recipient email is required", EmailValidators.validateEmail(""))
    }

    @Test
    fun `validateEmail rejects blank string`() {
        assertEquals("Recipient email is required", EmailValidators.validateEmail("   "))
    }

    @Test
    fun `validateEmail rejects email without at sign`() {
        val result = EmailValidators.validateEmail("userexample.com")
        assertNotNull(result)
        assertTrue(result.startsWith("Invalid email address"))
    }

    @Test
    fun `validateEmail rejects email without domain`() {
        val result = EmailValidators.validateEmail("user@")
        assertNotNull(result)
        assertTrue(result.startsWith("Invalid email address"))
    }

    @Test
    fun `validateEmail rejects email exceeding max length`() {
        val longEmail = "a".repeat(250) + "@b.com"
        val result = EmailValidators.validateEmail(longEmail)
        assertNotNull(result)
        assertTrue(result.contains("maximum length"))
    }

    // --- validateTemplateKey ---

    @Test
    fun `validateTemplateKey accepts a valid key`() {
        assertNull(EmailValidators.validateTemplateKey("welcome-email"))
    }

    @Test
    fun `validateTemplateKey rejects null`() {
        assertEquals("Template key is required", EmailValidators.validateTemplateKey(null))
    }

    @Test
    fun `validateTemplateKey rejects empty string`() {
        assertEquals("Template key is required", EmailValidators.validateTemplateKey(""))
    }

    @Test
    fun `validateTemplateKey rejects blank string`() {
        assertEquals("Template key is required", EmailValidators.validateTemplateKey("   "))
    }

    @Test
    fun `validateTemplateKey rejects key exceeding max length`() {
        val longKey = "k".repeat(101)
        val result = EmailValidators.validateTemplateKey(longKey)
        assertNotNull(result)
        assertTrue(result.contains("maximum length"))
    }

    // --- validateEmailData ---

    @Test
    fun `validateEmailData accepts valid data map`() {
        assertNull(EmailValidators.validateEmailData(mapOf("name" to "John")))
    }

    @Test
    fun `validateEmailData accepts empty data map`() {
        assertNull(EmailValidators.validateEmailData(emptyMap()))
    }

    @Test
    fun `validateEmailData rejects null`() {
        assertEquals("Template data is required", EmailValidators.validateEmailData(null))
    }

    // --- validateBulkCount ---

    @Test
    fun `validateBulkCount accepts count of 1`() {
        assertNull(EmailValidators.validateBulkCount(1))
    }

    @Test
    fun `validateBulkCount accepts count of 100`() {
        assertNull(EmailValidators.validateBulkCount(100))
    }

    @Test
    fun `validateBulkCount accepts count of 50`() {
        assertNull(EmailValidators.validateBulkCount(50))
    }

    @Test
    fun `validateBulkCount accepts count of 1000`() {
        assertNull(EmailValidators.validateBulkCount(1000))
    }

    @Test
    fun `validateBulkCount rejects zero`() {
        val result = EmailValidators.validateBulkCount(0)
        assertNotNull(result)
        assertTrue(result.contains("At least one email"))
    }

    @Test
    fun `validateBulkCount rejects negative count`() {
        val result = EmailValidators.validateBulkCount(-1)
        assertNotNull(result)
        assertTrue(result.contains("At least one email"))
    }

    @Test
    fun `validateBulkCount rejects count exceeding 1000`() {
        val result = EmailValidators.validateBulkCount(1001)
        assertNotNull(result)
        assertTrue(result.contains("Maximum of 1000"))
    }

    // --- validateSendEmailInput ---

    @Test
    fun `validateSendEmailInput returns empty list for valid input`() {
        val errors = EmailValidators.validateSendEmailInput(
            "welcome", mapOf("name" to "John"), "john@example.com"
        )
        assertTrue(errors.isEmpty())
    }

    @Test
    fun `validateSendEmailInput returns all errors for completely invalid input`() {
        val errors = EmailValidators.validateSendEmailInput(null, null, null)
        assertEquals(3, errors.size)
    }

    @Test
    fun `validateSendEmailInput returns single error for one invalid field`() {
        val errors = EmailValidators.validateSendEmailInput(
            "welcome", mapOf("name" to "John"), "invalid-email"
        )
        assertEquals(1, errors.size)
        assertTrue(errors[0].startsWith("Invalid email address"))
    }

    @Test
    fun `validateSendEmailInput returns two errors for two invalid fields`() {
        val errors = EmailValidators.validateSendEmailInput(
            "", null, "john@example.com"
        )
        assertEquals(2, errors.size)
    }

    @Test
    fun `validateSendEmailInput accepts recipient object`() {
        val errors = EmailValidators.validateSendEmailRecipientInput(
            "welcome",
            mapOf("name" to "John"),
            SendEmailRecipient(email = "john@example.com", type = "cc", data = mapOf("plan" to "pro"))
        )
        assertTrue(errors.isEmpty())
    }

    @Test
    fun `validateSendEmailInput rejects invalid recipient object email`() {
        val errors = EmailValidators.validateSendEmailRecipientInput(
            "welcome",
            mapOf("name" to "John"),
            SendEmailRecipient(email = "bad", type = "cc")
        )
        assertEquals(1, errors.size)
        assertTrue(errors[0].startsWith("Invalid email address"))
    }

    @Test
    fun `validateSendEmailInput rejects invalid recipient object type`() {
        val errors = EmailValidators.validateSendEmailRecipientInput(
            "welcome",
            mapOf("name" to "John"),
            SendEmailRecipient(email = "user@example.com", type = "weird")
        )
        assertEquals(listOf("Recipient type must be one of: to, cc, bcc"), errors)
    }
}
