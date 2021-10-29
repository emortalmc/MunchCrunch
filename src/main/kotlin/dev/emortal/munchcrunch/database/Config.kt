package dev.emortal.munchcrunch.database

import kotlinx.serialization.Serializable

@Serializable
class Config(
    val host: String = "0.0.0.0",
    val port: String = "3306",
    val username: String = "username",
    val password: String = "password",
    val database: String = "database"
) {
    companion object {

        // Allows for custom config setting during boot.
        private var _config: Config? = null

        var config: Config
            get() = _config ?: run {
                throw IllegalArgumentException("Config does not exist! Set it correctly or boot from a file.")
            }
            set(value) {
                _config = value
            }
    }
}