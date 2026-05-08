package com.teracrafts.huefy.lab

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import com.teracrafts.huefy.client.HuefyEmailClient
import com.teracrafts.huefy.config.HuefyConfig
import com.teracrafts.huefy.config.RetryConfig
import com.teracrafts.huefy.errors.HuefyException
import com.teracrafts.huefy.models.BulkRecipient
import com.teracrafts.huefy.models.EmailProvider
import com.teracrafts.huefy.models.SendBulkEmailsRequest
import com.teracrafts.huefy.models.SendEmailRecipient
import com.teracrafts.huefy.models.SendEmailRequest
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.InetSocketAddress

private const val GREEN = "\u001b[32m"
private const val RED = "\u001b[31m"
private const val RESET = "\u001b[0m"

private var passed = 0
private var failed = 0

fun main() = runBlocking {
    println("=== Huefy Kotlin SDK Lab ===")
    println()

    if (isLiveMode()) {
        runLiveLab()

        println()
        println("========================================")
        println("Results: $passed passed, $failed failed")
        println("========================================")
        println()

        if (failed == 0) {
            println("All verifications passed!")
        } else {
            System.exit(1)
        }
        return@runBlocking
    }

    StubServer().use { server ->
        server.start()

        var client: HuefyEmailClient? = null
        runCatching {
            client = buildClient(server.baseUrl)
        }.onSuccess {
            pass("Initialization")
        }.onFailure { error ->
            fail("Initialization", error.message ?: "unknown error")
        }

        client?.let {
            verifySingleSend(it, server)
            verifyBulkSend(it, server)
            verifyInvalidSingle(it, server)
            verifyInvalidBulk(it, server)
            verifyHealth(it, server)
            verifyCleanup(it)
        }
    }

    println()
    println("========================================")
    println("Results: $passed passed, $failed failed")
    println("========================================")
    println()

    if (failed == 0) {
        println("All verifications passed!")
    } else {
        System.exit(1)
    }
}

private fun isLiveMode(): Boolean =
    System.getenv("HUEFY_SDK_LAB_MODE")?.equals("live", ignoreCase = true) == true

private fun requireEnv(name: String): String =
    System.getenv(name)?.trim()?.takeIf { it.isNotEmpty() }
        ?: error("$name is required in live mode")

private fun resolveLiveProvider(): EmailProvider? =
    when (System.getenv("HUEFY_SDK_LIVE_PROVIDER")?.trim()?.lowercase()) {
        "sendgrid" -> EmailProvider.SENDGRID
        "ses" -> EmailProvider.SES
        "mailgun" -> EmailProvider.MAILGUN
        else -> null
    }

