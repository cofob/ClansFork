package ru.cofob.Clans;

import ru.cofob.Clans.AddonHandler.AddonAPI;
import ru.cofob.Clans.AddonHandler.CustomAddonHook;
import ru.cofob.Clans.Events.ClanAcceptEvent;
import ru.cofob.Clans.Events.ClanBaseEvent;
import ru.cofob.Clans.Events.ClanCreateEvent;
import ru.cofob.Clans.Events.ClanDeleteEvent;
import ru.cofob.Clans.Events.ClanInviteEvent;
import ru.cofob.Clans.Events.ClanKickEvent;
import ru.cofob.Clans.Events.ClanLeaveEvent;
import ru.cofob.Clans.Events.ClanSetBaseEvent;
import ru.cofob.PlaceholderAPIHook.ClipPlaceholder;
import ru.cofob.PlaceholderAPIHook.MaximvdwPlaceholder;
import ru.cofob.VaultHook.DepositHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClanCommand implements CommandExecutor {
    private List<ClanInvite> invites = new ArrayList();
    private ClanConfiguration clanConfiguration = new ClanConfiguration();

    public ClanCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPlugin().getPrefix() + " §cThis is not a console command!");
            return false;
        } else {
            Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("clan")) {
                if (!this.clanConfiguration.isWorldSupported(player.getWorld().getName())) {
                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("SpecificWorlds.FailedText"));
                    return false;
                }

                if (args.length == 0) {
                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanCreate"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayCreate"));
                    }

                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanInvite"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayInvite"));
                    }

                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanAccept"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayAccept"));
                    }

                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanKick"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayKick"));
                    }

                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanStats"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayStats"));
                    }

                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanTopStats"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayTopStats"));
                    }

                    if (this.clanConfiguration.isBaseTeleportEnabled()) {
                        if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanBase"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayBase"));
                        }

                        if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanSetBase"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplaySetBase"));
                        }
                    }

                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanLeave"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayLeave"));
                    }

                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanDelete"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayDelete"));
                    }

                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanList"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.DisplayList"));
                    }

                    if (this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClansReload"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.Reload"));
                    }
                }

                Iterator var6 = AddonAPI.getAddons().values().iterator();

                while(var6.hasNext()) {
                    CustomAddonHook addonHook = (CustomAddonHook)var6.next();
                    addonHook.onCommand(sender, cmd, label, args);
                }

                String clan;
                boolean detected;
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload") && this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClansReload"))) {
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageReload"));
                        this.clanConfiguration = new ClanConfiguration();
                        Main.getPlugin().setClanConfiguration(this.clanConfiguration);
                        ClientListener.setClanConfiguration(this.clanConfiguration);
                        ClipPlaceholder.setClanConfiguration(this.clanConfiguration);
                        MaximvdwPlaceholder.setClanConfiguration(this.clanConfiguration);
                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageReloadComplete"));
                    }

                    if (args[0].equalsIgnoreCase("stats")) {
                        if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanTopStats"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                            return false;
                        }

                        int id = 1;

                        for(Iterator var13 = this.clanConfiguration.getTopStats().iterator(); var13.hasNext(); ++id) {
                            clan = (String)var13.next();
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageTopStats").replace("%id%", String.valueOf(id)).replace("%clan%", this.clanConfiguration.getPrefix(clan)).replace("%kills%", String.valueOf(this.clanConfiguration.getClanKills(clan))));
                        }
                    }

                    if (this.clanConfiguration.isBaseTeleportEnabled()) {
                        if (args[0].equalsIgnoreCase("base")) {
                            if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanBase"))) {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                                return false;
                            }

                            clan = this.clanConfiguration.getClan(player);
                            if (clan != null) {
                                if (this.clanConfiguration.getClanBase(clan) != null) {
                                    if (this.clanConfiguration.getBaseTeleportCooldown() > 0) {
                                        if (this.clanConfiguration.isBaseTeleportMoveEnabled()) {
                                            detected = ClientListener.detectBaseTeleport(player);
                                            if (detected) {
                                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageBaseDelayMove").replace("%delay%", String.valueOf(this.clanConfiguration.getBaseTeleportCooldown())));
                                            } else {
                                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageBaseAlreadyQueued"));
                                            }
                                        } else {
                                            detected = ClientListener.detectBaseTeleport(player);
                                            if (detected) {
                                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageBaseDelayMove").replace("%delay%", String.valueOf(this.clanConfiguration.getBaseTeleportCooldown())));
                                            } else {
                                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageBaseAlreadyQueued"));
                                            }
                                        }
                                    } else {
                                        Location clanBase = this.clanConfiguration.getClanBase(clan);
                                        Bukkit.getPluginManager().callEvent(new ClanBaseEvent(player, clanBase, clan));
                                        player.teleport(clanBase);
                                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Actions.MessageBaseTeleported"));
                                    }
                                } else {
                                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClanBase"));
                                }
                            } else {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClan"));
                            }
                        }

                        if (args[0].equalsIgnoreCase("setbase")) {
                            if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanSetBase"))) {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                                return false;
                            }

                            clan = this.clanConfiguration.getClan(player);
                            if (clan != null) {
                                if (this.clanConfiguration.getClanOwner(clan).equals(this.clanConfiguration.isOnlineMode() ? player.getUniqueId().toString() : player.getName())) {
                                    if (this.clanConfiguration.getSetBaseCooldown() > 0) {
                                        if (this.clanConfiguration.isSetBaseEnabled()) {
                                            detected = ClientListener.detectSetBase(player);
                                            if (detected) {
                                                this.clanConfiguration.setBase(clan, player.getLocation());
                                                Bukkit.getPluginManager().callEvent(new ClanSetBaseEvent(player, player.getLocation(), clan));
                                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageSetBase"));
                                            } else {
                                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Actions.MessageSetBaseFailed"));
                                            }
                                        } else {
                                            detected = ClientListener.detectSetBase(player);
                                            if (detected) {
                                                this.clanConfiguration.setBase(clan, player.getLocation());
                                                Bukkit.getPluginManager().callEvent(new ClanSetBaseEvent(player, player.getLocation(), clan));
                                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageSetBase"));
                                            } else {
                                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Actions.MessageSetBaseFailed"));
                                            }
                                        }
                                    } else {
                                        this.clanConfiguration.setBase(clan, player.getLocation());
                                        Bukkit.getPluginManager().callEvent(new ClanSetBaseEvent(player, player.getLocation(), clan));
                                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageSetBase"));
                                    }
                                } else {
                                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageSetBaseFailed"));
                                }
                            } else {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClan"));
                            }
                        }
                    }

                    if (args[0].equalsIgnoreCase("delete")) {
                        if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanDelete"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                            return false;
                        }

                        clan = this.clanConfiguration.getClan(player);
                        if (clan != null) {
                            if (this.clanConfiguration.getClanOwner(this.clanConfiguration.getClan(player)).equals(this.clanConfiguration.isOnlineMode() ? player.getUniqueId().toString() : player.getName())) {
                                this.clanConfiguration.removeClan(clan);
                                Bukkit.getPluginManager().callEvent(new ClanDeleteEvent(player, clan));
                                this.clanConfiguration.playSound(player, "ClanLeave");
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageDeleteClan").replace("%clan%", clan));
                            } else {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageDeleteClanFailed"));
                            }
                        } else {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClan"));
                        }
                    }

                    if (args[0].equalsIgnoreCase("list")) {
                        if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanList"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                            return false;
                        }

                        clan = this.clanConfiguration.getClan(player);
                        if (clan != null) {
                            player.sendMessage(Main.getPlugin().getPrefix() + this.clanConfiguration.getClanMembers(clan));
                        } else {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClan"));
                        }
                    }

                    if (args[0].equalsIgnoreCase("leave")) {
                        if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanLeave"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                            return false;
                        }

                        clan = this.clanConfiguration.getClan(player);
                        if (clan != null) {
                            detected = this.clanConfiguration.removeClient(clan, player.getName());
                            if (!detected) {
                                if (this.clanConfiguration.getClanOwner(this.clanConfiguration.getClan(player)).equals(this.clanConfiguration.isOnlineMode() ? player.getUniqueId().toString() : player.getName())) {
                                    player.performCommand("clan delete");
                                    Bukkit.getPluginManager().callEvent(new ClanLeaveEvent(player, clan));
                                    Bukkit.getPluginManager().callEvent(new ClanDeleteEvent(player, clan));
                                } else {
                                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClan"));
                                }
                            } else {
                                this.clanConfiguration.playSound(player, "ClanLeave");
                                Bukkit.getPluginManager().callEvent(new ClanLeaveEvent(player, clan));
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageLeaveClan"));
                            }
                        }
                    }
                }

                if (args.length >= 2) {
                    boolean created;
                    boolean invited;
                    if (args[0].equalsIgnoreCase("create")) {
                        if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanCreate"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                            return false;
                        }

                        clan = args[1];
                        if (Main.getPlugin().getClanConfiguration().getBlacklistedNames().contains(clan.toLowerCase())) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageClanNameBlacklisted"));
                            return false;
                        }

                        if (clan.toCharArray().length > this.clanConfiguration.getClanNameLength()) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageTooLongClanName").replace("%length%", String.valueOf(this.clanConfiguration.getClanNameLength())));
                            return false;
                        }

                        if (this.clanConfiguration.getClan(player) != null) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageAlreadyInClan"));
                            return false;
                        }

                        detected = this.clanConfiguration.exists(clan);
                        invited = this.clanConfiguration.getClan(player) != null;
                        if (detected || invited) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageCreateFailedExist").replace("%clan%", clan));
                        }

                        if (!this.clanConfiguration.getClanNameMatcher().equals("") && !clan.matches(this.clanConfiguration.getClanNameMatcher())) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageCreateNameMatcherTriggered"));
                            return false;
                        }

                        if (DepositHandler.depositPlayer(player)) {
                            created = this.clanConfiguration.createClan(clan, player);
                            if (!created) {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageCreateFailedExist").replace("%clan%", clan));
                            } else {
                                this.clanConfiguration.playSound(player, "ClanEnter");
                                Bukkit.getPluginManager().callEvent(new ClanCreateEvent(player, clan));
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageCreateClanSuccess").replace("%clan%", clan));
                            }
                        }
                    }

                    Iterator var17;
                    ClanInvite clanInvite;
                    Player client;
                    if (args[0].equalsIgnoreCase("invite")) {
                        if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanInvite"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                            return false;
                        }

                        clan = args[1];
                        client = Bukkit.getPlayerExact(clan);
                        if (this.clanConfiguration.getClan(player) == null) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClan"));
                        } else if (!this.clanConfiguration.getClanOwner(this.clanConfiguration.getClan(player)).equals(this.clanConfiguration.isOnlineMode() ? player.getUniqueId().toString() : player.getName())) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClanOwner"));
                        } else if (client == null) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageInviteOffline").replace("%name%", clan));
                        } else if (this.clanConfiguration.getClan(client) != null) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageInviteFailed").replace("%name%", clan));
                        } else {
                            var17 = this.invites.iterator();

                            while(var17.hasNext()) {
                                clanInvite = (ClanInvite)var17.next();
                                if (clanInvite.getReceiver().getName().equals(clan) && clanInvite.getSender().equals(player)) {
                                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageInviteAlreadyReceived").replace("%name%", clan));
                                    return false;
                                }
                            }

                            if (this.clanConfiguration.isClanLimitEnabled() && this.clanConfiguration.getClanSize(this.clanConfiguration.getClan(player)) >= this.clanConfiguration.getClanLimit()) {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageInviteClanLimitReached"));
                                return false;
                            }

                            this.invites.add(new ClanInvite(player, client));
                            Bukkit.getPluginManager().callEvent(new ClanInviteEvent(player, client, this.clanConfiguration.getClan(player)));
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageInviteSendClan").replace("%name%", client.getName()));
                            this.sendClickableMessage(client, this.clanConfiguration.getMessages().getMessage("Commands.MessageInviteSendOwner").replace("%name%", player.getName()).replace("%clan%", this.clanConfiguration.getClan(player)), "/clan accept " + player.getName());
                        }
                    }

                    if (args[0].equalsIgnoreCase("accept")) {
                        if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanAccept"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                            return false;
                        }

                        clan = args[1];
                        ClanInvite invitation = null;
                        var17 = this.invites.iterator();

                        while(var17.hasNext()) {
                            clanInvite = (ClanInvite)var17.next();
                            if (clanInvite.getSender().getName().equals(clan) && clanInvite.getReceiver().equals(player)) {
                                invitation = clanInvite;
                            }
                        }

                        if (invitation != null) {
                            if (this.clanConfiguration.getClan(player) != null) {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageAlreadyInClan"));
                                return false;
                            }

                            if (this.clanConfiguration.getClan(invitation.getSender()) != null) {
                                if (this.clanConfiguration.isClanLimitEnabled() && this.clanConfiguration.getClanSize(this.clanConfiguration.getClan(invitation.getSender())) >= this.clanConfiguration.getClanLimit()) {
                                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageInviteClanLimitReached"));
                                    return false;
                                }

                                if (invitation.getSender().isOnline()) {
                                    invitation.getSender().sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageAcceptClanInvite").replace("%name%", invitation.getReceiver().getName()));
                                }

                                this.invites.remove(invitation);
                                invited = this.clanConfiguration.addClient(this.clanConfiguration.getClan(invitation.getSender()), player);
                                if (invited) {
                                    Bukkit.getPluginManager().callEvent(new ClanAcceptEvent(player, this.clanConfiguration.getClan(invitation.getSender())));
                                    this.clanConfiguration.playSound(player, "ClanEnter");
                                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageAcceptClanPlayer").replace("%clan%", this.clanConfiguration.getClan(player)));
                                } else {
                                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageAcceptError"));
                                }
                            } else {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageAcceptFailed").replace("%name%", invitation.getSender().getName()));
                            }

                            return false;
                        }

                        player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageAcceptNoInvite").replace("%name%", clan));
                    }

                    if (args[0].equalsIgnoreCase("kick")) {
                        if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanKick"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                            return false;
                        }

                        clan = args[1];
                        client = Bukkit.getPlayerExact(clan);
                        clan = this.clanConfiguration.getClan(player);
                        if (clan != null) {
                            if (this.clanConfiguration.getClanOwner(clan).equals(this.clanConfiguration.isOnlineMode() ? player.getUniqueId().toString() : player.getName())) {
                                created = this.clanConfiguration.removeClient(clan, clan);
                                if (created) {
                                    if (client != null) {
                                        client.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageKickSuccess").replace("%name%", player.getName()).replace("%clan%", this.clanConfiguration.getClan(player)));
                                    }

                                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageKickRemoved").replace("%name%", clan));
                                    String id = this.clanConfiguration.isOnlineMode() ? this.clanConfiguration.getUUID(clan) : clan;
                                    if (id != null) {
                                        Bukkit.getPluginManager().callEvent(new ClanKickEvent(player, id, clan));
                                    }
                                } else {
                                    player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageKickFailed").replace("%name%", clan));
                                }
                            } else {
                                player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClanOwner"));
                            }
                        } else {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClan"));
                        }
                    }

                    if (args[0].equalsIgnoreCase("stats")) {
                        if (!this.clanConfiguration.hasPermission(player, this.clanConfiguration.getPermission("PermissionClanStats"))) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.NoPermission"));
                            return false;
                        }

                        clan = args[1];
                        if (this.clanConfiguration.getClan(clan) != null) {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageStatsHeader"));
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageStatsSpace"));
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageStatsClanName").replace("%clan%", this.clanConfiguration.getPrefix(clan)));
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageStatsClanKills").replace("%kills%", String.valueOf(this.clanConfiguration.getClanKills(clan))));
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageStatsClanMembers").replace("%members%", this.clanConfiguration.getClanMembers(clan)));
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageStatsSpace"));
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageStatsFooter"));
                        } else {
                            player.sendMessage(this.clanConfiguration.getMessages().getMessage("Commands.MessageNoClanExist"));
                        }
                    }
                }
            }

            return false;
        }
    }

    private void sendClickableMessage(Player player, String displayedMessage, String command) {
        BaseComponent[] components = TextComponent.fromLegacyText(displayedMessage);
        ClickEvent clickEvent = new ClickEvent(Action.RUN_COMMAND, command);
        BaseComponent[] var6 = components;
        int var7 = components.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            BaseComponent component = var6[var8];
            component.setClickEvent(clickEvent);
        }

        player.spigot().sendMessage(components);
    }
}
