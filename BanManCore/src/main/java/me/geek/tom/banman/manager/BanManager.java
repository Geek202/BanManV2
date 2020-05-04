package me.geek.tom.banman.manager;

import me.geek.tom.banman.BanEntry;
import me.geek.tom.banman.web.BanManServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BanManager {

    private static BanManager INSTANCE = null;
    private Connection conn;
    private Statement statement;
    private BanManServer server;

    public static BanManager getInstance() {
        return INSTANCE;
    }

    public static void setInstance(BanManager manager) {
        INSTANCE = manager;
    }

    public void initialise(Connection db, int webApiPort) throws SQLException {
        this.conn = db;
        this.statement = db.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS bans(" +
                "PlayerUUID text," +
                "PlayerName text," +
                "Reason text," +
                "BannedUntil text," +
                "BannedBy text," +
                "BannedByName text," +
                "BannedOn text," +
                "IsPardoned bit," +
                "PardonedBy text," +
                "PardonedByName text," +
                "PardonedOn text" +
                ");";

        this.statement.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS users(" +
                "UUID text," +
                "Name text" +
                ");";

        this.statement.executeUpdate(sql);
        this.server = new BanManServer(webApiPort);
        this.server.run();
    }

    public void userCache(UUID uuid, String name) {
        try {
            ResultSet set = this.statement.executeQuery("SELECT Name FROM users WHERE UUID='" + uuid.toString() + "';");

            if (set.next())
                this.statement.executeUpdate("UPDATE users SET Name='" + name + "' WHERE UUID='" + uuid.toString() + "';");
            else
                this.statement.executeUpdate("INSERT INTO users VALUES('" + uuid.toString() + "','" + name + "');");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public BanEntry onPlayerJoin(UUID player) {
        List<BanEntry> bans = fetchBans(player);

        if (bans.size() > 0) { // The player has bans on their account.
            for (BanEntry ban : bans) { // Check each ban
                if (!ban.isPardoned()) { // Only 'active' bans
                    if (ban.isPerm())
                        return ban;

                    if (LocalDateTime.now().isBefore(ban.getExpiry())) {
                        return ban;
                    }
                }
            }
        }

        return null;
    }

    public String lookupUsername(UUID uuid) {
        try {
            ResultSet res = this.statement.executeQuery("SELECT Name FROM users WHERE UUID='" + uuid + "';");
            if (res.next())
                return res.getString("Name");
            else
                return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public List<BanEntry> fetchBans(UUID player) {
        String sql = "SELECT * FROM bans WHERE PlayerUUID=\"" + player + "\"";

        List<BanEntry> bans = new ArrayList<>();

        try {
            ResultSet results = this.statement.executeQuery(sql);
            while (results.next()) {
                // Parse the result
                String reason = results.getString("Reason");
                String bannedBy = results.getString("BannedBy");
                String bannedOnStr = results.getString("BannedOn");
                String playerName = results.getString("PlayerName");
                String bannedByName = results.getString("BannedByName");
                boolean isPardoned = results.getBoolean("IsPardoned");
                LocalDateTime bannedUntil = LocalDateTime.parse(results.getString("BannedUntil"), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

                UUID pardonedBy;
                String pardonedByName;
                LocalDateTime pardonedOn;

                if (isPardoned) {
                    pardonedBy = UUID.fromString(results.getString("PardonedBy"));
                    pardonedByName = results.getString("PardonedByName");
                    pardonedOn = LocalDateTime.parse(results.getString("PardonedOn"), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

                } else {
                    pardonedBy = null;
                    pardonedByName = null;
                    pardonedOn = null;
                }

                LocalDateTime bannedOn = LocalDateTime.parse(bannedOnStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

                // Build an entry.
                BanEntry entry = new BanEntry(
                        reason,
                        playerName,
                        UUID.fromString(bannedBy),
                        bannedByName,
                        player,
                        bannedUntil,
                        bannedOn,
                        isPardoned,
                        pardonedBy,
                        pardonedByName,
                        pardonedOn
                );
                bans.add(entry);
            }
        } catch (SQLException e) { // Failed, just return an empty list
            e.printStackTrace();
            return Collections.emptyList();
        }
        return bans;
    }

    public BanEntry banPlayer(UUID player, String playerName, String reason, LocalDateTime bannedUntil, LocalDateTime bannedOn, UUID bannedBy, String bannedByName) {

        String bannedOnStr = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(bannedOn);
        String bannedUntilStr = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(bannedUntil);

        String sql = "insert into bans (PlayerUUID,PlayerName,Reason,BannedUntil,BannedBy,BannedByName,BannedOn,IsPardoned,PardonedBy,PardonedByName,PardonedOn) values('" +
                player.toString() + "','" +
                playerName + "','" +
                reason + "','" +
                bannedUntilStr + "','" +
                bannedBy.toString() + "','" +
                bannedByName + "','" +
                bannedOnStr + "','" +
                false + "','" +
                "NULL','" +
                "NULL','" +
                "NULL" +
                "');";

        try {
            statement.executeUpdate(sql);

            return new BanEntry(
                    reason,
                    playerName,
                    bannedBy,
                    bannedByName,
                    player,
                    bannedUntil,
                    bannedOn,
                    false,
                    null,
                    null,
                    null
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateBanMessage(BanEntry entry, String RED, String YELLOW) {
        String expiry = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(entry.getExpiry());
        //LOGGER.info(player.getDisplayName() + " tried to join, but is banned!");
        return RED + "You are banned from this server for:\n" +
                YELLOW + entry.getReason() +
                RED + "\n You will be unbanned on:\n" +
                YELLOW + expiry;
    }

    public ResultSet query(String sql) throws SQLException {
        return this.statement.executeQuery(sql);
    }

    public void shutdown() {
        server.end();
    }
}