private suspend fun runLiveLab() {
    val client = runCatching {
        HuefyEmailClient(
            HuefyConfig(
                apiKey = requireEnv("HUEFY_SDK_LIVE_API_KEY"),
                baseUrl = requireEnv("HUEFY_SDK_LIVE_BASE_URL"),
                timeout = 10_000L,
                retryConfig = RetryConfig(maxRetries = 0, baseDelayMs = 50, maxDelayMs = 50),
            )
        )
    }.onSuccess {
        pass("Initialization")
    }.onFailure { error ->
        fail("Initialization", error.message ?: "unknown error")
    }.getOrNull() ?: return

    val recipient = requireEnv("HUEFY_SDK_LIVE_RECIPIENT")
    val templateKey = requireEnv("HUEFY_SDK_LIVE_TEMPLATE_KEY")
    val provider = resolveLiveProvider()

    runCatching {
        val response = client.sendEmail(
            SendEmailRequest(
                templateKey = templateKey,
                data = mapOf("FirstName" to "SDK Live"),
                recipient = recipient,
                provider = provider,
            )
        )
        require(response.success) { "expected successful live send" }
    }.onSuccess {
        pass("Single-send live behavior")
    }.onFailure { error ->
        fail("Single-send live behavior", error.message ?: "unknown error")
    }

    runCatching {
        val response = client.sendBulkEmails(
            SendBulkEmailsRequest(
                templateKey = templateKey,
                recipients = listOf(BulkRecipient(email = recipient, type = "TO")),
                provider = provider,
            )
        )
        require(response.success && response.data.totalRecipients >= 1) { "expected successful live bulk send" }
    }.onSuccess {
        pass("Bulk-send live behavior")
    }.onFailure { error ->
        fail("Bulk-send live behavior", error.message ?: "unknown error")
    }

    runCatching {
        client.sendEmail(
            SendEmailRequest(
                templateKey = templateKey,
                data = emptyMap<String, Any>(),
                recipient = "bad-email",
            )
        )
        error("expected validation failure")
    }.onSuccess {
        fail("Invalid single rejection", "expected validation failure")
    }.onFailure {
        pass("Invalid single rejection")
    }

    runCatching {
        client.sendBulkEmails(
            SendBulkEmailsRequest(
                templateKey = templateKey,
                recipients = listOf(BulkRecipient(email = "bad-email", type = "reply-to")),
            )
        )
        error("expected validation failure")
    }.onSuccess {
        fail("Invalid bulk rejection", "expected validation failure")
    }.onFailure {
        pass("Invalid bulk rejection")
    }

    runCatching {
        val health = client.healthCheck()
        require(health.data.status == "healthy") { "unexpected health status ${health.data.status}" }
    }.onSuccess {
        pass("Health request path behavior")
    }.onFailure { error ->
        fail("Health request path behavior", error.message ?: "unknown error")
    }

    runCatching {
        client.close()
    }.onSuccess {
        pass("Cleanup")
    }.onFailure { error ->
        fail("Cleanup", error.message ?: "unknown error")
    }
}

private fun buildClient(baseUrl: String): HuefyEmailClient = HuefyEmailClient(
    HuefyConfig(
        apiKey = "sdk_lab_test_key_xxxxxxxxxxxx",
        baseUrl = baseUrl,
        timeout = 2_000L,
        retryConfig = RetryConfig(maxRetries = 0, baseDelayMs = 50, maxDelayMs = 50),
    )
)

private suspend fun verifySingleSend(client: HuefyEmailClient, server: StubServer) {
    runCatching {
        val response = client.sendEmail(
            SendEmailRequest(
                templateKey = " welcome-email ",
                data = mapOf(
                    "name" to "John",
                    "count" to 2,
                    "beta" to true,
                    "roles" to listOf("admin", "editor"),
                ),
                recipient = " john@example.com ",
                provider = EmailProvider.SENDGRID,
            )
        )

        val body = server.lastBody("/emails/send") ?: error("missing single-send request body")
        require(response.success) { "stub response was not parsed as success" }
        require(server.lastPath("/emails/send") == "/emails/send") { "unexpected single-send path" }
        require(body["templateKey"]!!.jsonPrimitive.content == "welcome-email") { "templateKey was not trimmed" }
        require(body["recipient"]!!.jsonPrimitive.content == "john@example.com") { "recipient was not trimmed" }
        require(body["providerType"]!!.jsonPrimitive.content == "sendgrid") { "providerType mismatch" }
        require(body["template_key"] == null) { "legacy template_key should be absent" }
        require(body["provider"] == null) { "legacy provider should be absent" }
        val data = body["data"]!!.jsonObject
        require(data["name"]!!.jsonPrimitive.content == "John") { "data.name mismatch" }
        require(data["count"]!!.jsonPrimitive.int == 2) { "data.count mismatch" }
        require(data["beta"]!!.jsonPrimitive.boolean) { "data.beta mismatch" }
        require(data["roles"]!!.jsonArray[0].jsonPrimitive.content == "admin") { "data.roles mismatch" }
    }.onSuccess {
        pass("Single-send contract shaping")
    }.onFailure { error ->
        fail("Single-send contract shaping", error.message ?: "unknown error")
    }
}

