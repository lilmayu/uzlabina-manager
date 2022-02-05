package dev.mayuna.uzlabinamanager.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.paper.managers.EconomyManager;
import dev.mayuna.uzlabinamanager.paper.managers.UzlabinaPlayerManager;
import dev.mayuna.uzlabinamanager.paper.objects.UzlabinaPlayer;
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

    @Subcommand("pay|send")
    @CommandAlias("pay")
    public void payBalanceToPlayer(CommandSender sender, String otherPlayer, int balance) {
        if (!(sender instanceof Player player)) {
            Logger.error("Tento příkaz je pro hráče!");
            return;
        }

        if (!EconomyManager.getEconomy().hasAccount(Bukkit.getOfflinePlayer(otherPlayer))) {
            PaperMessageInfo.error(player, "Tento hráč neexistuje!");
            return;
        }

        EconomyResponse withdrawResponse = EconomyManager.getEconomy().withdrawPlayer(player, balance);

        if (withdrawResponse.transactionSuccess()) {
            EconomyResponse depositResponse = EconomyManager.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(otherPlayer), balance);

            if (depositResponse.transactionSuccess()) {
                PaperMessageInfo.success(player, "Úspěšně jsi poslal hráči §e" + otherPlayer + "§a " + EconomyUtils.getCurrencyValue(balance));

                Player otherPlayerBukkit = Bukkit.getPlayer(otherPlayer);
                if (otherPlayerBukkit != null) {
                    PaperMessageInfo.info(otherPlayerBukkit, "Hráč §e" + player.getName() + "§7 ti poslal " + EconomyUtils.getCurrencyValue(balance) + "§7!");
                }
            } else {
                PaperMessageInfo.error(player, "Nepodařilo se poslat peníze, tvé peníze ti budou navráceny. Důvod: " + withdrawResponse.errorMessage);
                EconomyManager.getEconomy().depositPlayer(player, balance);
            }
        } else {
            PaperMessageInfo.error(player, "Nepodařilo se poslat peníze. Nejspíše nemáš dostatek peněz!");
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

    @Subcommand("take")
    @CommandPermission("uzlabinamanager.admin")
    public void takeBalanceFromPlayer(CommandSender sender, String otherPlayer, double balance) {
        EconomyResponse economyResponse = EconomyManager.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(otherPlayer), balance);

        if (economyResponse.transactionSuccess()) {
            PaperMessageInfo.info(sender, "Úspěšně jsi odebral hráči §e" + otherPlayer + "§a " + EconomyUtils.getCurrencyValue(balance));
        } else {
            PaperMessageInfo.error(sender, "Nepodařilo se odebrat hráči peníze. Důvod: " + economyResponse.errorMessage);
        }
    }

    @Subcommand("set")
    @CommandPermission("uzlabinamanager.admin")
    public void setBalanceToPlayer(CommandSender sender, String otherPlayer, double balance) {
        if (balance < 0) {
            PaperMessageInfo.error(sender, "Hodnota nesmí být menší než nula!");
            return;
        }

        UzlabinaPlayer uzlabinaOtherPlayer = UzlabinaPlayerManager.getPlayerByName(otherPlayer);

        if (uzlabinaOtherPlayer != null) {
            uzlabinaOtherPlayer.setMoney(balance);
            PaperMessageInfo.info(sender, "Úspěšně jsi nastavil hráči §e" + uzlabinaOtherPlayer.getName() + "§a " + EconomyUtils.getCurrencyValue(balance));
        } else {
            PaperMessageInfo.error(sender, "Tento hráč neexistuje!");
        }
    }
}
