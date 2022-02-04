package dev.mayuna.uzlabinamanager.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dev.mayuna.uzlabinamanager.paper.managers.UzlabinaPlayerManager;
import dev.mayuna.uzlabinamanager.paper.objects.UzlabinaPlayer;
import dev.mayuna.uzlabinamanager.paper.util.PaperMessageInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("premiumwarning")
public class PremiumWarningCommand extends BaseCommand {

    @Default
    public void togglePremiumWarningMessage(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            PaperMessageInfo.error(sender, "Tento příkaz je pro hráče!");
            return;
        }

        UzlabinaPlayer uzlabinaPlayer = UzlabinaPlayerManager.getOrCreatePlayer(player);

        uzlabinaPlayer.setTogglePremiumWarningMessage(!uzlabinaPlayer.isTogglePremiumWarningMessage());

        PaperMessageInfo.success(player, "Úspěšně sis " + (!uzlabinaPlayer.isTogglePremiumWarningMessage() ? "§eZAPNUL" : "§cVYPNUL") + "§a zprávu o autologinu!");
    }
}
