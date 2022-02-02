package dev.mayuna.uzlabinamanager.paper.listeners;

import com.nickuc.openlogin.bukkit.api.events.AsyncAuthenticateEvent;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.managers.ScoreboardManager;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(null);
        // TODO: Poslat zprávu přes network, že se hráč připojil?

        Player player = event.getPlayer();
        Logger.info("Loading player " + player.getName() + " (" + player.getUniqueId() + ")...");

        // On-join
        if (PaperConfig.getOnJoinSpawnLocation() != null) {
            player.teleportAsync(PaperConfig.getOnJoinSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }

        if (PaperConfig.getOnJoinMessage() != null) {
            PaperConfig.getOnJoinMessage().send(player);
        }

        if (PaperConfig.getOnJoinSound() != null) {
            PaperConfig.getOnJoinSound().play(player);
        }

        ScoreboardManager.setupPlayer(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.quitMessage(null);

        Player player = event.getPlayer();
        Logger.info("Saving player " + player.getName() + " (" + player.getUniqueId() + ")...");

        ScoreboardManager.removePlayer(player);
    }

    // OpeNLogin listeners


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
    }
}
