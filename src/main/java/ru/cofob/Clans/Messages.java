package ru.cofob.Clans;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {
    private FileConfiguration configuration;

    public Messages() {
        File file = new File(Main.getPlugin().getDataFolder(), "Settings/messages.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.configuration.options().copyDefaults(true);
        this.configuration.addDefault("Prefix", "&7[&cClans&7]");
        this.configuration.addDefault("Commands.NoPermission", "%prefix% &cYou do not have the permission to execute this command!");
        this.configuration.addDefault("Commands.DisplayCreate", "%prefix% /clan create [name] | &eCreate a Clan");
        this.configuration.addDefault("Commands.Vault.CreatePriceFailed", "%prefix% You need %amount% $ to create a Clan");
        this.configuration.addDefault("Commands.DisplayInvite", "%prefix% /clan invite [player] | &eInvite a Player");
        this.configuration.addDefault("Commands.DisplayAccept", "%prefix% /clan accept [player] | &eAccept a Clan invite");
        this.configuration.addDefault("Commands.DisplayKick", "%prefix% /clan kick [player] | &eKick a Player");
        this.configuration.addDefault("Commands.DisplayStats", "%prefix% /clan stats [name] | &eGet Clan Stats");
        this.configuration.addDefault("Commands.DisplayTopStats", "%prefix% /clan stats | &eGlobal Top 10 Stats");
        this.configuration.addDefault("Commands.DisplayBase", "%prefix% /clan base | &eTeleport to the Clan Base");
        this.configuration.addDefault("Commands.DisplaySetBase", "%prefix% /clan setbase | &eSet the Clan Base");
        this.configuration.addDefault("Commands.DisplayLeave", "%prefix% /clan leave | &eLeave the Clan");
        this.configuration.addDefault("Commands.DisplayDelete", "%prefix% /clan delete | &eDelete the Clan");
        this.configuration.addDefault("Commands.DisplayList", "%prefix% /clan list | &eList all Clan Members");
        this.configuration.addDefault("Commands.Reload", "%prefix% /clan reload | &eReload the configuration");
        this.configuration.addDefault("Commands.MessageReload", "%prefix% &aReloading configuration!");
        this.configuration.addDefault("Commands.MessageReloadComplete", "%prefix% &aReloading complete!");
        this.configuration.addDefault("Commands.MessageTopStats", "%prefix% &c%id%.  &eClan: &7%clan% &8| &eKills: &7%kills%");
        this.configuration.addDefault("Commands.MessageBaseDelayMove", "%prefix% &9You will be teleported in &e%delay% &9seconds, &cdon't move&9!");
        this.configuration.addDefault("Commands.MessageBaseAlreadyQueued", "%prefix% &cYou are already in the teleport queue!");
        this.configuration.addDefault("Commands.MessageNoClanBase", "%prefix% &cFailed! There is no Clan Base available!");
        this.configuration.addDefault("Commands.MessageSetBase", "%prefix% &aThe Clan Base has been set to your position!");
        this.configuration.addDefault("Commands.MessageSetBaseFailed", "%prefix% &cFailed! Only the Clan Owner is allowed to perform this command!");
        this.configuration.addDefault("Commands.MessageDeleteClan", "%prefix% &9Clan &e%clan% &9has been removed!");
        this.configuration.addDefault("Commands.MessageDeleteClanFailed", "%prefix% &cFailed! Only the Clan Owner is allowed to perform this command!");
        this.configuration.addDefault("Commands.MessageLeaveClan", "%prefix% &9You left the Clan!");
        this.configuration.addDefault("Commands.MessageTooLongClanName", "%prefix% &cThe Clan Tag can't be longer than %length% Characters!");
        this.configuration.addDefault("Commands.MessageCreateFailedExist", "%prefix% &cClan &e%clan% &cdoes already exist!");
        this.configuration.addDefault("Commands.MessageCreateNameMatcherTriggered", "%prefix% &cYour Clan Name contains non allowed chars!");
        this.configuration.addDefault("Commands.MessageCreateClanSuccess", "%prefix% &aClan &e%clan% &ahas been created!");
        this.configuration.addDefault("Commands.MessageInviteOffline", "%prefix% &cPlayer &e%name% &cis not available!");
        this.configuration.addDefault("Commands.MessageInviteAlreadyReceived", "%prefix% &e%name% &chas already received an Invitation!");
        this.configuration.addDefault("Commands.MessageInviteClanLimitReached", "%prefix% &cError! The clan reached the allowed limit.");
        this.configuration.addDefault("Commands.MessageInviteSendClan", "%prefix% &e%name% &9has been invited to your Clan!");
        this.configuration.addDefault("Commands.MessageInviteSendOwner", "%prefix% &e%name% &9invited you to his Clan &7(&e%clan%&7)");
        this.configuration.addDefault("Commands.MessageInviteFailed", "%prefix% &cPlayer &e%name% &cis already in a Clan!");
        this.configuration.addDefault("Commands.MessageAcceptClanInvite", "%prefix% &e%name% &ahas accepted your Invitation!");
        this.configuration.addDefault("Commands.MessageAcceptClanPlayer", "%prefix% &aYou are now in Clan &e%clan%");
        this.configuration.addDefault("Commands.MessageAcceptError", "%prefix% &cError occurred, Ask an Operator to fix this Issue!");
        this.configuration.addDefault("Commands.MessageAcceptFailed", "%prefix% &e%name% &cis not in a Clan!");
        this.configuration.addDefault("Commands.MessageAcceptNoInvite", "%prefix% &cYou haven't received an Invitation from &e%name%&c!");
        this.configuration.addDefault("Commands.MessageKickSuccess", "%prefix% &e%name% &9kicked you from Clan &e%clan%&9!");
        this.configuration.addDefault("Commands.MessageKickRemoved", "%prefix% &e%name% &9has been removed from Your Clan!");
        this.configuration.addDefault("Commands.MessageKickFailed", "%prefix% &cFailed to remove &e%name% &cfrom your Clan!");
        this.configuration.addDefault("Commands.MessageStatsHeader", "&6-=-=-=-&e&lClan&6=-=&e&lStats&6-=-=-=-");
        this.configuration.addDefault("Commands.MessageStatsSpace", "");
        this.configuration.addDefault("Commands.MessageStatsClanName", "&a&lClan Name&7: %clan%");
        this.configuration.addDefault("Commands.MessageStatsClanKills", "&a&lClan Kills&7: &e%kills%");
        this.configuration.addDefault("Commands.MessageStatsClanMembers", "&a&lClan Member(s)&7: %members%");
        this.configuration.addDefault("Commands.MessageStatsFooter", "&6-=-=-=-&e&lClan&6=-=&e&lStats&6-=-=-=-");
        this.configuration.addDefault("Commands.MessageNoClanExist", "%prefix% &cThis Clan does not exist!");
        this.configuration.addDefault("Commands.MessageNoClanOwner", "%prefix% &cYou are not the Clan Owner!");
        this.configuration.addDefault("Commands.MessageAlreadyInClan", "%prefix% &cYou are already in a Clan!");
        this.configuration.addDefault("Commands.MessageNoClan", "%prefix% &cYou are not in a Clan!");
        this.configuration.addDefault("Commands.MessageClanNameBlacklisted", "%prefix% &cYour entered name is blacklisted.");
        this.configuration.addDefault("Actions.MessageBaseTeleported", "%prefix% &9You were teleported to the Clan Base!");
        this.configuration.addDefault("Actions.MessageBaseTeleportFailed", "%prefix% &cTeleport cancelled, don't move!");
        this.configuration.addDefault("Actions.MessageSetBaseFailed", "%prefix% &cWait until the cooldown is over!");
        this.configuration.addDefault("SpecificWorlds.FailedText", "%prefix% Your World does not support Clans");
        FileUtils.save(file, this.configuration);
    }

    public String getMessage(String path) {
        String value = ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
        value = value.replace("%prefix%", ChatColor.translateAlternateColorCodes('&', this.configuration.getString("Prefix")));
        return value;
    }
}
