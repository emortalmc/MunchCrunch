package dev.emortal.munchcrunch

import dev.emortal.munchcrunch.database.DatabaseUtil
import dev.emortal.munchcrunch.leaderboard.LeaderboardUtil
import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension

class MunchCrunchExtension : Extension() {

    override fun initialize() {
        LeaderboardUtil.loadLeaderboards(MinecraftServer.getInstanceManager().createInstanceContainer(), "somestring")

        logger.info("[MunchCrunch] has been enabled!")
    }

    override fun terminate() {
        DatabaseUtil.mainDB!!.disconnect()

        logger.info("[MunchCrunch] has been disabled!")
    }

}