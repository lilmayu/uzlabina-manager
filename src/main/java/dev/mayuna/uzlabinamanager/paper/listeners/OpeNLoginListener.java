package dev.mayuna.uzlabinamanager.paper.listeners;

import com.nickuc.openlogin.bukkit.api.events.AsyncAuthenticateEvent;
import dev.mayuna.mayuslibrary.utils.StringUtils;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.commands.ServerCommand;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OpeNLoginListener implements Listener {

    @EventHandler
    public void onPlayerAuthenticate(AsyncAuthenticateEvent event) {
        Player player = event.getPlayer();

        // On-auth
        if (PaperConfig.getOnAuthMessage() != null) {
            PaperConfig.getOnAuthMessage().send(player);
        }

        if (PaperConfig.getOnAuthSound() != null) {
            PaperConfig.getOnAuthSound().play(player);
        }

        if (PaperConfig.isOnAuthMoveToServerEnabled()) {
            String serverName = PaperConfig.getOnAuthServerName();

            Logger.info("Moving player to " + serverName + "...");
            ServerCommand.movePlayerToServer(player, serverName, StringUtils.prettyString(serverName));
        }
    }
}
