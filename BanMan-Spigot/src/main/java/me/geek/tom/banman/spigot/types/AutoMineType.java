package me.geek.tom.banman.spigot.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Period;

public class AutoMineType extends BanTypeImpl {
    public AutoMineType() {
        this.setSentence(1, Period.ofDays(30));
        this.setSentence(2, perm());
    }
    @Override
    public ItemStack getIcon() {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Auto mine/Other clicker");
        stack.setItemMeta(meta);
        return stack;
    }
    @Override
    public String getReason() {
        return "Auto mine/Other clicker";
    }
}
