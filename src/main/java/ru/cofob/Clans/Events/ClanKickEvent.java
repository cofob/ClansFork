package ru.cofob.Clans.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanKickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player owner;
    private String kickedUUID;
    private String kickedName;
    private String clanName;

    public ClanKickEvent(Player owner, String kickedId, String clanName) {
        this.owner = owner;
        this.kickedUUID = kickedId.contains("-") ? kickedId : null;
        this.kickedName = this.kickedUUID == null ? kickedId : null;
        this.clanName = clanName;
    }

    public String getClanName() {
        return this.clanName;
    }

    public String getKickedUUID() {
        return this.kickedUUID;
    }

    public String getKickedName() {
        return this.kickedName;
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
