package dev.emortal.munchcrunch

import dev.emortal.munchcrunch.database.Config
import dev.emortal.munchcrunch.database.DatabaseUtil
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.extensions.Extension
import java.io.File

class MunchCrunchExtension : Extension() {
    var databaseUtil: DatabaseUtil? = null
    override fun initialize() {
        logger.info("[MunchCrunchExtension] has been enabled!")
        databaseUtil = DatabaseUtil()
        initConfigFile(File("./credentials.json"), Config)
    }

    inline fun <reified T : Any> initConfigFile(file: File, emptyObj: T): T {
        return if (file.exists()) ConfigurationHelper.format.decodeFromString(file.readText()) else run {
            file.createNewFile()
            file.writeText(ConfigurationHelper.format.encodeToString(emptyObj))
            emptyObj
        }
    }

    override fun terminate() {
        logger.info("[MunchCrunchExtension] has been disabled!")
    }

}