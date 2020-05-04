package me.geek.tom.banman.spigot.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Period;

public class PvPType extends BanTypeImpl {
    public PvPType() {
        this.setSentence(1, Period.ofDays(14));
        this.setSentence(2, Period.ofDays(30));
        this.setSentence(3, perm());
    }
    @Override
    public ItemStack getIcon() {
        ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("KillAura/Crits/Other PvP");
        stack.setItemMeta(meta);
        return stack;
    }
    @Override
    public String getReason() {
        return "KillAura/Crits/Other PvP";
    }
}
