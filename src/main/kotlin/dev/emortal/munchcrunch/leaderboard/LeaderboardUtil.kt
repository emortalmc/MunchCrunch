package dev.emortal.munchcrunch.leaderboard

import com.google.gson.JsonParser
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.Instance
import java.nio.file.Path
import java.time.Duration
import java.util.*
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.readText

object LeaderboardUtil {
    val leaderboards = mutableListOf<Leaderboard>()

    init {
        doTheRefresh()
    }

    fun loadLeaderboards(instance: Instance, game: String){
        val savedBoards = Path.of("./leaderboards.json")
        if(!savedBoards.exists()){
            savedBoards.createFile()
            return
        }
        val json = JsonParser.parseString(savedBoards.readText()).asJsonObject
        json.get("leaderboards").asJsonObject.get(game).asJsonArray.forEach {
            val jsonObject = it.asJsonObject
            leaderboards.add(
                Leaderboard(
                    UUID.fromString(jsonObject.get("id").asString),
                    jsonObject.get("title").asString,
                    jsonObject.get("top").asInt,
                    jsonObject.get("stat").asString,
                    jsonObject.get("orderStyle").asString,
                    jsonObject.get("refresh").asInt,
                    Pos(
                        jsonObject.get("x").asDouble,
                        jsonObject.get("y").asDouble,
                        jsonObject.get("z").asDouble
                    ),
                    instance,
                    columnInt = 2
                )
            )
        }
        leaderboards.forEach {
            it.spawn()
        }
    }

    private fun doTheRefresh(){
        MinecraftServer.getSchedulerManager().buildTask {
            for(leaderboard in leaderboards){
                if(leaderboard.isReadyToRefresh()){
                    leaderboard.refresh()
                }
            }
        }.repeat(Duration.ofMillis(250)).schedule()
    }
}