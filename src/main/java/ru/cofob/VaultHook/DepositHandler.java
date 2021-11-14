package ru.cofob.VaultHook;

import ru.cofob.Clans.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class DepositHandler {
    private static RegisteredServiceProvider<?> service = null;

    public static boolean depositPlayer(Player player) {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null)
            return true;
        if (!Bukkit.getPluginManager().getPlugin("Vault").isEnabled())
            return true;
        if (!Main.getPlugin().getClanConfiguration().isVaultEnabled())
            return true;
        if (service == null)
            service = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (service == null)
            return true;
        Economy economy = (Economy)service.getProvider();
        double playerMoney = economy.getBalance((OfflinePlayer)player);
        double createPrice = Main.getPlugin().getClanConfiguration().getClanCreatePrice();
        if (playerMoney >= createPrice) {
            economy.withdrawPlayer((OfflinePlayer)player, createPrice);
            return true;
        }
        player.sendMessage(Main.getPlugin().getClanConfiguration().getMessages().getMessage("Commands.Vault.CreatePriceFailed").replace("%amount%", Double.toString(createPrice)));
        return false;
    }
}
