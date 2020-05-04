package me.geek.tom.banman.spigot.event;

import me.geek.tom.banman.BanEntry;
import me.geek.tom.banman.manager.BanManager;
import me.geek.tom.banman.spigot.BanManSpigot;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        System.out.println("Player attempting to login: " + event.getName() + " (" + event.getUniqueId() + ")");

        BanManager manager = BanManSpigot.getManager();

        manager.userCache(event.getUniqueId(), event.getName());

        BanEntry result = manager.onPlayerJoin(event.getUniqueId());
        if (result == null)
            return;

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, BanManager.generateBanMessage(result, ChatColor.RED.toString(), ChatColor.YELLOW.toString()));
    }

}
