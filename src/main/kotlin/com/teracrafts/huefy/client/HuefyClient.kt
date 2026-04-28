package com.teracrafts.huefy.client

import com.teracrafts.huefy.config.HuefyConfig
import com.teracrafts.huefy.errors.ErrorCode
import com.teracrafts.huefy.errors.HuefyException
import com.teracrafts.huefy.http.HttpClient
import com.teracrafts.huefy.models.HealthResponse
import com.teracrafts.huefy.models.HealthResponseData
import com.teracrafts.huefy.utils.Version
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull

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
open class HuefyClient(private val config: HuefyConfig) : java.io.Closeable {

    private val httpClient: HttpClient = HttpClient(config)

    @Volatile
    private var closed = false

    /**
     * Performs a health check against the API.
     *
     * @return [HealthResponse] containing status and version information.
     * @throws HuefyException if the request fails.
     */
    suspend fun healthCheck(): HealthResponse {
        check(!closed) { "Client has been closed" }
        return try {
            withTimeout(config.timeout) {
                val response = httpClient.get("/health")
                val dataNode = response["data"]?.jsonObject
                    ?: throw HuefyException.networkError("Missing data in health response", null)
                HealthResponse(
                    success = response["success"]?.jsonPrimitive?.booleanOrNull ?: false,
                    data = HealthResponseData(
                        status = dataNode["status"]?.jsonPrimitive?.contentOrNull ?: "unknown",
                        timestamp = dataNode["timestamp"]?.jsonPrimitive?.contentOrNull ?: "",
                        version = dataNode["version"]?.jsonPrimitive?.contentOrNull ?: "unknown",
                    ),
                    correlationId = response["correlationId"]?.jsonPrimitive?.contentOrNull ?: "",
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
        check(!closed) { "Client has been closed" }
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
    override fun close() {
        closed = true
        httpClient.close()
    }
}

