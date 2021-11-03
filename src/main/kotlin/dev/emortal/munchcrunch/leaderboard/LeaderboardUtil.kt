package dev.emortal.munchcrunch.leaderboard

import com.google.gson.JsonParser
import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.Instance
import java.nio.file.Path
import java.util.*
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.readText

object LeaderboardUtil {
    val leaderboards = mutableListOf<Leaderboard>()
    fun loadLeaderboards(instance: Instance, game: String){
        val savedBoards = Path.of("./leaderboards.json")
        if(!savedBoards.exists()){
            savedBoards.createFile()
            return
        }
        val json = JsonParser.parseString(savedBoards.readText()).asJsonObject
        json.get("leaderboards").asJsonArray.forEach {
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
                    instance
                )
            )
        }
        leaderboards.forEach {
            it.spawn()
        }
    }
}