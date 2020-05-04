package me.geek.tom.banman.spigot.types.manual;

import com.google.common.collect.Lists;
import me.geek.tom.banman.spigot.types.BanTypeImpl;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Period;

public class OneMonth extends BanTypeImpl {

    public OneMonth() {
        this.setSentence(1, Period.ofDays(30));
    }

    @Override
    public ItemStack getIcon() {
        ItemStack icon = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Manual 30 day ban");
        meta.setLore(Lists.newArrayList("Sr Staff+"));
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    public String getReason() {
        return "Manual 30 day ban";
    }
}
