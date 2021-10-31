package dev.emortal.munchcrunch

import dev.emortal.munchcrunch.database.DatabaseUtil
import dev.emortal.munchcrunch.leaderboard.LeaderboardUtil
import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension

class MunchCrunchExtension : Extension() {

    override fun initialize() {
        logger.info("[MunchCrunch] has been enabled!")
        LeaderboardUtil.loadLeaderboards(MinecraftServer.getInstanceManager().createInstanceContainer(), "somestring")
    }

    override fun terminate() {
        logger.info("[MunchCrunch] has been disabled!")
        DatabaseUtil.mainDB!!.disconnect()

    }

}