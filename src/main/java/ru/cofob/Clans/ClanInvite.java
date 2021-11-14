package ru.cofob.Clans;

import org.bukkit.entity.Player;

public class ClanInvite {
    private Player sender;
    private Player receiver;

    public ClanInvite(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Player getSender() {
        return this.sender;
    }

    public Player getReceiver() {
        return this.receiver;
    }
}
