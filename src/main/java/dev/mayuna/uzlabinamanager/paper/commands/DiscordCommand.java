package dev.mayuna.uzlabinamanager.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import dev.mayuna.uzlabinamanager.paper.util.PaperMessageInfo;
import org.bukkit.command.CommandSender;

@CommandAlias("discord")
public class DiscordCommand extends BaseCommand {

    @Default
    public void sendDiscordLink(CommandSender sender) {
        PaperMessageInfo.success(sender, "§eDiscord§7: §9§n" + PaperConfig.getMiscDiscordInviteLink());
    }
}
