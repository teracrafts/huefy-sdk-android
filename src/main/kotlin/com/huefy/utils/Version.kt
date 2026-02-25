package com.huefy.utils

/**
 * Version information for the Huefy SDK.
 */
object Version {

    /** The current SDK version. */
    const val SDK_VERSION: String = "1.0.0"

    /** The SDK identifier used in User-Agent headers. */
    const val SDK_IDENTIFIER: String = "huefy-kotlin"

    /** The full User-Agent string. */
    val USER_AGENT: String = "$SDK_IDENTIFIER/$SDK_VERSION"
}
