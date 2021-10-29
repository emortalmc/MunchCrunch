package dev.emortal.munchcrunch

import kotlinx.serialization.json.Json

object ConfigurationHelper {

    val format = Json {
        encodeDefaults = true
        prettyPrint = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

}