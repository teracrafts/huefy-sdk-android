# Huefy Kotlin SDK Lab

Verifies the core email contract through the real `HuefyEmailClient` against a local stub server.

## Run

```bash
./gradlew lab -q
```

from `sdks/kotlin/`.

## Scenarios

1. Initialization
2. Single-send contract shaping
3. Bulk-send contract shaping
4. Invalid single rejection
5. Invalid bulk rejection
6. Health request path behavior
7. Cleanup

## Notes

- The lab uses the Gradle wrapper so it is reproducible across environments.
- It verifies normalized request bodies, parsed responses, and validation boundaries.
