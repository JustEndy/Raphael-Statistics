package org.raphaelstats.raphaelStatistics

import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit.getLogger
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.raphaelstats.raphaelStatistics.Raphael_Statistics.Companion.supabase


public class EventListener() : Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent?) {
        if (event == null) return;

        var currentTime = java.util.Calendar.getInstance().time
        var deathMsg = PlainTextComponentSerializer.plainText().serialize(event.deathMessage()!!)
        var UUID: String = event.entity.uniqueId.toString()
        var name = event.entity.name
        var deathType = event.damageSource.damageType.key
        var killedBy = event.damageSource.causingEntity?.name
        var enemyType: Int = getEnemyType(event)
        var weapon = event.damageSource.directEntity?.name

        var Data = "$UUID;$enemyType;$deathType;$killedBy;$weapon;";

        getLogger().info("\n\tPlayerDeathTime: $currentTime\n\tFullMessage: $deathMsg\n\tKilled: $UUID ($name)\n\tType: $deathType\n\tKilledBy: $killedBy")

        CreateRow(0, Data)
    }

    @EventHandler
    fun OnEntityDeath(event: EntityDeathEvent?) {
        if (event == null) return;

        var currentTime = java.util.Calendar.getInstance().time
        var UUID: String = event.entity.uniqueId.toString()
        var name = event.entity.name
        var deathType = event.damageSource.damageType.key
        var killedBy = event.damageSource.causingEntity?.name
        var enemyType: Int = getEnemyType(event)
        var weapon = event.damageSource.directEntity?.name

        var Data = "$UUID;$enemyType;$deathType;$killedBy;$weapon;";

        getLogger().info("\n\tEntityDeathTime: $currentTime\n\tKilled: $UUID ($name)\n\tType: $deathType\n\tKilledBy: $killedBy")

        CreateRow(1, Data)
    }

    @EventHandler
    fun OnPlayerConnection(event: PlayerJoinEvent?) {
        if (event == null) return;

        var UUID: String = event.player.uniqueId.toString()
        var name = event.player.name
        var currentTime = java.util.Calendar.getInstance().time
        var Data = "$UUID";

        getLogger().info("\n\tJoinTime: $currentTime\n\tName: $UUID ($name)")

        CreateRow(2, Data)
    }

    private fun getEnemyType(event: EntityDeathEvent?): Int {
        if (event != null) {
            if (event.damageSource.causingEntity == null) {
                return 3
            } else if (event.damageSource.causingEntity?.type == EntityType.PLAYER) {
                return 1
            } else {
                return 2
            }
        }
        return 0
    }

    private fun CreateRow(Type: Int, Data: String) = runBlocking {
        val dataRow = DBData(Type, Data)
//        launch { // launch a new coroutine and continue
//            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
//            println("World!") // print after delay
//        }
        supabase.from("DataFetch").insert(dataRow)
    }
}