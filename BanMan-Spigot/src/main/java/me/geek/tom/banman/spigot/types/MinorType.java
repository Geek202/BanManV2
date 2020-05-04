package me.geek.tom.banman.spigot.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;
import java.time.Period;

public class MinorType extends BanTypeImpl {
    public MinorType() {
        this.setSentence(1, Duration.ofHours(6));
        this.setSentence(2, Period.ofDays(1));
        this.setSentence(3, Period.ofDays(7));
        this.setSentence(4, Period.ofDays(14));
        this.setSentence(5, Period.ofDays(30));
        this.setSentence(6, perm());
    }

    @Override
    public ItemStack getIcon() {
        ItemStack stack = new ItemStack(Material.RED_BED);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Other minor offence");
        stack.setItemMeta(meta);
        return stack;
    }
    @Override
    public String getReason() {
        return "Minor offence";
    }
}
