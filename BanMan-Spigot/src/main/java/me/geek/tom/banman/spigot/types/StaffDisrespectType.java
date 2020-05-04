package me.geek.tom.banman.spigot.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Period;

public class StaffDisrespectType extends BanTypeImpl {
    public StaffDisrespectType() {
        this.setSentence(1, Period.ofDays(7));
        this.setSentence(2, Period.ofDays(30));
        this.setSentence(3, perm());
    }
    @Override
    public ItemStack getIcon() {
        ItemStack stack = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Staff disrespect/impersonation");
        stack.setItemMeta(meta);
        return stack;
    }
    @Override
    public String getReason() {
        return "Staff disrespect/impersonation";
    }
}
