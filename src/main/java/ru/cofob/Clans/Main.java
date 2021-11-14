package ru.cofob.Clans;

import ru.cofob.PlaceholderAPIHook.ClipPlaceholder;
import ru.cofob.PlaceholderAPIHook.MaximvdwPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main m;
    private ClanConfiguration clanConfiguration;
    private ClanCommand clanCommand;
    private String prefix = "§7[§cClans§7] ";
    private PlayerClanHandler playerClanHandler;

    public Main() {
    }

    public void onEnable() {
        m = this;
        this.setClanConfiguration(new ClanConfiguration());
        Bukkit.getPluginManager().registerEvents(new ClientListener(), this);
        Bukkit.getPluginManager().registerEvents(this.playerClanHandler = new PlayerClanHandler(), this);
        this.getCommand("clan").setExecutor(this.clanCommand = new ClanCommand());
        ClientListener.launchBaseTeleporter();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            (new ClipPlaceholder()).load();
        }

        if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") != null) {
            (new MaximvdwPlaceholder()).load();
        }

    }

    public void onDisable() {
    }

    public static Main getPlugin() {
        return m;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ClanConfiguration getClanConfiguration() {
        return this.clanConfiguration;
    }

    public void setClanConfiguration(ClanConfiguration clanConfiguration) {
        this.clanConfiguration = clanConfiguration;
    }

    public ClanCommand getClanCommand() {
        return this.clanCommand;
    }

    public PlayerClanHandler getPlayerClanHandler() {
        return this.playerClanHandler;
    }
}
