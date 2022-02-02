package dev.mayuna.uzlabinamanager.velocity.listeners;

import com.velocitypowered.api.event.Continuation;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import dev.mayuna.uzlabinamanager.velocity.objects.VelocityConfig;

public class PlayerListener {

    @Subscribe
    public void onPlayerPreLogin(PreLoginEvent event, Continuation continuation) {
        if (VelocityConfig.isAutologinEnabled()) {
            // Check, jeslti si hráč nastavil, že chce autologin
            //  -> Pokud ano, tak ten speedyho kod
        }
    }
}
