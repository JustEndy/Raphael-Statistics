package org.raphaelstats.raphaelStatistics


import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit.getLogger
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.json.simple.JSONObject


public class EventListener : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent?) {
        getLogger().info("Somebody died UmU!")
        if (event != null) {
            var deathMsg = PlainTextComponentSerializer.plainText().serialize(event.deathMessage()!!)
            var UUID: String = event.entity.uniqueId.toString()
            var type = JSONObject(event.deathMessage().toString())

            var killedBy = event.damageSource.causingEntity?.name
            getLogger().info("FullMessage: $deathMsg\nKilled: $UUID\nType: $type\nKilledBy: $killedBy")
        }
    }
}