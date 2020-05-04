package me.geek.tom.banman.spigot.guis;

import me.geek.tom.banman.BanEntry;
import me.geek.tom.banman.manager.BanManager;
import me.geek.tom.banman.spigot.BanManSpigot;
import me.geek.tom.banman.spigot.PermsUtil;
import me.geek.tom.banman.spigot.types.BanType;
import me.geek.tom.banman.spigot.types.BanTypes;
import me.geek.tom.banman.spigot.types.manual.ManualBans;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TempBanGui extends InventoryGui {
    private final UUID playerToBan;
    private final UUID caller;
    private final String playerToBanName;
    private final String callerName;

    public TempBanGui(Player playerToBan, Player caller, Plugin plugin) {
        super("Baning " + playerToBan.getDisplayName(), PermsUtil.hasPerm(caller, "banman.manual") ? 18 : 9, plugin);
        this.playerToBan = playerToBan.getUniqueId();
        this.playerToBanName = playerToBan.getDisplayName();
        this.caller = caller.getUniqueId();
        this.callerName = caller.getDisplayName();
    }

    @Override
    public void populate() {
        setBackground("", new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        addBans(BanTypes.INSTANCE.types, 0);
        if (getSize() == 18) {
            addBans(ManualBans.INSTANCE.getTypes(), 9);
        }
    }

    private void addBans(List<BanType> types, int offset) {
        for (int i = 0; i < types.size(); i++) {
            BanType type = types.get(i);
            addButton(i + offset, type.getIcon().clone(), event -> {
                if (!PermsUtil.hasPerm(event.getPlayer(), "banman.silent")) {
                    banPlayer(type, event.getPlayer());
                    event.setWillClose(true);
                } else {
                    new PunishmentTypeGui(broadcast -> {
                        banPlayer(type, event.getPlayer());
                        if (broadcast)
                            Bukkit.getServer().broadcastMessage(BanManSpigot.MESSAGE_PREFIX + ChatColor.LIGHT_PURPLE + " Player '" + ChatColor.GREEN +
                                    event.getPlayer().getName() + ChatColor.LIGHT_PURPLE + "' banned '" + ChatColor.GREEN
                                    + this.playerToBanName + ChatColor.LIGHT_PURPLE + "' for '" + ChatColor.GREEN + type.getReason() + "'");
                    }, this.getPlugin()).open(event.getPlayer());
                }
            });
        }
    }

    private void banPlayer(BanType type, Player player) {
        List<BanEntry> bans = BanManSpigot.getManager().fetchBans(playerToBan);
        int count = 0;

        for (BanEntry ban : bans) {
            if (ban.getReason().equals(type.getReason())) {
                count++;
            }
        }
        count++;

        LocalDateTime bannedUntil;

        bannedUntil = LocalDateTime.now().plus(type.getDuration(count));

        BanEntry entry = BanManSpigot.getManager().banPlayer(this.playerToBan, this.playerToBanName, type.getReason(), bannedUntil, LocalDateTime.now(), this.caller, this.callerName);

        if (entry == null) {
            player.sendMessage(BanManSpigot.MESSAGE_PREFIX + " Failed to ban player: " + playerToBanName);
            return;
        }

        Player playerToBan = Bukkit.getServer().getPlayer(this.playerToBan);

        if (playerToBan != null) {
            playerToBan.getWorld().strikeLightningEffect(playerToBan.getLocation());
            playerToBan.chat("Oh poop I got banned!");
            playerToBan.kickPlayer(BanManager.generateBanMessage(entry, ChatColor.RED.toString(), ChatColor.YELLOW.toString()));
        }
    }
}