private suspend fun verifyBulkSend(client: HuefyEmailClient, server: StubServer) {
    runCatching {
        client.sendBulkEmails(
            SendBulkEmailsRequest(
                templateKey = " account-update ",
                recipients = listOf(
                    BulkRecipient(email = " alice@example.com ", type = "TO", data = mapOf("segment" to "vip")),
                    BulkRecipient(email = "bob@example.com", type = "cc", data = mapOf("segment" to "standard")),
                ),
                provider = EmailProvider.SES,
            )
        )

        val body = server.lastBody("/emails/send-bulk") ?: error("missing bulk request body")
        require(server.lastPath("/emails/send-bulk") == "/emails/send-bulk") { "unexpected bulk path" }
        require(body["templateKey"]!!.jsonPrimitive.content == "account-update") { "templateKey was not trimmed" }
        require(body["providerType"]!!.jsonPrimitive.content == "ses") { "providerType mismatch" }
        val recipients = body["recipients"]!!.jsonArray
        require(recipients[0].jsonObject["email"]!!.jsonPrimitive.content == "alice@example.com") { "recipient 0 email mismatch" }
        require(recipients[0].jsonObject["type"]!!.jsonPrimitive.content == "to") { "recipient 0 type mismatch" }
        require(recipients[0].jsonObject["data"]!!.jsonObject["segment"]!!.jsonPrimitive.content == "vip") { "recipient 0 data mismatch" }
        require(recipients[1].jsonObject["type"]!!.jsonPrimitive.content == "cc") { "recipient 1 type mismatch" }
    }.onSuccess {
        pass("Bulk-send contract shaping")
    }.onFailure { error ->
        fail("Bulk-send contract shaping", error.message ?: "unknown error")
    }
}

private suspend fun verifyInvalidSingle(client: HuefyEmailClient, server: StubServer) {
    val before = server.hitCount("/emails/send")
    runCatching {
        client.sendEmail(
            SendEmailRequest(
                templateKey = "welcome",
                data = mapOf("name" to "John"),
                recipient = "not-an-email",
            )
        )
        error("expected validation failure")
    }.onSuccess {
        fail("Invalid single rejection", "expected validation failure")
    }.onFailure { error ->
        if (server.hitCount("/emails/send") != before) {
            fail("Invalid single rejection", "transport was called for invalid single input")
        } else if (error !is HuefyException || !error.message.orEmpty().contains("Validation failed")) {
            fail("Invalid single rejection", error.message ?: "unexpected error")
        } else {
            pass("Invalid single rejection")
        }
    }
}

private suspend fun verifyInvalidBulk(client: HuefyEmailClient, server: StubServer) {
    val before = server.hitCount("/emails/send-bulk")
    runCatching {
        client.sendBulkEmails(
            SendBulkEmailsRequest(
                templateKey = "welcome",
                recipients = listOf(
                    BulkRecipient(
                        email = "john@example.com",
                        type = "reply-to",
                        data = mapOf("segment" to "vip"),
                    )
                ),
            )
        )
        error("expected validation failure")
    }.onSuccess {
        fail("Invalid bulk rejection", "expected validation failure")
    }.onFailure { error ->
        if (server.hitCount("/emails/send-bulk") != before) {
            fail("Invalid bulk rejection", "transport was called for invalid bulk input")
        } else if (error !is HuefyException || !error.message.orEmpty().contains("recipients[0]")) {
            fail("Invalid bulk rejection", error.message ?: "unexpected error")
        } else {
            pass("Invalid bulk rejection")
        }
    }
}

private suspend fun verifyHealth(client: HuefyEmailClient, server: StubServer) {
    runCatching {
        val health = client.healthCheck()
        require(server.lastPath("/health") == "/health") { "unexpected health path" }
        require(health.data.status == "healthy") { "unexpected health status ${health.data.status}" }
    }.onSuccess {
        pass("Health request path behavior")
    }.onFailure { error ->
        fail("Health request path behavior", error.message ?: "unknown error")
    }
}

