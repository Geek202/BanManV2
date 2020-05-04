package me.geek.tom.banman.spigot.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.geek.tom.banman.spigot.BanManSpigot.MESSAGE_PREFIX;
import static me.geek.tom.banman.spigot.BanManSpigot.VERSION;

public class BanManCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(MESSAGE_PREFIX + " Running version " + ChatColor.YELLOW + VERSION);

        return true;
    }
}
