package dev.emortal.munchcrunch.leaderboard

import dev.emortal.munchcrunch.database.DBCache
import net.kyori.adventure.text.Component
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.instance.Instance
import java.util.*

class Leaderboard(val id: UUID,
                  val title: String,
                  val top: Int,
                  val stat: String,
                  val orderStyle: String = "DESC",
                  val refresh: Int,
                  var position: Pos,
                  val instance: Instance,
                  private val dbCache: DBCache = DBCache()
) {
    val armorstands = mutableListOf<Entity>()
    fun spawn(){
        val titleHologram = Entity(EntityType.ARMOR_STAND)
        titleHologram.customName = Component.text(title)
        titleHologram.isCustomNameVisible = true
    }
}