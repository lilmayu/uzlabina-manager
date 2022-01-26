package dev.mayuna.uzlabinamanager.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.mayuna.uzlabinamanager.BuildConstants;
import lombok.Getter;
import org.slf4j.Logger;

@Plugin(id = "uzlabinamanager", name = "UzlabinaManager", version = BuildConstants.VERSION, description = "Velocity Plugin pro Úžlabinský Minecraft Server", url = "https://mayuna.dev", authors = {"mayuna#8016"})
public class VelocityMain {

    private static @Getter ProxyServer proxyServer;
    private static @Getter Logger logger;

    @Inject
    public VelocityMain(ProxyServer proxyServer, Logger logger) {
        VelocityMain.proxyServer = proxyServer;
        VelocityMain.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("> UzlabinaManager - Velocity @ " + BuildConstants.VERSION);
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        logger.info("Ukončuji UzlabinaManager...");

        logger.info("o/");
    }

}
