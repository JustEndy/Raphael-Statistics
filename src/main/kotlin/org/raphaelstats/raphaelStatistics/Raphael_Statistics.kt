package org.raphaelstats.raphaelStatistics

import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import org.bukkit.plugin.java.JavaPlugin

class Raphael_Statistics : JavaPlugin() {

    companion object {
        lateinit var supabase: SupabaseClient;
    }

    override fun onEnable() {
        getLogger().info("Hello World UwU!")

//        val client = HttpClient(Java)
//
        supabase = createSupabaseClient(
            "https://xpuhmrctgbzimvdpfwks.supabase.co/",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhwdWhtcmN0Z2J6aW12ZHBmd2tzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU4Njg2MjksImV4cCI6MjA0MTQ0NDYyOX0.3SBbL58ZXv-WC2wQLr1yyB55ERrQ26UogocvHZrvCw8"
        ) {
            install(Auth)
            install(Postgrest)
            //install other modules
        }

        getCommand("verify")!!.setExecutor(VerifyCommand())
        server.pluginManager.registerEvents(EventListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }



}
