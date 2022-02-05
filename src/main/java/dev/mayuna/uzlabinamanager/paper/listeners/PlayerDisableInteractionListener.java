package dev.mayuna.uzlabinamanager.paper.listeners;

import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigDisableInteraction;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerDisableInteractionListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        var disableInteraction = getDisableInteraction(event.getPlayer().getGameMode());

        if (disableInteraction == null) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();;

        if (clickedBlock == null) {
            return;
        }

        process(event, disableInteraction, clickedBlock.getType().name());
    }

    @EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.PLAYER) {
            return;
        }

        var disableInteraction = getDisableInteraction(event.getPlayer().getGameMode());

        if (disableInteraction == null) {
            return;
        }

        process(event, disableInteraction, event.getRightClicked().getType().name());
    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }

        var disableInteraction = getDisableInteraction(((Player) event.getDamager()).getGameMode());

        if (disableInteraction == null) {
            return;
        }

        process(event, disableInteraction, event.getEntity().getType().name());

    }

    private void process(Cancellable cancellable, ConfigDisableInteraction disableInteraction, String interactedMaterial) {
        boolean doesMatch = false;

        interactedMaterial = interactedMaterial.toLowerCase();

        for (String material : disableInteraction.getEntries()) {
            Logger.info("0: " + material + " // " + interactedMaterial);
            if (material == null) {
                continue;
            }

            material = material.toLowerCase();

            if (material.startsWith("any:")) {
                material = material.replace("any:", "");

                if (interactedMaterial.contains(material)) {
                    doesMatch = true;
                    break;
                }
            } else {
                if (interactedMaterial.equalsIgnoreCase(material)) {
                    doesMatch = true;
                    break;
                }
            }
        }

        Logger.info("0.5: " + doesMatch);

        // Whitelist = Jen to, co tam je
        // Blacklist = vše ale ignorovat to co tam je

        if (doesMatch) {
            switch (disableInteraction.getListType()) {
                case WHITELIST -> {
                    Logger.info("1");
                }
                case BLACKLIST -> {
                    Logger.info("2");
                    cancellable.setCancelled(true);
                }
            }
        } else {
            switch (disableInteraction.getListType()) {
                case WHITELIST -> {
                    Logger.info("3");
                    cancellable.setCancelled(true);
                }
                case BLACKLIST -> {
                    Logger.info("4");
                }
            }
        }
    }

    private ConfigDisableInteraction getDisableInteraction(GameMode gameMode) {
        var disableInteraction = PaperConfig.getMiscDisableInteraction();

        if (disableInteraction == null) {
            return null;
        }

        if (!disableInteraction.isEnabled()) {
            return null;
        }

        if (gameMode == GameMode.CREATIVE && disableInteraction.isIgnoreCreative()) {
            return null;
        }

        return disableInteraction;
    }
}
