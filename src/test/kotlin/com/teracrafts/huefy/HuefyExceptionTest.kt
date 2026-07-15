package com.teracrafts.huefy

import com.teracrafts.huefy.errors.ErrorCode
import com.teracrafts.huefy.errors.HuefyException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HuefyExceptionTest {

    @Test
    fun `fromStatusCode maps 402 to insufficient quota`() {
        val exception = HuefyException.fromStatusCode(
            402,
            """{"error":"Quota exceeded","code":"INSUFFICIENT_QUOTA"}""",
            "req_123"
        )

        assertEquals(ErrorCode.INSUFFICIENT_QUOTA, exception.errorCode)
        assertEquals(3003, exception.errorCode.numericCode)
        assertEquals(402, exception.statusCode)
        assertEquals("req_123", exception.requestId)
        assertFalse(exception.recoverable)
        assertTrue(exception.message.orEmpty().contains("Quota exceeded"))
    }
}
