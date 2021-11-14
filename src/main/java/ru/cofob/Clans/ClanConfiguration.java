package ru.cofob.Clans;

import ru.cofob.Clans.MojangHandler.MojangProfileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ClanConfiguration {
    private String chatFormat;

    private String clanNameMatcher;

    private boolean chatEnabled;

    private boolean customChatEnabled;

    private boolean permissionEnabled;

    private boolean damageEnabled;

    private boolean selfDamageEnabled;

    private boolean baseTeleportEnabled;

    private boolean baseTeleportMoveEnabled;

    private boolean setBaseEnabled;

    private boolean clanLimitEnabled;

    private boolean soundEnabled;

    private boolean vaultEnabled;

    private boolean onlineMode;

    private List<String> clanColors;

    private int clanNameLength;

    private int baseTeleportCooldown;

    private int setBaseCooldown;

    private int clanLimit;

    private double clanCreatePrice;

    private Messages messages;

    private FileConfiguration configuration;

    private List<String> enabledWorlds = new ArrayList<String>();

    private List<String> blacklistedNames = new ArrayList<String>();

    public static Map<String, Integer> topClans = new HashMap<String, Integer>();

    public ClanConfiguration() {
        File dir = Main.getPlugin().getDataFolder();
        if (!dir.exists())
            dir.mkdirs();
        File settings = new File(Main.getPlugin().getDataFolder(), "Settings");
        if (!settings.exists())
            settings.mkdirs();
        this.messages = new Messages();
        Main.getPlugin().setPrefix(this.messages.getMessage("Prefix"));
        File config = new File(Main.getPlugin().getDataFolder(), "Settings/config.yml");
        this.configuration = (FileConfiguration)YamlConfiguration.loadConfiguration(config);
        this.configuration.options().copyDefaults(true);
        this.configuration.addDefault("ClanNameLength", Integer.valueOf(5));
        this.configuration.addDefault("ChatFormatEnabled", Boolean.valueOf(true));
        this.configuration.addDefault("ChatFormat", "%clan% &8* %player%&7: &f%message%");
        this.configuration.addDefault("CustomChatFormatEnabled", Boolean.valueOf(false));
        this.configuration.addDefault("PermissionEnabled", Boolean.valueOf(false));
        this.configuration.addDefault("PermissionClanCreate", "clans.create");
        this.configuration.addDefault("PermissionClanInvite", "clans.invite");
        this.configuration.addDefault("PermissionClanAccept", "clans.accept");
        this.configuration.addDefault("PermissionClanKick", "clans.kick");
        this.configuration.addDefault("PermissionClanStats", "clans.stats");
        this.configuration.addDefault("PermissionClanTopStats", "clans.topstats");
        this.configuration.addDefault("PermissionClanBase", "clans.base");
        this.configuration.addDefault("PermissionClanSetBase", "clans.setbase");
        this.configuration.addDefault("PermissionClanLeave", "clans.leave");
        this.configuration.addDefault("PermissionClanDelete", "clans.delete");
        this.configuration.addDefault("PermissionClanList", "clans.list");
        this.configuration.addDefault("PermissionClansReload", "clans.reload");
        this.configuration.addDefault("DamageEnabled", Boolean.valueOf(false));
        this.configuration.addDefault("SelfDamageEnabled", Boolean.valueOf(false));
        this.configuration.addDefault("ClanColors", Arrays.asList(new String[] { "0-99:&7", "100-299:&a", "500:&c" }));
        this.configuration.addDefault("BaseTeleportEnabled", Boolean.valueOf(true));
        this.configuration.addDefault("BaseTeleportMoveEnabled", Boolean.valueOf(false));
        this.configuration.addDefault("SetBaseEnabled", Boolean.valueOf(false));
        this.configuration.addDefault("BaseTeleportCooldown", Integer.valueOf(5));
        this.configuration.addDefault("SetBaseCooldown", Integer.valueOf(0));
        this.configuration.addDefault("ClanLimitEnabled", Boolean.valueOf(false));
        this.configuration.addDefault("ClanLimit", Integer.valueOf(10));
        this.configuration.addDefault("ClanNameMatcher", "^[a-zA-Z0-9]*$");
        this.configuration.addDefault("VaultSupport.Enabled", Boolean.valueOf(false));
        this.configuration.addDefault("VaultSupport.ClanCreatePrice", Double.valueOf(100.0D));
        this.configuration.addDefault("Sounds.Enabled", Boolean.valueOf(false));
        this.configuration.addDefault("Sounds.ClanEnter", "BLOCK_IRON_DOOR_OPEN");
        this.configuration.addDefault("Sounds.ClanLeave", "BLOCK_IRON_DOOR_CLOSE");
        this.configuration.addDefault("Sounds.ClanRankup", "ENTITY_PLAYER_LEVELUP");
        this.configuration.addDefault("Sounds.BaseTeleport", "BLOCK_PORTAL_AMBIENT");
        this.configuration.addDefault("SpecificWorlds.Enabled", Boolean.valueOf(false));
        this.configuration.addDefault("BlacklistedClanNames", Arrays.asList(new String[] { "racist", "fuck" }));
        List<String> worldList = new ArrayList<String>();
        for (World world : Bukkit.getWorlds())
            worldList.add(world.getName());
        this.configuration.addDefault("SpecificWorlds.List", worldList);
        this.configuration.addDefault("OnlineMode", Boolean.valueOf(true));
        FileUtils.save(config, this.configuration);
        this.soundEnabled = this.configuration.getBoolean("Sounds.Enabled");
        this.customChatEnabled = this.configuration.getBoolean("CustomChatFormatEnabled");
        this.chatEnabled = this.configuration.getBoolean("ChatFormatEnabled");
        this.chatFormat = ChatColor.translateAlternateColorCodes('&', this.configuration.getString("ChatFormat"));
        this.clanNameMatcher = this.configuration.getString("ClanNameMatcher");
        this.permissionEnabled = this.configuration.getBoolean("PermissionEnabled");
        this.clanColors = this.configuration.getStringList("ClanColors");
        this.clanNameLength = this.configuration.getInt("ClanNameLength");
        this.damageEnabled = this.configuration.getBoolean("DamageEnabled");
        this.selfDamageEnabled = this.configuration.getBoolean("SelfDamageEnabled");
        this.baseTeleportEnabled = this.configuration.getBoolean("BaseTeleportEnabled");
        this.baseTeleportMoveEnabled = this.configuration.getBoolean("BaseTeleportMoveEnabled");
        this.setBaseEnabled = this.configuration.getBoolean("SetBaseEnabled");
        this.baseTeleportCooldown = this.configuration.getInt("BaseTeleportCooldown");
        this.setBaseCooldown = this.configuration.getInt("SetBaseCooldown");
        this.clanLimitEnabled = this.configuration.getBoolean("ClanLimitEnabled");
        this.clanLimit = this.configuration.getInt("ClanLimit");
        this.vaultEnabled = this.configuration.getBoolean("VaultSupport.Enabled");
        this.clanCreatePrice = this.configuration.getDouble("VaultSupport.ClanCreatePrice");
        if (this.configuration.getBoolean("SpecificWorlds.Enabled"))
            this.enabledWorlds = this.configuration.getStringList("SpecificWorlds.List");
        this.blacklistedNames = new ArrayList<String>();
        for (String name : this.configuration.getStringList("BlacklistedClanNames"))
            this.blacklistedNames.add(name.toLowerCase());
        this.onlineMode = this.configuration.getBoolean("OnlineMode");
        if (topClans.isEmpty())
            for (File clan : Main.getPlugin().getDataFolder().listFiles()) {
                if (!clan.isDirectory()) {
                    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(clan);
                    topClans.put(clan.getName().replace(".yml", ""), Integer.valueOf(yamlConfiguration.getInt("Kills")));
                }
            }
    }

    public String getClanNameMatcher() {
        return this.clanNameMatcher;
    }

    public List<String> getBlacklistedNames() {
        return this.blacklistedNames;
    }

    public boolean isWorldSupported(String world) {
        if (this.enabledWorlds.isEmpty())
            return true;
        return this.enabledWorlds.contains(world);
    }

    public boolean isVaultEnabled() {
        return this.vaultEnabled;
    }

    public double getClanCreatePrice() {
        return this.clanCreatePrice;
    }

    public boolean hasPermission(Player player, String permission) {
        if (isPermissionEnabled())
            return player.hasPermission(permission);
        return true;
    }

    public boolean hasPermission(CommandSender sender, String permission) {
        if (isPermissionEnabled())
            return sender.hasPermission(permission);
        return true;
    }

    public boolean exists(String name) {
        File clan = new File(Main.getPlugin().getDataFolder(), name + ".yml");
        if (clan.exists())
            return true;
        return false;
    }

    public boolean createClan(String name, Player owner) {
        File clan = new File(Main.getPlugin().getDataFolder(), name + ".yml");
        if (clan.exists())
            return false;
        if (getClan(owner) != null)
            return false;
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(clan);
        yamlConfiguration.set("ClanOwner", this.onlineMode ? owner.getUniqueId().toString() : owner.getName());
        yamlConfiguration.set("Clients", Arrays.asList(new String[] { this.onlineMode ? owner.getUniqueId().toString() : owner.getName() }));
        yamlConfiguration.set("Kills", Integer.valueOf(0));
        FileUtils.save(clan, (FileConfiguration)yamlConfiguration);
        topClans.put(name, Integer.valueOf(0));
        Main.getPlugin().getPlayerClanHandler().addClan(this.onlineMode ? owner.getUniqueId().toString() : owner.getName(), clan);
        return true;
    }

    public boolean removeClan(String name) {
        File clan = new File(Main.getPlugin().getDataFolder(), name + ".yml");
        if (!clan.exists())
            return false;
        for (String id : getRawClanMembers(clan))
            Main.getPlugin().getPlayerClanHandler().removeClan(id);
        clan.delete();
        topClans.remove(name);
        return true;
    }

    public File getClanFile(String name) {
        File clan = new File(Main.getPlugin().getDataFolder(), name + ".yml");
        if (!clan.exists())
            return null;
        return clan;
    }

    public FileConfiguration getClanFileConfiguration(String name) {
        File clan = new File(Main.getPlugin().getDataFolder(), name + ".yml");
        if (!clan.exists())
            return null;
        return (FileConfiguration)YamlConfiguration.loadConfiguration(clan);
    }

    public Location getClanBase(String name) {
        File clan = new File(Main.getPlugin().getDataFolder(), name + ".yml");
        String baseLocation = YamlConfiguration.loadConfiguration(clan).getString("BaseLocation");
        if (baseLocation != null) {
            String world = baseLocation.split(":")[0];
            String x = baseLocation.split(":")[1];
            String y = baseLocation.split(":")[2];
            String z = baseLocation.split(":")[3];
            String yaw = baseLocation.split(":")[4];
            String pitch = baseLocation.split(":")[5];
            World bukkitWorld = Bukkit.getWorld(world);
            if (bukkitWorld != null) {
                Location location = new Location(bukkitWorld, Double.valueOf(x).doubleValue(), Double.valueOf(y).doubleValue(), Double.valueOf(z).doubleValue());
                location.setYaw(Float.valueOf(yaw).floatValue());
                location.setPitch(Float.valueOf(pitch).floatValue());
                return location;
            }
        }
        return null;
    }

    public File getPlayerClan(Player player) {
        File clans = Main.getPlugin().getDataFolder();
        for (File clan : clans.listFiles()) {
            List<String> clients = YamlConfiguration.loadConfiguration(clan).getStringList("Clients");
            if (clients.contains(this.onlineMode ? player.getUniqueId().toString() : player.getName()))
                return clan;
        }
        return null;
    }

    public String getClan(Player player) {
        if (player == null)
            return null;
        if (Main.getPlugin() == null)
            return null;
        if (Main.getPlugin().getPlayerClanHandler() == null)
            return null;
        File clan = Main.getPlugin().getPlayerClanHandler().getClan(this.onlineMode ? player.getUniqueId().toString() : player.getName());
        if (clan == null) {
            clan = getPlayerClan(player);
        } else if (!clan.exists()) {
            clan = getPlayerClan(player);
        }
        if (clan != null)
            return clan.getName().replace(".yml", "");
        return null;
    }

    public String getClan(String name) {
        File clans = Main.getPlugin().getDataFolder();
        for (File clan : clans.listFiles()) {
            if (clan.getName().replace(".yml", "").equals(name))
                return name;
        }
        return null;
    }

    public boolean setBase(String clan, Location location) {
        File file = new File(Main.getPlugin().getDataFolder(), clan + ".yml");
        if (!file.exists())
            return false;
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        String convertedLocation = location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
        yamlConfiguration.set("BaseLocation", convertedLocation);
        FileUtils.save(file, (FileConfiguration)yamlConfiguration);
        return true;
    }

    public boolean addClient(String clan, Player player) {
        File file = new File(Main.getPlugin().getDataFolder(), clan + ".yml");
        if (!file.exists())
            return false;
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        List<String> clients = yamlConfiguration.getStringList("Clients");
        String id = this.onlineMode ? player.getUniqueId().toString() : player.getName();
        if (!clients.contains(id)) {
            clients.add(id);
            Main.getPlugin().getPlayerClanHandler().addClan(id, file);
            yamlConfiguration.set("Clients", clients);
            FileUtils.save(file, (FileConfiguration)yamlConfiguration);
            return true;
        }
        return false;
    }

    public boolean removeClient(String clan, String name) {
        File file = new File(Main.getPlugin().getDataFolder(), clan + ".yml");
        if (!file.exists())
            return false;
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        List<String> clients = yamlConfiguration.getStringList("Clients");
        String id = this.onlineMode ? getUUID(name) : name;
        if (!id.equals("")) {
            if (getClanOwner(clan).equals(id))
                return false;
            if (clients.contains(id)) {
                clients.remove(id);
                Main.getPlugin().getPlayerClanHandler().removeClan(id);
                yamlConfiguration.set("Clients", clients);
                FileUtils.save(file, (FileConfiguration)yamlConfiguration);
                return true;
            }
        }
        return false;
    }

    public String getUUID(String name) {
        String uuid = null;
        Player onlinePlayer = Bukkit.getPlayerExact(name);
        if (onlinePlayer != null)
            uuid = onlinePlayer.getUniqueId().toString();
        if (uuid == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
            if (offlinePlayer != null)
                uuid = offlinePlayer.getUniqueId().toString();
        }
        if (uuid == null) {
            uuid = (new MojangProfileReader()).getUUID(name);
            if (uuid == null)
                return uuid;
        }
        return uuid;
    }

    public List<String> getTopStats() {
        List<String> stats = new ArrayList<String>();
        int id = 1;
        for (Map.Entry<String, Integer> sortedMap : sortByComparator(topClans, false).entrySet()) {
            if (id <= 10)
                stats.add(sortedMap.getKey());
            id++;
        }
        return stats;
    }

    private Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (order)
                    return ((Integer)o1.getValue()).compareTo(o2.getValue());
                return ((Integer)o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
            sortedMap.put(entry.getKey(), entry.getValue());
        return sortedMap;
    }

    public List<String> getRawClanMembers(File file) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        List<String> clients = yamlConfiguration.getStringList("Clients");
        return clients;
    }

    public String getClanMembers(String clan) {
        String owner;
        File file = new File(Main.getPlugin().getDataFolder(), clan + ".yml");
        if (!file.exists())
            return "";
        String rawOwner = getClanOwner(clan);
        Player onlineOwner = this.onlineMode ? Bukkit.getPlayer(UUID.fromString(rawOwner)) : Bukkit.getPlayerExact(rawOwner);
        if (onlineOwner != null) {
            owner = onlineOwner.getName();
        } else {
            OfflinePlayer offlinePlayer = this.onlineMode ? Bukkit.getOfflinePlayer(UUID.fromString(rawOwner)) : null;
            if (offlinePlayer != null) {
                owner = offlinePlayer.getName();
            } else {
                owner = this.onlineMode ? (new MojangProfileReader()).getName(rawOwner) : rawOwner;
            }
        }
        String members = " + owner";
        for (String member : getRawClanMembers(file)) {
            if (!member.equals(rawOwner)) {
                String name;
                Player onlinePlayer = this.onlineMode ? Bukkit.getPlayer(UUID.fromString(member)) : Bukkit.getPlayerExact(member);
                if (onlinePlayer != null) {
                    name = onlinePlayer.getName();
                } else {
                    OfflinePlayer offlinePlayer = this.onlineMode ? Bukkit.getOfflinePlayer(UUID.fromString(member)) : null;
                    if (offlinePlayer != null) {
                        name = offlinePlayer.getName();
                    } else {
                        name = this.onlineMode ? (new MojangProfileReader()).getName(member) : member;
                    }
                }
                members = members + "+ name";
            }
        }
        return members;
    }

    public int getClanSize(String clan) {
        int size = -1;
        File file = new File(Main.getPlugin().getDataFolder(), clan + ".yml");
        if (!file.exists())
            return size;
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        size = yamlConfiguration.getStringList("Clients").size();
        return size;
    }

    public String getClanOwner(String clan) {
        File file = new File(Main.getPlugin().getDataFolder(), clan + ".yml");
        if (!file.exists())
            return null;
        return YamlConfiguration.loadConfiguration(file).getString("ClanOwner");
    }

    public int getClanKills(String clan) {
        File file = new File(Main.getPlugin().getDataFolder(), clan + ".yml");
        if (!file.exists())
            return -1;
        return YamlConfiguration.loadConfiguration(file).getInt("Kills");
    }

    public void addClanKill(Player player) {
        if (getClan(player) != null) {
            String clan = getClan(player);
            File file = new File(Main.getPlugin().getDataFolder(), clan + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            int kills = yamlConfiguration.getInt("Kills");
            String oldColor = getClanColor(clan);
            kills++;
            yamlConfiguration.set("Kills", Integer.valueOf(kills));
            FileUtils.save(file, (FileConfiguration)yamlConfiguration);
            topClans.put(clan, Integer.valueOf(kills));
            String newColor = getClanColor(clan);
            if (!oldColor.equals(newColor)) {
                String[] members = ChatColor.stripColor(getClanMembers(clan)).replace(" ", "").split(",");
                for (String member : members) {
                    Player onlinePlayer = this.onlineMode ? Bukkit.getPlayer(UUID.fromString(member)) : Bukkit.getPlayerExact(member);
                    if (onlinePlayer != null)
                        playSound(onlinePlayer, "ClanRankup");
                }
            }
        }
    }

    public String getClanColor(String clan) {
        int kills = getClanKills(clan);
        String clancolor = "";
        for (String line : this.clanColors) {
            String[] builder = line.split(":");
            String color = ChatColor.translateAlternateColorCodes('&', builder[1]);
            if (line.contains("-")) {
                String begin = builder[0].split("-")[0];
                String str1 = builder[0].split("-")[1];
                try {
                    int minkills = Integer.parseInt(begin);
                    int maxkills = Integer.parseInt(str1);
                    if (kills >= minkills && kills <= maxkills)
                        clancolor = color;
                } catch (Exception exception) {}
                continue;
            }
            String end = builder[0];
            try {
                int minkills = Integer.parseInt(end);
                if (kills >= minkills)
                    clancolor = color;
            } catch (Exception exception) {}
        }
        return clancolor;
    }

    public String getPrefix(String clan) {
        return getClanColor(clan) + clan;
    }

    public void playSound(Player player, String soundName) {
        if (!this.soundEnabled)
            return;
        String soundValue = this.configuration.getString("Sounds." + soundName);
        Sound playedSound = null;
        for (Sound sound : Sound.values()) {
            if (sound.name().equals(soundValue))
                playedSound = sound;
        }
        if (playedSound != null) {
            player.playSound(player.getEyeLocation(), playedSound, 0.5F, 1.0F);
        } else {
            Bukkit.getConsoleSender().sendMessage(Main.getPlugin().getPrefix() + " an invalid Sound - " + soundValue);
        }
    }

    public boolean isPermissionEnabled() {
        return this.permissionEnabled;
    }

    public boolean isChatEnabled() {
        return this.chatEnabled;
    }

    public boolean isCustomChatEnabled() {
        return this.customChatEnabled;
    }

    public boolean isDamageEnabled() {
        return this.damageEnabled;
    }

    public boolean isSelfDamageEnabled() {
        return this.selfDamageEnabled;
    }

    public boolean isBaseTeleportEnabled() {
        return this.baseTeleportEnabled;
    }

    public boolean isBaseTeleportMoveEnabled() {
        return this.baseTeleportMoveEnabled;
    }

    public boolean isSetBaseEnabled() {
        return this.setBaseEnabled;
    }

    public boolean isClanLimitEnabled() {
        return this.clanLimitEnabled;
    }

    public boolean isOnlineMode() {
        return this.onlineMode;
    }

    public String getPermission() {
        return "clans.*";
    }

    public String getPermission(String path) {
        return this.configuration.getString(path);
    }

    public String getChatFormat() {
        return this.chatFormat;
    }

    public int getClanNameLength() {
        return this.clanNameLength;
    }

    public int getBaseTeleportCooldown() {
        return this.baseTeleportCooldown;
    }

    public int getSetBaseCooldown() {
        return this.setBaseCooldown;
    }

    public int getClanLimit() {
        return this.clanLimit;
    }

    public Messages getMessages() {
        return this.messages;
    }
}
