package me.geek.tom.banman.spigot.types;

import org.bukkit.inventory.ItemStack;

import java.time.Period;
import java.time.temporal.TemporalAmount;

public abstract class BanType {
    // Set the sentence for the given offence count
    public abstract void setSentence(int count, TemporalAmount duration);

    // Throws an error if count is invalid
    public abstract TemporalAmount getDuration(int count) throws IllegalArgumentException;

    // Perm ban duration
    public TemporalAmount perm() {
        return Period.ofDays(50 * 365);
    }

    // Get the itemstack to use for the ban icon
    public abstract ItemStack getIcon();

    // Get the reason for the ban
    public abstract String getReason();
}
