package dev.emortal.munchcrunch

import dev.emortal.munchcrunch.database.DatabaseUtil
import dev.emortal.munchcrunch.leaderboard.LeaderboardUtil
import net.minestom.server.MinecraftServer
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.extensions.Extension
import world.cepi.kstom.event.listenOnly

class MunchCrunchExtension : Extension() {

    override fun initialize() {
        val eventNode = EventNode.all("test-event-node")
        eventNode.listenOnly<PlayerChatEvent> {
            when(message){
                "sus" -> {
                    LeaderboardUtil.loadLeaderboards(player.instance!!, "parkourTag")
                }
            }

        }
        MinecraftServer.getGlobalEventHandler().addChild(eventNode)
        logger.info("[MunchCrunch] has been enabled!")
    }

    override fun terminate() {
        DatabaseUtil.mainDB!!.disconnect()
        logger.info("[MunchCrunch] has been disabled!")
    }

}