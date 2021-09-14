package dev.emortal.munchcrunch

import net.minestom.server.extensions.Extension;

class MunchCrunchExtension : Extension() {

    override fun initialize() {
        logger.info("[MunchCrunchExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[MunchCrunchExtension] has been disabled!")
    }

}