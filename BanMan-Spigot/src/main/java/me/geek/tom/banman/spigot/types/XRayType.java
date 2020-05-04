package me.geek.tom.banman.spigot.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Period;

public class XRayType extends BanTypeImpl {
    public XRayType() {
        this.setSentence(1, Period.ofDays(3));
        this.setSentence(2, Period.ofDays(7));
        this.setSentence(3, Period.ofDays(30));
        this.setSentence(4, perm());
    }
    @Override
    public ItemStack getIcon() {
        ItemStack stack = new ItemStack(Material.DIAMOND);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("XRAY");
        stack.setItemMeta(meta);
        return stack;
    }
    @Override
    public String getReason() {
        return "XRAY";
    }
}
