package ru.cofob.Clans.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanJoinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private String clanName;

    public ClanJoinEvent(Player player, String clanName) {
        this.player = player;
        this.clanName = clanName;
    }

    public String getClanName() {
        return this.clanName;
    }

    public Player getPlayer() {
        return this.player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
