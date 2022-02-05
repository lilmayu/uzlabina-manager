package dev.mayuna.uzlabinamanager.paper.listeners;

import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.managers.ScoreboardManager;
import dev.mayuna.uzlabinamanager.paper.managers.UzlabinaPlayerManager;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import dev.mayuna.uzlabinamanager.paper.util.BukkitUtils;
import dev.mayuna.uzlabinamanager.paper.util.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(null);
        // TODO: Poslat zprávu přes network, že se hráč připojil?

        Player player = event.getPlayer();
        Logger.info("Loading player " + player.getName() + " (" + player.getUniqueId() + ")...");

        // On-join
        if (PaperConfig.getOnJoinMessage() != null) {
            PaperConfig.getOnJoinMessage().send(player);
        }

        if (PaperConfig.getOnJoinSound() != null) {
            PaperConfig.getOnJoinSound().play(player);
        }

        if (PaperConfig.isOnJoinInformPlayerAboutAutologin()) {
            if (BukkitUtils.isPremiumNickWithOfflineUUID(player.getName(), player.getUniqueId())) {
                if (!UzlabinaPlayerManager.getOrCreatePlayer(player).isTogglePremiumWarningMessage()) {
                    ChatUtils.sendAutologinInfo(player);
                }
            }
        }

        ScoreboardManager.setupPlayer(player);
        UzlabinaPlayerManager.getOrCreatePlayer(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.quitMessage(null);

        Player player = event.getPlayer();
        Logger.info("Saving player " + player.getName() + " (" + player.getUniqueId() + ")...");

        ScoreboardManager.removePlayer(player);
    }

    @EventHandler
    public void onPlayerSpawnLocationEvent(PlayerSpawnLocationEvent event) {
        if (PaperConfig.getOnJoinSpawnLocation() != null) {
            event.setSpawnLocation(PaperConfig.getOnJoinSpawnLocation());
        }
    }
}
