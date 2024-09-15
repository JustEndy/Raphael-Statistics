package org.raphaelstats.raphaelStatistics

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
//import kotlinx.datetime.LocalDateTime
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit.getLogger
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent


public class EventListener() : Listener {

    @EventHandler
    suspend fun onPlayerDeath(event: PlayerDeathEvent?) {
        //getLogger().info("Somebody died UmU!")
        if (event != null) {
            var deathMsg = PlainTextComponentSerializer.plainText().serialize(event.deathMessage()!!)
            var UUID: String = event.entity.uniqueId.toString()
            var name = event.entity.name
            var type = event.damageSource.damageType.key
            var killedBy = event.damageSource.causingEntity?.name

            getLogger().info("\n\tFullMessage: $deathMsg\n\tKilled: $UUID ($name)\n\tType: $type\n\tKilledBy: $killedBy")

            //CreateRow(supabase)
        }
    }

    private suspend fun CreateRow() {
        //val dataRow = DBData(Time = LocalDateTime, Data = "Cheese")
        //supabase.from("DataFetch").insert(dataRow)
    }
}