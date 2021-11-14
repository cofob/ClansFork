package ru.cofob.Clans;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerClanHandler implements Listener {
    private Map<String, File> playerClans = new HashMap();

    public PlayerClanHandler() {
        Iterator var1 = Bukkit.getOnlinePlayers().iterator();

        while(var1.hasNext()) {
            Player player = (Player)var1.next();
            String id = Main.getPlugin().getClanConfiguration().isOnlineMode() ? player.getUniqueId().toString() : player.getName();
            this.addClan(id, Main.getPlugin().getClanConfiguration().getPlayerClan(player));
        }

    }

    @EventHandler
    public void connectClanInitialize(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String id = Main.getPlugin().getClanConfiguration().isOnlineMode() ? player.getUniqueId().toString() : player.getName();
        this.addClan(id, Main.getPlugin().getClanConfiguration().getPlayerClan(player));
    }

    @EventHandler
    public void disconnectClanUnitialize(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        String id = Main.getPlugin().getClanConfiguration().isOnlineMode() ? player.getUniqueId().toString() : player.getName();
        this.removeClan(id);
    }

    @EventHandler
    public void disconnectClanUnitialize(PlayerKickEvent e) {
        Player player = e.getPlayer();
        String id = Main.getPlugin().getClanConfiguration().isOnlineMode() ? player.getUniqueId().toString() : player.getName();
        this.removeClan(id);
    }

    public File removeClan(String id) {
        if (this.playerClans.containsKey(id)) {
            File file = (File)this.playerClans.get(id);
            this.playerClans.remove(id);
            return file;
        } else {
            return null;
        }
    }

    public File addClan(String id, File file) {
        this.playerClans.put(id, file);
        return file;
    }

    public File getClan(String id) {
        return this.playerClans.containsKey(id) ? (File)this.playerClans.get(id) : null;
    }
}
