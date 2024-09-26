package org.raphaelstats.raphaelStatistics

import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.runBlocking
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.raphaelstats.raphaelStatistics.Raphael_Statistics.Companion.supabase


public class VerifyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.isOp) {
            sender.sendMessage("You must be an operator to use this command!")
            return true
        }

        var verifyKey = args?.get(0)?.split("-")
        var emailNamespace = verifyKey?.get(0) + "-" + verifyKey?.get(1)
        var email = emailNamespace + "@raphael.com"
        var password = args?.get(0)?.removePrefix(emailNamespace + "-")

        sender.sendMessage("$verifyKey\n$emailNamespace\n$email\n$password")

        if(email != null && password != null) {

            Signin(email, password)
            sender.sendMessage("You are verified UwU!")
        } else {
            sender.sendMessage("Wrong verification code UmU!")
        }

        return true;
    }

    private fun Signin(useremail: String, userpassword: String) = runBlocking{
        supabase.auth.signInWith(Email) {
            email = useremail
            password = userpassword
        }
    }

}