package ru.cofob.Clans;

import ru.cofob.Clans.Events.ClanBaseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class ClientListener implements Listener {
    private Map<Entity, Entity> lastKiller = new HashMap();
    private static ClanConfiguration clanConfiguration = new ClanConfiguration();
    private static Map<Player, Long> baseTeleport = new HashMap();
    private static Map<Player, Long> setBase = new HashMap();

    public ClientListener() {
    }

    @EventHandler
    public void death(PlayerDeathEvent e) {
        Entity entity = e.getEntity();
        if (clanConfiguration.isWorldSupported(entity.getWorld().getName())) {
            Entity killer = (Entity)this.lastKiller.get(entity);
            if (killer != null && killer instanceof Player) {
                Player player = (Player)killer;
                clanConfiguration.addClanKill(player);
            }

        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void customChatHighest(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (clanConfiguration.isCustomChatEnabled()) {
            String format = e.getFormat();
            if (format != null) {
                String playerClan = clanConfiguration.getClan(player);
                String clan = playerClan == null ? "" : playerClan;
                String coloredClan = playerClan == null ? "" : clanConfiguration.getClanColor(playerClan) + playerClan;
                e.setFormat(ChatColor.translateAlternateColorCodes('&', format.replace("{clan_tag}", clan).replace("{colored_clan_tag}", coloredClan)));
            }
        }

    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (clanConfiguration.isWorldSupported(player.getWorld().getName())) {
            if (clanConfiguration.isChatEnabled()) {
                String clan = clanConfiguration.getClan(player);
                if (clan != null) {
                    e.setFormat(clanConfiguration.getChatFormat().replace("%clan%", clanConfiguration.getPrefix(clan)).replace("%player%", player.getName()).replace("%message%", e.getMessage().replace("%", "%%")));
                }
            }

        }
    }

    @EventHandler
    public void baseMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (clanConfiguration.isWorldSupported(player.getWorld().getName())) {
            if (baseTeleport.containsKey(player) && !clanConfiguration.isBaseTeleportMoveEnabled() && (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ())) {
                undetectBaseTeleport(player, true);
            }

        }
    }

    @EventHandler
    public void leaveQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        undetectBaseTeleport(player, false);
        undetectSetBase(player, false);
    }

    @EventHandler
    public void leaveKick(PlayerKickEvent e) {
        Player player = e.getPlayer();
        undetectBaseTeleport(player, false);
        undetectSetBase(player, false);
    }

    public static void launchBaseTeleporter() {
        (new BukkitRunnable() {
            public void run() {
                Map<Player, Long> setBasePlayers = new HashMap();
                setBasePlayers.putAll(ClientListener.setBase);
                Iterator var2 = setBasePlayers.entrySet().iterator();

                while(var2.hasNext()) {
                    Entry<Player, Long> setBaseDelay = (Entry)var2.next();
                    long delay = (Long)setBaseDelay.getValue();
                    if (System.currentTimeMillis() > delay) {
                        ClientListener.undetectSetBase((Player)setBaseDelay.getKey(), false);
                    }
                }

                Map<Player, Long> basePlayers = new HashMap();
                basePlayers.putAll(ClientListener.baseTeleport);
                Iterator var11 = basePlayers.entrySet().iterator();

                while(var11.hasNext()) {
                    Entry<Player, Long> baseTeleportDelay = (Entry)var11.next();
                    long delayx = (Long)baseTeleportDelay.getValue();
                    if (System.currentTimeMillis() > delayx) {
                        ClientListener.undetectBaseTeleport((Player)baseTeleportDelay.getKey(), false);
                        Player player = (Player)baseTeleportDelay.getKey();
                        String clan = ClientListener.clanConfiguration.getClan(player);
                        Location clanBase = ClientListener.clanConfiguration.getClanBase(clan);
                        ((Player)baseTeleportDelay.getKey()).teleport(clanBase);
                        Bukkit.getPluginManager().callEvent(new ClanBaseEvent(player, clanBase, clan));
                        ClientListener.clanConfiguration.playSound(player, "BaseTeleport");
                        ((Player)baseTeleportDelay.getKey()).sendMessage(ClientListener.clanConfiguration.getMessages().getMessage("Actions.MessageBaseTeleported"));
                    }
                }

            }
        }).runTaskTimer(Main.getPlugin(), 0L, 0L);
    }

    private static void undetectBaseTeleport(Player player, boolean detected) {
        if (baseTeleport.containsKey(player)) {
            baseTeleport.remove(player);
        }

        if (detected) {
            player.sendMessage(clanConfiguration.getMessages().getMessage("Actions.MessageBaseTeleportFailed"));
        }

    }

    private static void undetectSetBase(Player player, boolean detected) {
        if (setBase.containsKey(player)) {
            setBase.remove(player);
        }

        if (detected) {
            player.sendMessage(clanConfiguration.getMessages().getMessage("Actions.MessageSetBaseFailed"));
        }

    }

    public static boolean detectBaseTeleport(Player player) {
        if (!baseTeleport.containsKey(player)) {
            baseTeleport.put(player, System.currentTimeMillis() + (long)(1000 * clanConfiguration.getBaseTeleportCooldown()));
            return true;
        } else {
            return false;
        }
    }

    public static boolean detectSetBase(Player player) {
        if (!setBase.containsKey(player)) {
            setBase.put(player, System.currentTimeMillis() + (long)(1000 * clanConfiguration.getSetBaseCooldown()));
            return true;
        } else {
            return false;
        }
    }

    public static void setClanConfiguration(ClanConfiguration clanConfiguration) {
        ClientListener.clanConfiguration = clanConfiguration;
    }
}
