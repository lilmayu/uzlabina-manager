package dev.mayuna.uzlabinamanager.paper.vault;

import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.paper.managers.UzlabinaPlayerManager;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import dev.mayuna.uzlabinamanager.paper.objects.UzlabinaPlayer;
import dev.mayuna.uzlabinamanager.paper.util.EconomyUtils;
import dev.mayuna.uzlabinamanager.paper.util.PaperMessageInfo;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class EconomyImpl extends AbstractEconomy {

    @Override
    public boolean isEnabled() {
        return PaperMain.getInstance().isEnabled();
    }

    @Override
    public String getName() {
        return "UzlabinaManagerEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return String.valueOf(amount);
    }

    @Override
    public String currencyNamePlural() {
        return PaperConfig.getEconomyCurrency();
    }

    @Override
    public String currencyNameSingular() {
        return PaperConfig.getEconomyCurrency();
    }

    @Override
    public boolean hasAccount(String playerName) {
        if (playerName == null) {
            return false;
        }

        return UzlabinaPlayerManager.hasDataByName(playerName);
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public double getBalance(String playerName) {
        UzlabinaPlayer uzlabinaPlayer = UzlabinaPlayerManager.getPlayerByName(playerName);

        if (uzlabinaPlayer == null) {
            return 0;
        } else {
            return uzlabinaPlayer.getMoney();
        }
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (!hasAccount(playerName)) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Tento hr???? nem?? vytvo??en?? economy ????et!");
        }

        if (amount <= 0) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Hodnota nesm?? b??t men???? nebo rovna nule!");
        }

        if (!has(playerName, amount)) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Hr???? nem?? dostatek pen??z!");
        }

        double currentBalance = getBalance(playerName);
        double finalBalance = currentBalance - amount;

        if (finalBalance < 0) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Hr???? nem?? dostatek pen??z!");
        }

        UzlabinaPlayer uzlabinaPlayer = UzlabinaPlayerManager.getPlayerByName(playerName);
        uzlabinaPlayer.setMoney(finalBalance);

        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            PaperMessageInfo.warning(player, "Bylo ti odebr??no " + EconomyUtils.getCurrencyValue(amount));
        }

        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        if (!hasAccount(playerName)) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Tento hr???? nem?? vytvo??en?? economy ????et!");
        }

        if (amount <= 0) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Hodnota nesm?? b??t men???? nebo rovna nule!");
        }

        double currentBalance = getBalance(playerName);
        double finalBalance = currentBalance + amount;

        UzlabinaPlayer uzlabinaPlayer = UzlabinaPlayerManager.getPlayerByName(playerName);
        uzlabinaPlayer.setMoney(finalBalance);

        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            PaperMessageInfo.success(player, "Bylo ti p??id??no " + EconomyUtils.getCurrencyValue(amount));
        }

        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        UzlabinaPlayerManager.getOrCreatePlayer(Bukkit.getOfflinePlayer(playerName));
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }
}
