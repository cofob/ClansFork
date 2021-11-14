package ru.cofob.Clans.Events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanSetBaseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Location location;
    private String clanName;

    public ClanSetBaseEvent(Player player, Location location, String clanName) {
        this.player = player;
        this.location = location;
        this.clanName = clanName;
    }

    public String getClanName() {
        return this.clanName;
    }

    public Location getLocation() {
        return this.location;
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