private suspend fun verifyCleanup(client: HuefyEmailClient) {
    runCatching {
        client.close()
        client.healthCheck()
        error("expected closed client to reject requests")
    }.onSuccess {
        fail("Cleanup", "expected closed client to reject requests")
    }.onFailure { error ->
        if (error is IllegalStateException && error.message.orEmpty().contains("closed")) {
            pass("Cleanup")
        } else {
            fail("Cleanup", error.message ?: "unknown error")
        }
    }
}

private fun pass(label: String) {
    passed++
    println("${GREEN}[PASS]${RESET} $label")
}

private fun fail(label: String, reason: String) {
    failed++
    println("${RED}[FAIL]${RESET} $label - $reason")
}

private class StubServer : AutoCloseable {
    private val server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0)
    private val hitCounts = mutableMapOf<String, Int>()
    private val lastPaths = mutableMapOf<String, String>()
    private val lastBodies = mutableMapOf<String, JsonObject>()

    val baseUrl: String
        get() = "http://127.0.0.1:${server.address.port}"

    init {
        server.createContext("/emails/send") { exchange -> handle(exchange, SINGLE_SEND_RESPONSE) }
        server.createContext("/emails/send-bulk") { exchange -> handle(exchange, BULK_SEND_RESPONSE) }
        server.createContext("/health") { exchange -> handle(exchange, HEALTH_RESPONSE) }
    }

    fun start() {
        server.start()
    }

    fun hitCount(path: String): Int = hitCounts[path] ?: 0

    fun lastPath(path: String): String? = lastPaths[path]

    fun lastBody(path: String): JsonObject? = lastBodies[path]

    private fun handle(exchange: HttpExchange, responseBody: String) {
        val path = exchange.requestURI.path
        hitCounts[path] = hitCount(path) + 1
        lastPaths[path] = path

        val requestBody = exchange.requestBody.readBytes().toString(Charsets.UTF_8)
        if (requestBody.isNotBlank()) {
            lastBodies[path] = Json.parseToJsonElement(requestBody).jsonObject
        }

        val bytes = responseBody.toByteArray()
        exchange.responseHeaders.add("Content-Type", "application/json")
        exchange.sendResponseHeaders(200, bytes.size.toLong())
        exchange.responseBody.use { it.write(bytes) }
        exchange.close()
    }

    override fun close() {
        server.stop(0)
    }
}

private const val SINGLE_SEND_RESPONSE =
    """{"success":true,"data":{"emailId":"email_123","status":"queued","recipients":[{"email":"john@example.com","status":"queued","messageId":"msg_123","sentAt":"2026-01-01T00:00:00Z"}],"scheduledAt":null,"sentAt":null},"correlationId":"corr_single"}"""

private const val BULK_SEND_RESPONSE =
    """{"success":true,"data":{"batchId":"batch_123","status":"queued","templateKey":"account-update","templateVersion":1,"senderUsed":"noreply@example.com","senderVerified":true,"totalRecipients":2,"processedCount":2,"successCount":2,"failureCount":0,"suppressedCount":0,"startedAt":"2026-01-01T00:00:00Z","completedAt":"2026-01-01T00:00:01Z","recipients":[{"email":"alice@example.com","status":"queued","messageId":"msg_1","sentAt":"2026-01-01T00:00:00Z"},{"email":"bob@example.com","status":"queued","messageId":"msg_2","sentAt":"2026-01-01T00:00:00Z"}],"errors":[],"metadata":{"source":"sdk-lab"}},"correlationId":"corr_bulk"}"""

private const val HEALTH_RESPONSE =
    """{"success":true,"data":{"status":"healthy","timestamp":"2026-01-01T00:00:00Z","version":"sdk-lab"},"correlationId":"corr_health"}"""
