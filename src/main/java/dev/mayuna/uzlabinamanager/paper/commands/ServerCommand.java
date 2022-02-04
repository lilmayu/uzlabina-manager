package dev.mayuna.uzlabinamanager.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.gmail.tracebachi.SockExchange.Spigot.SockExchangeApi;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.mayuna.mayuslibrary.utils.StringUtils;
import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.paper.util.PaperMessageInfo;
import org.bukkit.Bukkit;
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

    @Subcommand("sendother")
    @CommandPermission("uzlabinamanager.admin")
    public void sendOtherPlayerToServer(CommandSender sender, String playerName, String server) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            PaperMessageInfo.error(sender, "Tento hráč není online!");
            return;
        }

        PaperMessageInfo.success(sender, "Posílám hráče §e" + player.getName() + "§a na server §e" + server + "§a!");
        movePlayerToServer(player, server, StringUtils.prettyString(server));
    }

    public static void movePlayerToServer(Player player, String serverName, String serverNamePretty) {
        PaperMessageInfo.info(player, "Teleportuji tě na server §e" + serverNamePretty + "§7...");

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(PaperMain.getInstance(), "BungeeCord", out.toByteArray());
    }
}
