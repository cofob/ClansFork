package ru.cofob.Clans.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanDeleteEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player owner;
    private String clanName;

    public ClanDeleteEvent(Player owner, String clanName) {
        this.owner = owner;
        this.clanName = clanName;
    }

    public String getClanName() {
        return this.clanName;
    }

    public Player getOwner() {
        return this.owner;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
