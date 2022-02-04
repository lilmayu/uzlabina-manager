package dev.mayuna.uzlabinamanager.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.managers.EconomyManager;
import dev.mayuna.uzlabinamanager.paper.util.EconomyUtils;
import dev.mayuna.uzlabinamanager.paper.util.PaperMessageInfo;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("economy")
public class EconomyCommand extends BaseCommand {

    @Subcommand("balance|money")
    @CommandAlias("balance|money")
    public void showCurrentBalance(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            Logger.error("Tento příkaz je pro hráče!");
            return;
        }

        PaperMessageInfo.info(player, EconomyUtils.getCurrentBalance(player));
    }

    @Subcommand("balance|money")
    @CommandAlias("balance|money")
    public void showCurrentBalanceOther(CommandSender sender, String otherPlayer) {
        if (!(sender instanceof Player player)) {
            Logger.error("Tento příkaz je pro hráče!");
            return;
        }

        if (EconomyManager.getEconomy().hasAccount(otherPlayer)) {
            PaperMessageInfo.info(player, EconomyUtils.getCurrencyBalanceOther(Bukkit.getOfflinePlayer(otherPlayer)));
        } else {
            PaperMessageInfo.error(player, "Tento hráč neexistuje!");
        }
    }

    @Subcommand("give")
    @CommandPermission("uzlabinamanager.admin")
    public void giveBalanceToPlayer(CommandSender sender, String otherPlayer, double balance) {
        EconomyResponse economyResponse = EconomyManager.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(otherPlayer), balance);

        if (economyResponse.transactionSuccess()) {
            PaperMessageInfo.info(sender, "Úspěšně jsi přidal hráči §e" + otherPlayer + "§a " + EconomyUtils.getCurrencyValue(balance));
        } else {
            PaperMessageInfo.error(sender, "Nepodařilo se přidat hráči peníze. Důvod: " + economyResponse.errorMessage);
        }
    }
}
