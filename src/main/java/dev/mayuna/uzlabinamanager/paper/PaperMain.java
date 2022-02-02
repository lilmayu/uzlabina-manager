package dev.mayuna.uzlabinamanager.paper;

import co.aikar.commands.PaperCommandManager;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.common.Shared;
import dev.mayuna.uzlabinamanager.common.util.PluginType;
import dev.mayuna.uzlabinamanager.paper.commands.ServerCommand;
import dev.mayuna.uzlabinamanager.paper.commands.UzlabinaCommand;
import dev.mayuna.uzlabinamanager.paper.listeners.PlayerBlockBreakListener;
import dev.mayuna.uzlabinamanager.paper.listeners.PlayerJoinQuitListener;
import dev.mayuna.uzlabinamanager.paper.listeners.PressurePlateListener;
import dev.mayuna.uzlabinamanager.paper.managers.ScoreboardManager;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigMessage;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigScoreboard;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigSound;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperMain extends JavaPlugin {

    private static @Getter PaperMain instance;
    private static @Getter boolean debug;

    @Override
    public void onEnable() {
        instance = this;
        Shared.setPluginType(PluginType.PAPER);

        Logger.info("> UzlabinaManager - Paper @ " + this.getDescription().getVersion());

        Logger.info("Loading config...");
        loadConfiguration();
        Logger.info("Server is recognized as " + PaperConfig.getServerType() + "!");

        Logger.info("Registering listeners...");
        registerListeners();

        Logger.info("Loading commands...");
        loadCommands();

        Logger.info("Loading managers...");
        loadManagers();

        Logger.success("Loading done!");
    }

    @Override
    public void onDisable() {
        Logger.info("Shutting down UzlabinaManager...");

        Logger.info("o/");
    }

    private void loadManagers() {
        ScoreboardManager.init();
    }

    private void loadConfiguration() {
        ConfigurationSerialization.registerClass(ConfigSound.class, "ConfigSound");
        ConfigurationSerialization.registerClass(ConfigMessage.class, "ConfigMessage");
        ConfigurationSerialization.registerClass(ConfigScoreboard.class, "ConfigScoreboard");

        PaperConfig.load();
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerJoinQuitListener(), this);
        pluginManager.registerEvents(new PlayerBlockBreakListener(), this);
        pluginManager.registerEvents(new PressurePlateListener(), this);
    }

    private void loadCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);

        paperCommandManager.registerCommand(new UzlabinaCommand());
        paperCommandManager.registerCommand(new ServerCommand());
    }
}
