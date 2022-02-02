package dev.mayuna.uzlabinamanager.paper.listeners;

import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBlockBreakListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBreak(BlockBreakEvent event) {
        if (PaperConfig.isAntiBlockBreakEnabled()) {
            if (PaperConfig.isAntiBlockBreakIgnoreCreative() && event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                return;
            }

            if (PaperConfig.getAntiBlockBreakWhitelistedWorlds().contains(event.getBlock().getLocation().getWorld().getName())) {
                event.setCancelled(true);
            }
        }
    }
}
