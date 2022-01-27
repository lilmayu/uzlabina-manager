package dev.mayuna.uzlabinamanager.paper.listeners;

import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(null);
        // TODO: Poslat zprávu přes network, že se hráč připojil?

        Player player = event.getPlayer();
        Logger.info("Loading player " + player.getName() + " (" + player.getUniqueId() + ")...");

        player.teleportAsync(PaperConfig.getLobbySpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBreak(BlockBreakEvent event) {
        if (PaperConfig.isLobbyDisabledBreakingEnabled()) {
            if (PaperConfig.isLobbyDisabledBreakingIgnoreCreative() && event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                return;
            }

            if (PaperConfig.getLobbyDisableBreakingWorlds().contains(event.getBlock().getLocation().getWorld().getName())) {
                event.setCancelled(true);
            }
        }
    }
}
