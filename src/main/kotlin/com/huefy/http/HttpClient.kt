package com.huefy.http

import com.huefy.config.HuefyConfig
import com.huefy.errors.ErrorSanitizer
import com.huefy.errors.HuefyException
import com.huefy.utils.Version
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/**
 * HTTP client for making API requests with retry and circuit breaker support.
 *
 * Uses Ktor CIO engine for non-blocking, coroutine-friendly HTTP.
 */
class HttpClient(private val config: HuefyConfig) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val client = io.ktor.client.HttpClient(CIO) {
        engine {
            requestTimeout = config.timeout
        }
    }

    private val retryHandler = RetryHandler(config.retryConfig)
    private val circuitBreaker = CircuitBreaker(config.circuitBreakerConfig)

    /**
     * Performs a GET request.
     *
     * @param path API endpoint path.
     * @return Parsed JSON response.
     */
    suspend fun get(path: String): JsonObject {
        return executeWithResilience {
            val response = client.get(buildUrl(path)) {
                applyHeaders()
            }
            handleResponse(response)
        }
    }

    /**
     * Performs a POST request.
     *
     * @param path API endpoint path.
     * @param body Optional JSON body.
     * @return Parsed JSON response.
     */
    suspend fun post(path: String, body: JsonObject? = null): JsonObject {
        return executeWithResilience {
            val response = client.post(buildUrl(path)) {
                applyHeaders()
                body?.let {
                    contentType(ContentType.Application.Json)
                    setBody(it.toString())
                }
            }
            handleResponse(response)
        }
    }

    /**
     * Performs a PUT request.
     *
     * @param path API endpoint path.
     * @param body Optional JSON body.
     * @return Parsed JSON response.
     */
    suspend fun put(path: String, body: JsonObject? = null): JsonObject {
        return executeWithResilience {
            val response = client.put(buildUrl(path)) {
                applyHeaders()
                body?.let {
                    contentType(ContentType.Application.Json)
                    setBody(it.toString())
                }
            }
            handleResponse(response)
        }
    }

    /**
     * Performs a DELETE request.
     *
     * @param path API endpoint path.
     * @return Parsed JSON response.
     */
    suspend fun delete(path: String): JsonObject {
        return executeWithResilience {
            val response = client.delete(buildUrl(path)) {
                applyHeaders()
            }
            handleResponse(response)
        }
    }

    /**
     * Closes the underlying Ktor HTTP client.
     */
    fun close() {
        client.close()
    }

    private fun buildUrl(path: String): String {
        val base = config.resolvedBaseUrl().trimEnd('/')
        val cleanPath = path.trimStart('/')
        return "$base/$cleanPath"
    }

    private fun HttpRequestBuilder.applyHeaders() {
        header("X-API-Key", config.apiKey)
        header("User-Agent", "huefy-kotlin/${Version.SDK_VERSION}")
        header("Accept", "application/json")
    }

    private suspend fun handleResponse(response: HttpResponse): JsonObject {
        val bodyText = response.bodyAsText()
        val statusCode = response.status.value

        if (statusCode in 200..299) {
            return try {
                json.decodeFromString<JsonObject>(bodyText)
            } catch (e: Exception) {
                throw HuefyException.serverError(
                    "Failed to parse response body: ${e.message}",
                    statusCode
                )
            }
        }

        val message = if (config.enableErrorSanitization) {
            ErrorSanitizer.sanitize(bodyText)
        } else {
            bodyText
        }

        throw HuefyException.fromStatusCode(statusCode, message)
    }

    private suspend fun <T> executeWithResilience(block: suspend () -> T): T {
        return circuitBreaker.execute {
            retryHandler.executeWithRetry { block() }
        }
    }
}
