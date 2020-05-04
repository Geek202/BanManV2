package me.geek.tom.banman.spigot.types.manual;

import com.google.common.collect.Lists;
import me.geek.tom.banman.spigot.types.BanTypeImpl;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class SixHour extends BanTypeImpl {

    public SixHour() {
        this.setSentence(1, Duration.of(6, ChronoUnit.HOURS));
    }

    @Override
    public ItemStack getIcon() {
        ItemStack icon = new ItemStack(Material.COAL_ORE);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Manual 6 hour ban");
        meta.setLore(Lists.newArrayList("Sr Staff+"));
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    public String getReason() {
        return "Manual 6 hour ban";
    }
}
