package ru.cofob.PlaceholderAPIHook;

import ru.cofob.Clans.ClanConfiguration;
import ru.cofob.Clans.Main;
import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MaximvdwPlaceholder {
    private static ClanConfiguration clanConfiguration;

    public void load() {
        setClanConfiguration(new ClanConfiguration());
        (new BukkitRunnable() {
            public void run() {
                if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") != null)
                    if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
                        PlaceholderAPI.registerPlaceholder((Plugin)Main.getPlugin(), "clan_leader", new PlaceholderReplacer() {
                            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                                String clan = MaximvdwPlaceholder.clanConfiguration.getClan(e.getPlayer());
                                if (clan != null)
                                    return MaximvdwPlaceholder.clanConfiguration.getClanOwner(clan);
                                return "";
                            }
                        });
                        PlaceholderAPI.registerPlaceholder((Plugin)Main.getPlugin(), "clan_kills", new PlaceholderReplacer() {
                            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                                String clan = MaximvdwPlaceholder.clanConfiguration.getClan(e.getPlayer());
                                if (clan != null)
                                    return String.valueOf(MaximvdwPlaceholder.clanConfiguration.getClanKills(clan));
                                return "";
                            }
                        });
                        PlaceholderAPI.registerPlaceholder((Plugin)Main.getPlugin(), "colored_clan_tag", new PlaceholderReplacer() {
                            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                                String clan = MaximvdwPlaceholder.clanConfiguration.getClan(e.getPlayer());
                                if (clan != null)
                                    return MaximvdwPlaceholder.clanConfiguration.getPrefix(clan);
                                return "";
                            }
                        });
                        PlaceholderAPI.registerPlaceholder((Plugin)Main.getPlugin(), "clan_tag", new PlaceholderReplacer() {
                            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                                String clan = MaximvdwPlaceholder.clanConfiguration.getClan(e.getPlayer());
                                if (clan != null)
                                    return clan;
                                return "";
                            }
                        });
                        cancel();
                    }
            }
        }).runTaskTimer((Plugin)Main.getPlugin(), 5L, 5L);
    }

    public static void setClanConfiguration(ClanConfiguration clanConfiguration) {
        MaximvdwPlaceholder.clanConfiguration = clanConfiguration;
    }
}
