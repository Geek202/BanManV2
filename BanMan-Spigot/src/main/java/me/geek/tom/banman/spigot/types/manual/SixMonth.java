package me.geek.tom.banman.spigot.types.manual;

import com.google.common.collect.Lists;
import me.geek.tom.banman.spigot.types.BanTypeImpl;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Period;

public class SixMonth extends BanTypeImpl {

    public SixMonth() {
        this.setSentence(1, Period.ofMonths(6));
    }

    @Override
    public ItemStack getIcon() {
        ItemStack icon = new ItemStack(Material.EMERALD_ORE);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Manual 6 month ban");
        meta.setLore(Lists.newArrayList("Sr Staff+"));
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    public String getReason() {
        return "Manual 6 month ban";
    }
}
