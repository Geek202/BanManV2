package me.geek.tom.banman.spigot.types.manual;

import com.google.common.collect.Lists;
import me.geek.tom.banman.spigot.types.BanTypeImpl;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PermBan  extends BanTypeImpl {

    public PermBan() {
        this.setSentence(1, this.perm());
    }

    @Override
    public ItemStack getIcon() {
        ItemStack icon = new ItemStack(Material.BEDROCK);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Manual PERM BAN!");
        meta.setLore(Lists.newArrayList("Sr Staff+"));
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    public String getReason() {
        return "Manually " + ChatColor.BOLD + "PERM BANNED!" + ChatColor.RESET;
    }
}
