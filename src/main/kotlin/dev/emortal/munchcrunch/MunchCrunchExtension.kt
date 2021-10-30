package dev.emortal.munchcrunch

import dev.emortal.munchcrunch.database.DatabaseUtil
import net.minestom.server.extensions.Extension

class MunchCrunchExtension : Extension() {
    private var databaseUtil: DatabaseUtil? = null

    override fun initialize() {
        logger.info("has been enabled!")
        databaseUtil = DatabaseUtil()
    }

    override fun terminate() {
        logger.info("has been disabled!")
        databaseUtil!!.mainDB!!.disconnect()
    }
}