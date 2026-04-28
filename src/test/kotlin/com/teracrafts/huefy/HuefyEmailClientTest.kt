package com.teracrafts.huefy

import com.teracrafts.huefy.config.HuefyConfig
import com.teracrafts.huefy.client.HuefyEmailClient
import com.teracrafts.huefy.errors.HuefyException
import com.teracrafts.huefy.models.SendEmailRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.runBlocking

class HuefyEmailClientTest {

    @Test
    fun `sendEmail returns validation error when recipient is missing`() = runBlocking {
        val client = HuefyEmailClient(HuefyConfig(apiKey = "sdk_test_key"))

        val error = assertFailsWith<HuefyException> {
            client.sendEmail(
                SendEmailRequest(
                    templateKey = "welcome",
                    data = mapOf("name" to "John"),
                    recipient = null,
                    recipientObject = null,
                )
            )
        }

        assertEquals("VALIDATION_ERROR", error.errorCode.name)
    }
}
