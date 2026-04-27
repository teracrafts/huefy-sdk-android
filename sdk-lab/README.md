# Huefy Kotlin SDK Lab

A standalone verification runner that exercises core SDK infrastructure.

## Run

```bash
gradle lab
```

## Scenarios

1. Initialization — create client with dummy key, no exception
2. Config validation — empty API key throws
3. HMAC signing — 64-char hex signature
4. Error sanitization — PII patterns redacted
5. PII detection — email/ssn identified
6. Circuit breaker state — new breaker starts CLOSED
7. Health check — invoke `GET /health` against the configured base URL
8. Cleanup — close client, no exception
