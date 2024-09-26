package org.raphaelstats.raphaelStatistics

import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit.getLogger
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.*
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.entity.Entity
import org.bukkit.event.EventPriority
import org.raphaelstats.raphaelStatistics.Raphael_Statistics.Companion.supabase
import java.text.SimpleDateFormat
import java.util.*

public class EventListener() : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun OnEntityDeath(event: EntityDeathEvent?) {
        if (event == null) return;

        var currentTime = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().time)
        var UUID: String = event.entity.uniqueId.toString()
        var name = event.entity.name
        var entityType = getEntityType(event.entity)
        var deathType = event.damageSource.damageType.key
        var killedBy = event.damageSource.causingEntity
        var enemyType: Int = getEntityType(event.damageSource.causingEntity)
        var weapon = event.damageSource.directEntity?.name

        var identifier = if(entityType == 1) UUID else name
        var enemyIdentifier = if(enemyType == 1) killedBy?.uniqueId else killedBy?.name

        var Data = "$entityType;$identifier;$enemyType;$enemyIdentifier;$deathType;$weapon";

        getLogger().info("\n\tEntityDeathTime: $currentTime\n\tKilled: $identifier\n\tType: $deathType\n\tKilledBy: $enemyIdentifier")

        CreateRow(currentTime, 0, Data)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun OnPlayerJoin(event: PlayerJoinEvent?) {
        if (event == null) return;

        var currentTime = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().time)
        var UUID: String = event.player.uniqueId.toString()
        var name = event.player.name
        var Data = "$UUID";

        getLogger().info("\n\tJoinTime: $currentTime\n\tName: $UUID ($name)")

        CreateRow(currentTime,1, Data)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun OnPlayerQuit(event: PlayerQuitEvent?) {
        if (event == null) return;

        var currentTime = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().time)
        var UUID: String = event.player.uniqueId.toString()
        var name = event.player.name
        var Data = "$UUID";

        getLogger().info("\n\tDisconnectTime: $currentTime\n\tName: $UUID ($name)")

        CreateRow(currentTime,2, Data)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun OnPlayerAchievement(event: PlayerAdvancementDoneEvent?) {
        if (event == null) return;
        if (event.advancement.key.key.contains("recipes")) return;

        var currentTime = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().time)
        var UUID: String = event.player.uniqueId.toString()
        var name = event.player.name
        var achievement = event.advancement.key.key
        var Data = "$UUID;$achievement";

        getLogger().info("\n\tAchievementTime: $currentTime\n\tName: $UUID ($name)\n\tAchievement: $achievement")

        CreateRow(currentTime,3, Data)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun OnPlayerComment(event: AsyncChatEvent?) {
        if (event == null) return;

        var currentTime = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().time)
        var UUID: String = event.player.uniqueId.toString()
        var name = event.player.name
        var Data = "$UUID";

        getLogger().info("\n\tCommentTime: $currentTime\n\tName: $UUID ($name)")

        CreateRow(currentTime, 4, Data)
    }

    private fun getEntityType(entity: Entity?): Int {
        if (entity == null) {
            return 3
        } else if (entity.type == EntityType.PLAYER) {
            return 1
        } else {
            return 2
        }
        return 0
    }

    private fun CreateRow(Time: String, Type: Int, Data: String) = runBlocking {
        var user = supabase.auth.currentUserOrNull();
        if (user != null) {
            val dataRow = DBData(user.id, Type, Time, Data)
            supabase.from("DataFetch").insert(dataRow)
        }
    }
}