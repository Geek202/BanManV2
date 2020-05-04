package me.geek.tom.banman.spigot;

import me.geek.tom.banman.manager.BanManager;
import me.geek.tom.banman.spigot.command.TempBanCommand;
import me.geek.tom.banman.spigot.command.BanManCommand;
import me.geek.tom.banman.spigot.event.EventListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BanManSpigot extends JavaPlugin {

    public static BanManSpigot INSTANCE = null;
    private static BanManager manager = null;

    public static BanManager getManager() {
        if (manager == null) {
            BanManager.setInstance(new BanManager());
            manager = BanManager.getInstance();
        }
        return manager;
    }

    public static final String MESSAGE_PREFIX = ChatColor.GRAY + "[" + ChatColor.BLUE + "Ban" + ChatColor.LIGHT_PURPLE + "Man" + ChatColor.GRAY + "]" + ChatColor.RESET;

    public static String VERSION = "";

    private File dbFile;
    private Connection connection;
    //private Statement statement;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();
        dbFile = new File(getDataFolder(), "bans.db");

        VERSION = getDescription().getVersion();
        INSTANCE = this;

        saveDefaultConfig();

        FileConfiguration config = getConfig();
        config.addDefault("web-api-port", 3080);
        config.options().copyDefaults(true);
        saveConfig();

        PermsUtil.load();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbFile);
            getManager().initialise(connection, config.getInt("web-api-port"));
        } catch (ClassNotFoundException | SQLException e) {
            getLogger().severe("FALIED TO CONNECT TO DATABASE! THIS PLUGIN WILL NOW BE DISABLED!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }

        getCommand("banman").setExecutor(new BanManCommand());
        getCommand("tempban").setExecutor(new TempBanCommand());
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
