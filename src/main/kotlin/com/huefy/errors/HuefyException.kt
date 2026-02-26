package com.huefy.errors

/**
 * Base exception for all Huefy SDK errors.
 *
 * @property errorCode Structured error code for programmatic handling.
 * @property statusCode HTTP status code, if applicable.
 * @property recoverable Whether the error is potentially recoverable via retry.
 * @property context Additional context about the error.
 */
class HuefyException(
    message: String,
    val errorCode: ErrorCode = ErrorCode.UNKNOWN_ERROR,
    val statusCode: Int? = null,
    val recoverable: Boolean = false,
    val context: Map<String, String> = emptyMap(),
    val requestId: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause) {

    override fun toString(): String {
        val parts = mutableListOf<String>()
        parts.add("HuefyException[${errorCode.name}]")
        parts.add(message ?: "Unknown error")
        statusCode?.let { parts.add("status=$it") }
        if (recoverable) parts.add("recoverable=true")
        if (context.isNotEmpty()) parts.add("context=$context")
        return parts.joinToString(" | ")
    }

    companion object {
        /**
         * Creates an exception for network-related errors.
         */
        fun networkError(message: String, cause: Throwable? = null): HuefyException {
            return HuefyException(
                message = message,
                errorCode = ErrorCode.NETWORK_ERROR,
                recoverable = true,
                cause = cause
            )
        }

        /**
         * Creates an exception for authentication failures.
         */
        fun authenticationError(message: String = "Authentication failed"): HuefyException {
            return HuefyException(
                message = message,
                errorCode = ErrorCode.AUTHENTICATION_ERROR,
                statusCode = 401,
                recoverable = false
            )
        }

        /**
         * Creates an exception for rate limiting.
         */
        fun rateLimitError(retryAfterMs: Long? = null): HuefyException {
            val context = retryAfterMs?.let { mapOf("retryAfterMs" to it.toString()) } ?: emptyMap()
            return HuefyException(
                message = "Rate limit exceeded",
                errorCode = ErrorCode.RATE_LIMIT_ERROR,
                statusCode = 429,
                recoverable = true,
                context = context
            )
        }

        /**
         * Creates an exception for validation errors.
         */
        fun validationError(message: String, field: String? = null): HuefyException {
            val context = field?.let { mapOf("field" to it) } ?: emptyMap()
            return HuefyException(
                message = message,
                errorCode = ErrorCode.VALIDATION_ERROR,
                statusCode = 400,
                recoverable = false,
                context = context
            )
        }

        /**
         * Creates an exception for server errors.
         */
        fun serverError(message: String, statusCode: Int = 500): HuefyException {
            return HuefyException(
                message = message,
                errorCode = ErrorCode.SERVER_ERROR,
                statusCode = statusCode,
                recoverable = true
            )
        }

        /**
         * Creates an exception for timeout errors.
         */
        fun timeoutError(message: String = "Request timed out"): HuefyException {
            return HuefyException(
                message = message,
                errorCode = ErrorCode.TIMEOUT_ERROR,
                recoverable = true
            )
        }

        /**
         * Creates an exception for circuit breaker open state.
         */
        fun circuitBreakerOpen(message: String = "Circuit breaker is open"): HuefyException {
            return HuefyException(
                message = message,
                errorCode = ErrorCode.CIRCUIT_BREAKER_OPEN,
                recoverable = true
            )
        }

        /**
         * Creates an exception from an HTTP status code.
         */
        fun fromStatusCode(statusCode: Int, message: String, requestId: String? = null): HuefyException {
            return when (statusCode) {
                401 -> HuefyException(
                    message = message,
                    errorCode = ErrorCode.AUTHENTICATION_ERROR,
                    statusCode = 401,
                    recoverable = false,
                    requestId = requestId
                )
                403 -> HuefyException(
                    message = message,
                    errorCode = ErrorCode.FORBIDDEN_ERROR,
                    statusCode = 403,
                    recoverable = false,
                    requestId = requestId
                )
                404 -> HuefyException(
                    message = message,
                    errorCode = ErrorCode.NOT_FOUND_ERROR,
                    statusCode = 404,
                    recoverable = false,
                    requestId = requestId
                )
                429 -> HuefyException(
                    message = "Rate limit exceeded",
                    errorCode = ErrorCode.RATE_LIMIT_ERROR,
                    statusCode = 429,
                    recoverable = true,
                    requestId = requestId
                )
                in 500..599 -> HuefyException(
                    message = message,
                    errorCode = ErrorCode.SERVER_ERROR,
                    statusCode = statusCode,
                    recoverable = true,
                    requestId = requestId
                )
                else -> HuefyException(
                    message = message,
                    errorCode = ErrorCode.UNKNOWN_ERROR,
                    statusCode = statusCode,
                    recoverable = false,
                    requestId = requestId
                )
            }
        }
    }
}
