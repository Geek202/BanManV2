package me.geek.tom.banman.spigot.command;

import me.geek.tom.banman.spigot.BanManSpigot;
import me.geek.tom.banman.spigot.guis.TempBanGui;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TempBanCommand implements CommandExecutor {

    private static final UUID consoleUUID = UUID.randomUUID();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(BanManSpigot.MESSAGE_PREFIX + ChatColor.RED + " Player not found!");
            return true;
        }

        if (sender instanceof Player)
            new TempBanGui(player, (Player) sender, BanManSpigot.INSTANCE).open((Player) sender);
        else {
            sender.sendMessage(BanManSpigot.MESSAGE_PREFIX + ChatColor.RED + " this command can only be run as a player.");
        }

        /*UUID uuid = sender instanceof Player ? ((Player) sender).getUniqueId() : consoleUUID;

        BanManSpigot.getManager().banPlayer(
                player.getUniqueId(),
                player.getName(),
                "You hath been banished!",
                LocalDateTime.now().plus(50, ChronoUnit.YEARS),
                LocalDateTime.now(),
                uuid,
                sender.getName()
        );*/
        
        return true;
    }
}
