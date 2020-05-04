package me.geek.tom.banman.spigot.types.manual;

import com.google.common.collect.Lists;
import me.geek.tom.banman.spigot.types.BanTypeImpl;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Period;

public class OneDay extends BanTypeImpl {

    public OneDay() {
        this.setSentence(1, Period.ofDays(1));
    }

    @Override
    public ItemStack getIcon() {
        ItemStack icon = new ItemStack(Material.IRON_ORE);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Manual one day ban");
        meta.setLore(Lists.newArrayList("Sr Staff+"));
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    public String getReason() {
        return "Manual one day ban";
    }
}
