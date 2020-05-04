package me.geek.tom.banman;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class BanEntry {
    private final String reason;
    private final String playerName;
    private final UUID bannedBy;
    private final String bannedByName;
    private final UUID player;
    private final LocalDateTime expiry;
    private final boolean isPerm;
    private final LocalDateTime bannedOn;
    private final boolean isPardoned;
    private final UUID pardenedBy;
    private final String pardenedByName;
    private final LocalDateTime pardenedOn;

    public BanEntry(String reason, String playerName, UUID bannedBy, String bannedByName, UUID player,
                    LocalDateTime expiry, LocalDateTime bannedOn, boolean isPardoned, UUID pardenedBy,
                    String pardenedByName, LocalDateTime pardenedOn) {
        this.reason = reason;
        this.playerName = playerName;
        this.bannedBy = bannedBy;
        this.bannedByName = bannedByName;
        this.player = player;
        this.expiry = expiry;
        this.isPerm = Duration.between(bannedOn, expiry).minusDays(18250).isZero(); // 50years = perm ban;
        this.bannedOn = bannedOn;
        this.isPardoned = isPardoned;
        this.pardenedBy = pardenedBy;
        this.pardenedByName = pardenedByName;
        this.pardenedOn = pardenedOn;
    }

    public String getReason() {
        return reason;
    }

    public String getPlayerName() {
        return playerName;
    }

    public UUID getBannedBy() {
        return bannedBy;
    }

    public String getBannedByName() {
        return bannedByName;
    }

    public UUID getPlayer() {
        return player;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public boolean isPerm() {
        return isPerm;
    }

    public LocalDateTime getBannedOn() {
        return bannedOn;
    }

    public boolean isPardoned() {
        return isPardoned;
    }

    public UUID getPardenedBy() {
        return pardenedBy;
    }

    public String getPardenedByName() {
        return pardenedByName;
    }

    public LocalDateTime getPardenedOn() {
        return pardenedOn;
    }
}
