package dev.emortal.munchcrunch.leaderboard

import dev.emortal.munchcrunch.MethodHolder
import dev.emortal.munchcrunch.database.DBCache
import dev.emortal.munchcrunch.database.DatabaseUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.metadata.other.AreaEffectCloudMeta
import net.minestom.server.instance.Instance
import java.util.*

class Leaderboard(val id: UUID,
                  val game: String,
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
        DatabaseUtil.mainDB!!.getTable(game, stat, orderStyle, top, dbCache,
            object : MethodHolder() {
                override fun codeToRun(){
                    println("Got initial table")
                }
            }
        )
    }
    fun spawn(){

        var tempEntity = Entity(EntityType.AREA_EFFECT_CLOUD)
        tempEntity.setInstance(instance, position)
        val temp_areaEffectCloudMeta = tempEntity.entityMeta as AreaEffectCloudMeta
        temp_areaEffectCloudMeta.radius = 0f

        for (row in dbCache.table.reversed()){
            if(row == dbCache.table[0]) continue

            val entity = Entity(EntityType.AREA_EFFECT_CLOUD)
            val areaEffectCloudMeta = entity.entityMeta as AreaEffectCloudMeta
            areaEffectCloudMeta.radius = 0f
            entity.setInstance(instance)
            entity.isCustomNameVisible = true
            entity.isInvisible = true
            entity.setGravity(0.0, 0.0)
            armorstands.add(entity)


            entity.customName = Component.text(
                "${
                    MinecraftServer.getConnectionManager()
                        .getPlayer(UUID.fromString(row.values[0] as String?))?.username
                }: ",
                NamedTextColor.GOLD)
                .append(
                    Component.text("${row.values[columnInt]}", NamedTextColor.YELLOW)
                )
            entity.teleport(tempEntity.position.withY(tempEntity.position.y() + 0.3))
            tempEntity = entity
        }
    }

    fun isReadyToRefresh(): Boolean {
        return lastRefreshed + (refresh*1000) < System.currentTimeMillis()
    }

    fun refresh() {
        lastRefreshed = System.currentTimeMillis()
        DatabaseUtil.mainDB!!.getTable(game, stat, orderStyle, top, dbCache,
            object : MethodHolder() {
                override fun codeToRun(){

                }
            }
        )
        //TODO actually refresh
    }
}