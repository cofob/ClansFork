package ru.cofob.Clans;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanInviteEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player owner;
    private Player invited;
    private String clanName;

    public ClanInviteEvent(Player owner, Player invited, String clanName) {
        this.owner = owner;
        this.invited = invited;
        this.clanName = clanName;
    }

    public String getClanName() {
        return this.clanName;
    }

    public Player getInvited() {
        return this.invited;
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
