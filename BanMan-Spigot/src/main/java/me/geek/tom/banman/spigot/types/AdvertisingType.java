package me.geek.tom.banman.spigot.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;
import java.time.Period;

import static java.time.temporal.ChronoUnit.HOURS;

public class AdvertisingType extends BanTypeImpl {
    public AdvertisingType() {
        this.setSentence(1, Duration.of(12, HOURS));
        this.setSentence(2, Period.ofDays(1));
        this.setSentence(3, Period.ofDays(7));
        this.setSentence(4, Period.ofMonths(1));
        this.setSentence(5, perm());
    }

    @Override
    public ItemStack getIcon() {
        ItemStack stack = new ItemStack(Material.BIRCH_SIGN);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Advertising");
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public String getReason() {
        return "Advertising";
    }
}
