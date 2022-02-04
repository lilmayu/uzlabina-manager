package dev.mayuna.uzlabinamanager.paper.util;

import dev.mayuna.uzlabinamanager.paper.managers.EconomyManager;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import org.bukkit.OfflinePlayer;

public class EconomyUtils {

    public static String getCurrencyValue(double balance) {
        return "§e" +  balance + " §6" + PaperConfig.getEconomyCurrency();
    }

    public static String getCurrencyValue(OfflinePlayer offlinePlayer) {
        return getCurrencyValue(EconomyManager.getEconomy().getBalance(offlinePlayer));
    }

    public static String getCurrentBalance(OfflinePlayer offlinePlayer) {
        return "Momentálně vlastníš §e" + getCurrencyValue(offlinePlayer);
    }

    public static String getCurrencyBalanceOther(OfflinePlayer offlinePlayer) {
        return "Hráč §e" + offlinePlayer.getName() + "§7 momentálně vlastní " + getCurrencyValue(offlinePlayer);
    }
}
