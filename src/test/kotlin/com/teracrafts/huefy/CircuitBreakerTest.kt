package com.teracrafts.huefy

import com.teracrafts.huefy.config.CircuitBreakerConfig
import com.teracrafts.huefy.errors.ErrorCode
import com.teracrafts.huefy.errors.HuefyException
import com.teracrafts.huefy.http.CircuitBreaker
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CircuitBreakerTest {

    @Test
    fun `circuit breaker starts in closed state`() = runTest {
        val cb = CircuitBreaker(CircuitBreakerConfig())
        assertEquals(CircuitBreaker.State.CLOSED, cb.currentState())
    }

    @Test
    fun `successful calls keep circuit closed`() = runTest {
        val cb = CircuitBreaker(CircuitBreakerConfig(failureThreshold = 3))

        val result = cb.execute { "success" }

        assertEquals("success", result)
        assertEquals(CircuitBreaker.State.CLOSED, cb.currentState())
        assertEquals(0, cb.currentFailureCount())
    }

    @Test
    fun `failures below threshold keep circuit closed`() = runTest {
        val cb = CircuitBreaker(CircuitBreakerConfig(failureThreshold = 3))

        repeat(2) {
            try {
                cb.execute {
                    throw HuefyException.networkError("fail")
                }
            } catch (_: HuefyException) { }
        }

        assertEquals(CircuitBreaker.State.CLOSED, cb.currentState())
        assertEquals(2, cb.currentFailureCount())
    }

    @Test
    fun `circuit opens after reaching failure threshold`() = runTest {
        val cb = CircuitBreaker(CircuitBreakerConfig(failureThreshold = 3))

        repeat(3) {
            try {
                cb.execute {
                    throw HuefyException.networkError("fail")
                }
            } catch (_: HuefyException) { }
        }

        assertEquals(CircuitBreaker.State.OPEN, cb.currentState())
    }

    @Test
    fun `open circuit rejects requests`() = runTest {
        val cb = CircuitBreaker(CircuitBreakerConfig(failureThreshold = 1))

        try {
            cb.execute { throw HuefyException.networkError("fail") }
        } catch (_: HuefyException) { }

        val exception = assertFailsWith<HuefyException> {
            cb.execute { "should not execute" }
        }

        assertEquals(ErrorCode.CIRCUIT_BREAKER_OPEN, exception.errorCode)
    }

    @Test
    fun `non-recoverable errors do not count as failures`() = runTest {
        val cb = CircuitBreaker(CircuitBreakerConfig(failureThreshold = 2))

        repeat(5) {
            try {
                cb.execute {
                    throw HuefyException.authenticationError()
                }
            } catch (_: HuefyException) { }
        }

        assertEquals(CircuitBreaker.State.CLOSED, cb.currentState())
        assertEquals(0, cb.currentFailureCount())
    }

    @Test
    fun `successful call after failures resets count`() = runTest {
        val cb = CircuitBreaker(CircuitBreakerConfig(failureThreshold = 5))

        repeat(3) {
            try {
                cb.execute { throw HuefyException.networkError("fail") }
            } catch (_: HuefyException) { }
        }

        assertEquals(3, cb.currentFailureCount())

        cb.execute { "success" }

        assertEquals(0, cb.currentFailureCount())
        assertEquals(CircuitBreaker.State.CLOSED, cb.currentState())
    }

    @Test
    fun `reset restores circuit to initial state`() = runTest {
        val cb = CircuitBreaker(CircuitBreakerConfig(failureThreshold = 1))

        try {
            cb.execute { throw HuefyException.networkError("fail") }
        } catch (_: HuefyException) { }

        assertEquals(CircuitBreaker.State.OPEN, cb.currentState())

        cb.reset()

        assertEquals(CircuitBreaker.State.CLOSED, cb.currentState())
        assertEquals(0, cb.currentFailureCount())
    }
}
