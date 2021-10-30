package dev.emortal.munchcrunch.database

import dev.emortal.munchcrunch.ConfigurationHelper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.nio.file.Path
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

class DatabaseUtil() {
    var mainDB: SQLStorage? = null
    init {
        Config.config = initConfigFile(Path.of("./credentials.json"), Config())
        mainDB = SQLStorage(Config.config)
        mainDB!!.connect()
    }

    private inline fun <reified T : Any> initConfigFile(file: Path, emptyObj: T): T {
        return if (file.exists()) ConfigurationHelper.format.decodeFromString(file.readText()) else run {
            file.createFile()
            file.writeText(ConfigurationHelper.format.encodeToString(emptyObj))
            emptyObj
        }
    }
}