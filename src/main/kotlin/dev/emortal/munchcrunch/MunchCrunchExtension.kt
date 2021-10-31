package dev.emortal.munchcrunch

import dev.emortal.munchcrunch.database.DatabaseUtil
import net.minestom.server.extensions.Extension

class MunchCrunchExtension : Extension() {

    override fun initialize() {
        logger.info("[MunchCrunch] has been enabled!")
    }

    override fun terminate() {
        logger.info("[MunchCrunch] has been disabled!")
        DatabaseUtil.mainDB!!.disconnect()
    }

}