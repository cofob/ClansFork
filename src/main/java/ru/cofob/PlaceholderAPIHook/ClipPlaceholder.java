package ru.cofob.PlaceholderAPIHook;

import ru.cofob.Clans.ClanConfiguration;
import ru.cofob.Clans.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class ClipPlaceholder extends PlaceholderExpansion {
    private static ClanConfiguration clanConfiguration;

    public void load() {
        setClanConfiguration(new ClanConfiguration());
        register();
    }

    public static void setClanConfiguration(ClanConfiguration clanConfiguration) {
        ClipPlaceholder.clanConfiguration = clanConfiguration;
    }

    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    public String getIdentifier() {
        return "clans";
    }

    public String getAuthor() {
        return Main.getPlugin().getDescription().getAuthors().toString();
    }

    public String getVersion() {
        return Main.getPlugin().getDescription().getVersion();
    }

    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null)
            return "";
        if (identifier.equals("clan_leader")) {
            String clan = clanConfiguration.getClan(player);
            if (clan != null)
                return String.valueOf(clanConfiguration.getClanOwner(clan));
            return "";
        }
        if (identifier.equals("clan_kills")) {
            String clan = clanConfiguration.getClan(player);
            if (clan != null)
                return String.valueOf(clanConfiguration.getClanKills(clan));
            return "";
        }
        if (identifier.equals("colored_clan_tag")) {
            String clan = clanConfiguration.getClan(player);
            if (clan != null)
                return clanConfiguration.getPrefix(clan);
            return "";
        }
        if (identifier.equals("clan_tag")) {
            String clan = clanConfiguration.getClan(player);
            if (clan != null)
                return clan;
            return "";
        }
        return null;
    }
}
