package me.geek.tom.banman.web.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.geek.tom.banman.BanEntry;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class JsonUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static JsonObject createJsonError(int code, String message) {
        JsonObject ret = new JsonObject();
        ret.addProperty("code", code);
        ret.add("message", new JsonPrimitive(message));
        return ret;
    }

    public static JsonObject createUsernameResponse(String username, UUID uuid) {
        JsonObject object = new JsonObject();

        object.addProperty("username", username);
        object.addProperty("uuid", uuid.toString());

        return object;
    }

    public static JsonObject player(String name, UUID uuid) {
        JsonObject player = new JsonObject();
        player.addProperty("uuid", uuid.toString());
        player.addProperty("name", name);
        return player;
    }

    public static JsonObject serializeBanEntry(BanEntry entry) {
        JsonObject ret = new JsonObject();

        // Player
        ret.add("player", player(
                entry.getPlayerName(),
                entry.getPlayer()
        ));

        // Reason
        ret.addProperty("reason", entry.getReason());

        // Banned by
        ret.add("banner", player(
                entry.getBannedByName(),
                entry.getBannedBy()
        ));

        // Date/time
        ret.addProperty("bannedOn", FORMATTER.format(entry.getBannedOn()));
        ret.addProperty("expiry", FORMATTER.format(entry.getExpiry()));
        ret.addProperty("perm", entry.isPerm());

        // Pardon
        ret.addProperty("pardoned", entry.isPardoned());
        if (entry.isPardoned()) {
            JsonObject pardon = new JsonObject();

            pardon.add("pardoner", player(
                    entry.getPardenedByName(),
                    entry.getPardenedBy()
            ));

            pardon.addProperty("pardonedOn", FORMATTER.format(entry.getPardenedOn()));

            ret.add("pardon", pardon);
        }

        return ret;
    }

}
