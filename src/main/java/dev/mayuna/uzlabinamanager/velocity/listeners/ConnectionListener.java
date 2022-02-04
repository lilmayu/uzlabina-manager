package dev.mayuna.uzlabinamanager.velocity.listeners;

import com.velocitypowered.api.event.Continuation;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.velocity.VelocityMain;
import dev.mayuna.uzlabinamanager.velocity.managers.VelocityPlayerManager;
import dev.mayuna.uzlabinamanager.velocity.objects.VelocityConfig;
import dev.mayuna.uzlabinamanager.velocity.objects.VelocityPlayer;
import net.kyori.adventure.text.Component;

public class ConnectionListener {

    @Subscribe
    public void onPlayerPreLogin(PreLoginEvent event, Continuation continuation) {
        if (VelocityConfig.isAutologinEnabled()) {
            VelocityPlayer player = VelocityPlayerManager.getOrCreatePlayer(event.getUsername());

            if (player.isAutologinEnabled()) {
                Logger.info("[AUTOLOGIN] Forcing Online Mode for player " + event.getUsername() + "...");
                event.setResult(PreLoginEvent.PreLoginComponentResult.forceOnlineMode());
            }
        }

        continuation.resume();
    }

    @Subscribe
    public void onPlayerChooseInitialServer(PlayerChooseInitialServerEvent event) {
        VelocityPlayer player = VelocityPlayerManager.getOrCreatePlayer(event.getPlayer().getUsername());

        if (player.isAutologinEnabled()) {
            String serverId = VelocityConfig.getLobbyServerName();

            var proxyServer = VelocityMain.getProxyServer().getServer(serverId);

            if (proxyServer.isPresent()) {
                event.setInitialServer(proxyServer.get());
            } else {
                Logger.error("Server with ID " + serverId + " does not exist!");

                if (VelocityConfig.isKickOnInvalidLobbyServer()) {
                    event.getPlayer().disconnect(Component.text("Server s ID " + serverId + " neexistuje! Kontaktuj nás."));
                }
            }
        }
    }
}
