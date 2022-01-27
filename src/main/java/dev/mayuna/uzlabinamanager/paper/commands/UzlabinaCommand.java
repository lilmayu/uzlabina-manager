package dev.mayuna.uzlabinamanager.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import dev.mayuna.uzlabinamanager.paper.util.PaperMessageInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("uzlabina|uz")
public class UzlabinaCommand extends BaseCommand {

    @Subcommand("set lobby-spawn")
    @CommandPermission("uzlabinamanager.admin")
    public void setLobbySpawn(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            Logger.error("This command is for players only!");
            return;
        }

        PaperConfig.setLobbySpawnLocation(player.getLocation());

        PaperMessageInfo.success(sender, "Lobby spawn byl nastaven na tvou momentální pozici!");
    }

    @Subcommand("reload")
    @CommandPermission("uzlabinamanager.admin")
    public void reload(CommandSender sender) {
        PaperMain.getInstance().reloadConfig();
        PaperConfig.load();

        PaperMessageInfo.success(sender, "Reloaded!");
    }
}
