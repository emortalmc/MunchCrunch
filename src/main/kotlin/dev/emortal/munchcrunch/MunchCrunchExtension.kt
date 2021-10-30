package dev.emortal.munchcrunch

import dev.emortal.munchcrunch.database.DatabaseUtil
import net.minestom.server.extensions.Extension

class MunchCrunchExtension : Extension() {
    var databaseUtil: DatabaseUtil? = null

    override fun initialize() {
        logger.info("[MunchCrunchExtension] has been enabled!")
        databaseUtil = DatabaseUtil()
    }

    override fun terminate() {
        logger.info("[MunchCrunchExtension] has been disabled!")
        databaseUtil!!.mainDB!!.disconnect()
    }

}