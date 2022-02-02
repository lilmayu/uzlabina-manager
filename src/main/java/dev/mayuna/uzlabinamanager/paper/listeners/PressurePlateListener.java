package dev.mayuna.uzlabinamanager.paper.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PressurePlateListener implements Listener {

    @EventHandler
    public void onPhysicalInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }

    }
}
