package dev.emortal.munchcrunch.leaderboard

import dev.emortal.munchcrunch.MethodHolder
import dev.emortal.munchcrunch.database.DBCache
import dev.emortal.munchcrunch.database.DatabaseUtil
import net.kyori.adventure.text.Component
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.metadata.other.ArmorStandMeta
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
                  private val dbCache: DBCache = DBCache(),
                  private val columnInt: Int /* will make this automatic later, just too lazy rn */
) {
    private var lastRefreshed = System.currentTimeMillis()
    val armorstands = mutableListOf<Entity>()
    init {
        DatabaseUtil.mainDB!!.getTable("testtable3", stat, "DESC", top, dbCache,
            object : MethodHolder() {
                override fun codeToRun(){
                    println("WAIT THERES NO WAY THIS WILL WORK")
                }
            }
        )
    }
    fun spawn(){
        println("work?")
        val titleHologram = Entity(EntityType.ARMOR_STAND)
        titleHologram.customName = Component.text(title)
        titleHologram.isCustomNameVisible = true
        var tempEntity = Entity(EntityType.ARMOR_STAND)
        tempEntity.setInstance(instance, position)
        for (row in dbCache.table){
            val entity = Entity(EntityType.ARMOR_STAND)
            val armorStandMeta = entity.entityMeta as ArmorStandMeta
            entity.setInstance(instance, position)
            armorStandMeta.isSmall = true
            entity.isCustomNameVisible = true
            entity.customName = Component.text("${row.values[0]} ${row.values[columnInt]}")
            entity.isInvisible = true
            entity.setGravity(0.0, 0.0)
            entity.teleport(tempEntity.position.withY(tempEntity.position.y() + 0.3))
            tempEntity = entity
            armorstands.add(entity)
        }
        //TODO spawn and stack armorstands
    }

    fun isReadyToRefresh(): Boolean {
        return lastRefreshed + (refresh*1000) < System.currentTimeMillis()
    }

    fun refresh() {
        lastRefreshed = System.currentTimeMillis()
        DatabaseUtil.mainDB!!.getTable("testtable3", stat, "DESC", top, dbCache,
            object : MethodHolder() {
                override fun codeToRun(){
                    println("WAIT THERES NO WAY THIS WILL WORK")
                }
            }
        )
        //TODO actually refresh
        println(refresh)
    }
}