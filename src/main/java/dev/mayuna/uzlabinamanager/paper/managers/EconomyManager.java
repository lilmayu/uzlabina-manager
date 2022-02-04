package dev.mayuna.uzlabinamanager.paper.managers;

import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import dev.mayuna.uzlabinamanager.paper.util.BukkitUtils;
import dev.mayuna.uzlabinamanager.paper.vault.EconomyImpl;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.ServicePriority;

public class EconomyManager {

    private static @Getter Economy economy;

    public static void init() {
        if (!PaperConfig.isEconomyEnabled()) {
            Logger.info("Economy is not enabled.");
            return;
        }

        if (!BukkitUtils.isPluginEnabled("Vault")) {
            Logger.error("Vault is not installed! Cannot start economy.");
            return;
        }

        economy = new EconomyImpl();
        PaperMain.getInstance().getServer().getServicesManager().register(Economy.class, economy, PaperMain.getInstance(), ServicePriority.Normal);
        Logger.success("Economy currency is " + PaperConfig.getEconomyCurrency());
    }

    public static boolean isEconomyEnabled() {
        return economy != null;
    }
}
