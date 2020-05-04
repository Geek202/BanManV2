package me.geek.tom.banman.spigot.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Period;

public class ChatType extends BanTypeImpl {
    public ChatType() {
        this.setSentence(1, Period.ofDays(1));
        this.setSentence(2, Period.ofDays(7));
        this.setSentence(3, Period.ofDays(14));
        this.setSentence(4, Period.ofDays(30));
        this.setSentence(5, perm());
    }
    @Override
    public ItemStack getIcon() {
        ItemStack stack = new ItemStack(Material.PAPER);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Chat spam/Offencive language");
        stack.setItemMeta(meta);
        return stack;
    }
    @Override
    public String getReason() {
        return "Chat spam/Offencive language";
    }
}
