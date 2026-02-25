package com.huefy.client

import com.huefy.config.HuefyConfig
import com.huefy.errors.ErrorCode
import com.huefy.errors.HuefyException
import com.huefy.http.HttpClient
import com.huefy.utils.Version
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Main client for the Huefy SDK.
 *
 * Usage:
 * ```kotlin
 * val client = HuefyClient(
 *     HuefyConfig(apiKey = "your-api-key")
 * )
 * val health = client.healthCheck()
 * client.close()
 * ```
 */
class HuefyClient(private val config: HuefyConfig) {

    private val httpClient: HttpClient = HttpClient(config)

    /**
     * Performs a health check against the API.
     *
     * @return [HealthResponse] containing status and version information.
     * @throws HuefyException if the request fails.
     */
    suspend fun healthCheck(): HealthResponse {
        return try {
            withTimeout(config.timeout) {
                val response = httpClient.get("/health")
                HealthResponse(
                    status = "ok",
                    version = Version.SDK_VERSION,
                    apiVersion = response["apiVersion"]?.toString()?.trim('"') ?: "unknown"
                )
            }
        } catch (e: HuefyException) {
            throw e
        } catch (e: Exception) {
            throw HuefyException.networkError("Health check failed: ${e.message}", e)
        }
    }

    /**
     * Sends a request to the API.
     *
     * @param method HTTP method (GET, POST, PUT, DELETE).
     * @param path API endpoint path.
     * @param body Optional request body.
     * @return Parsed JSON response as [JsonObject].
     * @throws HuefyException if the request fails.
     */
    suspend fun request(
        method: String,
        path: String,
        body: JsonObject? = null
    ): JsonObject {
        return withTimeout(config.timeout) {
            when (method.uppercase()) {
                "GET" -> httpClient.get(path)
                "POST" -> httpClient.post(path, body)
                "PUT" -> httpClient.put(path, body)
                "DELETE" -> httpClient.delete(path)
                else -> throw HuefyException(
                    message = "Unsupported HTTP method: $method",
                    errorCode = ErrorCode.VALIDATION_ERROR
                )
            }
        }
    }

    /**
     * Closes the underlying HTTP client and releases resources.
     */
    fun close() {
        httpClient.close()
    }
}

@Serializable
data class HealthResponse(
    val status: String,
    val version: String,
    val apiVersion: String
)
