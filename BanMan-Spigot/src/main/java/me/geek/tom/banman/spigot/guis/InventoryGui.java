package me.geek.tom.banman.spigot.guis;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;


/**
 * A inventory GUI implementation based on https://bukkit.org/threads/icon-menu.108342/
 * but improved and adapted for modern Bukkit versions.
 */
public abstract class InventoryGui implements Listener {

    private String name;
    private int size;
    private Plugin plugin;

    private List<Inventory> activeInventories = Lists.newArrayList();
    private String[] optionNames;
    private ItemStack[] optionIcons;
    private ButtonHandler[] handlers;

    public InventoryGui(String name, int size, Plugin plugin) {
        this.name = name;
        this.size = size;
        this.plugin = plugin;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        this.handlers = new ButtonHandler[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        populate();
    }

    public int getSize() {
        return size;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public abstract void populate();

    public void setBackground(String name, ItemStack stack) {
        for (int i = 0; i < this.optionIcons.length; i++) {
            if (optionIcons[i] == null) {
                ItemStack stack1 = stack.clone();
                ItemMeta meta = stack1.getItemMeta();
                meta.setDisplayName(name);
                stack1.setItemMeta(meta);
                optionIcons[i] = stack1;
                optionNames[i] = name;
            }
        }
    }

    public void addButton(int slot, ItemStack stack, ButtonHandler handler) {
        this.optionIcons[slot] = stack;
        this.optionNames[slot] = stack.getItemMeta().getDisplayName();
        this.handlers[slot] = handler;
    }

    public void addButton(int x, int y, ItemStack stack, ButtonHandler handler) {
        int slot = (y * 9) + x;
        addButton(slot, stack, handler);
    }

    public void addButton(int x, int y, ItemStack stack, ButtonHandler handler, String name, String... lore) {
        addButton(x, y, setItemNameAndLore(stack, name, lore), handler);
    }

    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        activeInventories.add(inventory);
        player.openInventory(inventory);
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        for (Inventory inv : activeInventories) {
            if (event.getInventory().equals(inv)) {
                event.setCancelled(true);
                int slot = event.getRawSlot();
                if (slot >= 0 && slot < size && optionNames[slot] != null) {
                    final Player p = (Player) event.getWhoClicked();
                    if (handlers[slot] != null) {
                        Plugin plugin = this.plugin;
                        OptionClickEvent e = new OptionClickEvent((Player) event.getWhoClicked(), slot, optionNames[slot]);
                        handlers[slot].onButton(e);
                        if (e.willClose()) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, p::closeInventory, 1);
                        }
                        if (e.willDestroy()) {
                            destroy();
                        }
                    } else {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, .1f);
                    }
                }
            }
        }
    }

    public interface ButtonHandler {
        void onButton(OptionClickEvent e);
    }

    public static class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;

        public OptionClickEvent(Player player, int position, String name) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = false;
            this.destroy = false;
        }

        public Player getPlayer() {
            return player;
        }

        public int getPosition() {
            return position;
        }

        public String getName() {
            return name;
        }

        public boolean willClose() {
            return close;
        }

        public boolean willDestroy() {
            return destroy;
        }

        public void setWillClose(boolean close) {
            this.close = close;
        }

        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }
    }
}
