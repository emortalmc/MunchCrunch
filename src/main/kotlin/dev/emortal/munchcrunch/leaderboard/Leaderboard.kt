package dev.emortal.munchcrunch.leaderboard

import dev.emortal.munchcrunch.database.DatabaseUtil
import net.kyori.adventure.text.Component
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.instance.Instance

class Leaderboard(val id: Int,
                  val title: String,
                  val top: Int,
                  val stat: String,
                  val orderStyle: String = "DESC",
                  val refresh: Int,
                  var position: Pos,
                  val instance: Instance
) {
    val armorstands = mutableListOf<Entity>()
    fun spawn(){
        val titleHologram = Entity(EntityType.ARMOR_STAND)
        titleHologram.customName = Component.text(title)
        titleHologram.isCustomNameVisible = true
        for(column in DatabaseUtil.mainDB!!.getTable("testtable3", "Gems", "DESC", top)){
            println(column.values[2])
        }
    }
}