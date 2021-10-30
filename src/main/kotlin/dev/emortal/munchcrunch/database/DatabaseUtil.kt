package dev.emortal.munchcrunch.database

import dev.emortal.munchcrunch.ConfigurationHelper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.MinecraftServer
import java.io.File
import java.nio.file.Path
import java.time.Duration
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

class DatabaseUtil() {
    var mainDB: SQLStorage? = null
    init {
        Config.config = initConfigFile(File("./credentials.json"), Config())
        mainDB = SQLStorage(Config.config)
        mainDB!!.connect()
        mainDB!!.insertIntoTable(
            "testTable", listOf("UUID", "Gems"),
            Values(UUID.randomUUID().toString().replace("-", ""),21),
            Values(UUID.randomUUID().toString().replace("-", ""),13),
            Values(UUID.randomUUID().toString().replace("-", ""),16)
        )
    }

    private inline fun <reified T : Any> initConfigFile(file: File, emptyObj: T): T {
        return if (file.exists()) ConfigurationHelper.format.decodeFromString(file.readText()) else run {
            file.createNewFile()
            file.writeText(ConfigurationHelper.format.encodeToString(emptyObj))
            emptyObj
        }
    }
}