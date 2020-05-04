package me.geek.tom.banman.spigot.guis;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

public class PunishmentTypeGui extends InventoryGui {

    private Consumer<Boolean> banMethod;

    /**
     * Creates a new GUI
     * @param banMethod The method to call when the user clicks. true will be passed if the ban should be broadcast.
     * @param plugin The plugin that is showing the call.
     */
    public PunishmentTypeGui(Consumer<Boolean> banMethod, Plugin plugin) {
        super("Punishment type", 27, plugin);
        this.banMethod = banMethod;
    }

    @Override
    public void populate() {
        setBackground(" ", new ItemStack(Material.BLUE_STAINED_GLASS_PANE));
        addButton(2, 1, new ItemStack(Material.GREEN_CONCRETE), event -> {
            banMethod.accept(true);
            event.setWillClose(true);
        }, "Normal punishment", "Broadcasts the ban to the entire server");
        addButton(6, 1, new ItemStack(Material.BLUE_CONCRETE), event -> {
            banMethod.accept(false);
            event.setWillClose(true);
        }, "Silent punishment", "Supresses the ban broadcast");
    }
}
