package dev.mayuna.uzlabinamanager.paper.listeners;

import com.nickuc.openlogin.bukkit.api.events.AsyncAuthenticateEvent;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OpeNLoginListener implements Listener {

    @EventHandler
    public void onPlayerAuthenticate(AsyncAuthenticateEvent event) {
        Player player = event.getPlayer();

        if (PaperConfig.isOnAuthSoundEnabled()) {
            if (PaperConfig.getOnAuthSound() != null) {
                player.playSound(player.getLocation(), PaperConfig.getOnAuthSound(), PaperConfig.getOnAuthSoundVolume(), PaperConfig.getOnAuthSoundPitch());
            }
        }

        for (String messageLine : PaperConfig.getOnAuthMessage()) {
            player.sendMessage(messageLine);
        }
    }
}
