package dev.mayuna.uzlabinamanager.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.mayuna.uzlabinamanager.BuildConstants;
import dev.mayuna.uzlabinamanager.common.Shared;
import dev.mayuna.uzlabinamanager.common.SLogger;
import dev.mayuna.uzlabinamanager.common.SharedConfig;
import dev.mayuna.uzlabinamanager.common.util.PluginType;
import dev.mayuna.uzlabinamanager.velocity.commands.StopCommand;
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
        Shared.setPluginType(PluginType.VELOCITY);

        SLogger.info("> UzlabinaManager - Velocity @ " + BuildConstants.VERSION);

        SLogger.info("Loading config...");
        SharedConfig.load();

        SLogger.info("Loading commands...");
        loadCommands();

        SLogger.info("Loading done!");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        SLogger.info("Ukončuji UzlabinaManager...");

        SLogger.info("o/");
    }

    private static void loadCommands() {
        CommandManager commandManager = VelocityMain.getProxyServer().getCommandManager();

        commandManager.register(commandManager.metaBuilder("stop").build(), new StopCommand());
    }
}
