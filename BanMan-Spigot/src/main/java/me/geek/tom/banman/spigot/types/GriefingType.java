package me.geek.tom.banman.spigot.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Period;

public class GriefingType extends BanTypeImpl {
    public GriefingType() {
        this.setSentence(1, Period.ofDays(7));
        this.setSentence(2, Period.ofDays(14));
        this.setSentence(3, Period.ofDays(30));
        this.setSentence(4, perm());
    }
    @Override
    public ItemStack getIcon() {
        ItemStack stack = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Griefing");
        stack.setItemMeta(meta);
        return stack;
    }
    @Override
    public String getReason() {
        return "Griefing";
    }
}
