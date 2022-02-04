package dev.mayuna.uzlabinamanager.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.mayuna.uzlabinamanager.BuildConstants;
import dev.mayuna.uzlabinamanager.common.Shared;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.common.util.PluginType;
import dev.mayuna.uzlabinamanager.common.util.Utils;
import dev.mayuna.uzlabinamanager.velocity.commands.AutologinCommand;
import dev.mayuna.uzlabinamanager.velocity.commands.StopCommand;
import dev.mayuna.uzlabinamanager.velocity.listeners.ConnectionListener;
import dev.mayuna.uzlabinamanager.velocity.managers.VelocityPlayerManager;
import dev.mayuna.uzlabinamanager.velocity.objects.VelocityConfig;
import lombok.Getter;

@Plugin(id = "uzlabinamanager", name = "UzlabinaManager", version = BuildConstants.VERSION, description = "Velocity Plugin pro Úžlabinský Minecraft Server", url = "https://mayuna.dev", authors = {"mayuna#8016"})
public class VelocityMain {

    private static @Getter ProxyServer proxyServer;
    private static @Getter org.slf4j.Logger logger;

    @Inject
    public VelocityMain(ProxyServer proxyServer, org.slf4j.Logger logger) {
        VelocityMain.proxyServer = proxyServer;
        VelocityMain.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Shared.setPluginType(PluginType.VELOCITY);

        Utils.printLogo();
        Logger.info("> UzlabinaManager - Velocity @ " + BuildConstants.VERSION);
        Logger.info("> Made by mayuna#8016");

        Logger.info("Loading config...");
        VelocityConfig.load();

        Logger.info("Registering listeners...");
        registerListeners();

        Logger.info("Loading commands...");
        loadCommands();

        Logger.info("Loading managers...");
        loadManagers();

        Logger.success("Loading done!");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        Logger.info("Shutting down UzlabinaManager...");

        VelocityPlayerManager.save();

        Logger.info("o/");
    }

    private void registerListeners() {
        EventManager eventManager = proxyServer.getEventManager();

        eventManager.register(this, new ConnectionListener());
    }

    private static void loadCommands() {
        CommandManager commandManager = VelocityMain.getProxyServer().getCommandManager();

        commandManager.register(commandManager.metaBuilder("stop").build(), new StopCommand());
        commandManager.register(commandManager.metaBuilder("autologin").build(), new AutologinCommand());
    }

    private static void loadManagers() {
        VelocityPlayerManager.init();
    }
}
