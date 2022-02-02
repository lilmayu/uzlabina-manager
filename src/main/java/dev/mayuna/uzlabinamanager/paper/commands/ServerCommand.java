package dev.mayuna.uzlabinamanager.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import com.gmail.tracebachi.SockExchange.Spigot.SockExchangeApi;
import dev.mayuna.uzlabinamanager.paper.util.PaperMessageInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

@CommandAlias("server")
public class ServerCommand extends BaseCommand {

    @Subcommand("survival")
    @CommandAlias("survival")
    public void teleportToSurvival(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            PaperMessageInfo.error(sender, "Tento příkaz je pro hráče!");
            return;
        }

        movePlayerToServer(player, "survival", "Survival");
    }

    @Subcommand("oldvanilla")
    @CommandAlias("oldvanilla")
    public void teleportToOldVanilla(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            PaperMessageInfo.error(sender, "Tento příkaz je pro hráče!");
            return;
        }

        movePlayerToServer(player, "oldvanilla", "Old Vanilla");
    }

    @Subcommand("hub|lobby")
    @CommandAlias("hub|lobby")
    public void teleportToLobby(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            PaperMessageInfo.error(sender, "Tento příkaz je pro hráče!");
            return;
        }

        movePlayerToServer(player, "lobby", "Lobby");
    }

    private void movePlayerToServer(Player player, String serverName, String serverNamePretty) {
        SockExchangeApi api = SockExchangeApi.instance();

        if (api.getServerInfo(serverName) != null || !api.getServerInfo(serverName).isOnline()) {
            PaperMessageInfo.warning(player, serverNamePretty + " není online! Pokud tato chyba přetrvává, napiš nám.");
            return;
        }

        PaperMessageInfo.info(player, "Teleportuji tě na server §e" + serverNamePretty + "§7...");
        api.movePlayers(Set.of(player.getName()), serverName);
    }
}
