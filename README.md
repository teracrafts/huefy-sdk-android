# huefy-sdk

Official Kotlin SDK for [Huefy](https://huefy.dev) — transactional email delivery made simple.

## Installation

In `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.teracrafts:huefy-sdk:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
```

## Requirements

- Kotlin 1.9+
- JVM 17+
- `kotlinx-coroutines-core`

## Quick Start

```kotlin
import com.huefy.client.HuefyEmailClient
import com.huefy.config.HuefyConfig
import com.huefy.models.SendEmailRequest
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    HuefyEmailClient(HuefyConfig(apiKey = "sdk_your_api_key")).use { client ->
        val response = client.sendEmail(
            SendEmailRequest(
                templateKey = "welcome-email",
                data = mapOf("firstName" to "Alice", "trialDays" to 14),
                recipient = "alice@example.com",
            )
        )
        println("Email ID: ${response.data.emailId}")
    }
}
```

## Key Features

- **Coroutines-native** — all network calls are `suspend` functions, integrating naturally with structured concurrency
- **`Closeable`** — use with Kotlin's `use { }` block for safe resource cleanup
- **Data classes** — all request and response types are idiomatic Kotlin data classes
- **Retry with exponential backoff** — configurable attempts, base delay, ceiling, and jitter
- **Circuit breaker** — opens after 5 consecutive failures, probes after 30 s
- **HMAC-SHA256 signing** — optional request signing for additional integrity verification
- **Key rotation** — primary + secondary API key with seamless failover
- **Rate limit callbacks** — `onRateLimitUpdate` fires whenever rate-limit headers change
- **Thread-safe** — safe for concurrent use across coroutines
- **PII detection** — warns when template variables contain sensitive field patterns

## Configuration Reference

| Parameter | Default | Description |
|-----------|---------|-------------|
| `apiKey` | — | **Required.** Must have prefix `sdk_`, `srv_`, or `cli_` |
| `baseUrl` | `https://api.huefy.dev/api/v1/sdk` | Override the API base URL |
| `timeout` | `30000` | Request timeout in milliseconds |
| `retryConfig.maxAttempts` | `3` | Total attempts including the first |
| `retryConfig.baseDelayMs` | `500` | Exponential backoff base delay (ms) |
| `retryConfig.maxDelayMs` | `10000` | Maximum backoff delay (ms) |
| `retryConfig.jitter` | `0.2` | Random jitter factor (0–1) |
| `circuitBreakerConfig.failureThreshold` | `5` | Consecutive failures before circuit opens |
| `circuitBreakerConfig.resetTimeoutMs` | `30000` | Milliseconds before half-open probe |
| `logger` | `ConsoleLogger` | Custom logging sink |
| `secondaryApiKey` | `null` | Backup key used during key rotation |
| `enableRequestSigning` | `false` | Enable HMAC-SHA256 request signing |
| `onRateLimitUpdate` | `null` | Callback fired on rate-limit header changes |

## Bulk Email

```kotlin
val bulk = client.sendBulkEmails(
    SendBulkEmailsRequest(
        templateKey = "promo",
        recipients = listOf(
            BulkRecipient(email = "bob@example.com"),
            BulkRecipient(email = "carol@example.com"),
        )
    )
)

println("Sent: ${bulk.data.successCount}, Failed: ${bulk.data.failureCount}")
```

## Error Handling

```kotlin
import com.huefy.exception.HuefyAuthException
import com.huefy.exception.HuefyRateLimitException
import com.huefy.exception.HuefyCircuitOpenException
import com.huefy.exception.HuefyException

try {
    val response = client.sendEmail(request)
    println("Delivered: ${response.messageId}")
} catch (e: HuefyAuthException) {
    println("Invalid API key")
} catch (e: HuefyRateLimitException) {
    println("Rate limited. Retry after ${e.retryAfter}s")
} catch (e: HuefyCircuitOpenException) {
    println("Circuit open — service unavailable, backing off")
} catch (e: HuefyException) {
    println("Huefy error [${e.code}]: ${e.message}")
}
```

### Error Code Reference

| Exception | Code | Meaning |
|-----------|------|---------|
| `HuefyInitException` | 1001 | Client failed to initialise |
| `HuefyAuthException` | 1102 | API key rejected |
| `HuefyNetworkException` | 1201 | Upstream request failed |
| `HuefyCircuitOpenException` | 1301 | Circuit breaker tripped |
| `HuefyRateLimitException` | 2003 | Rate limit exceeded |
| `HuefyTemplateMissingException` | 2005 | Template key not found |

## Health Check

```kotlin
val health = client.healthCheck()
if (health.data.status != "healthy") {
    println("Huefy degraded: ${health.data.status}")
}
```

## Local Development

The current Kotlin SDK does not resolve `HUEFY_MODE`. To target localhost, override `baseUrl` in config:

```kotlin
val client = HuefyEmailClient(
    HuefyConfig(
        apiKey = "sdk_local_key",
        baseUrl = "http://localhost:3000/api/v1/sdk",
    )
)
```

## Developer Guide

Full documentation, advanced patterns, and provider configuration are in the [Kotlin Developer Guide](../../docs/spec/guides/kotlin.guide.md).

## License

MIT
