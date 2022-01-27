package dev.mayuna.uzlabinamanager.paper;

import co.aikar.commands.PaperCommandManager;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.common.Shared;
import dev.mayuna.uzlabinamanager.common.util.PluginType;
import dev.mayuna.uzlabinamanager.paper.commands.UzlabinaCommand;
import dev.mayuna.uzlabinamanager.paper.listeners.OpeNLoginListener;
import dev.mayuna.uzlabinamanager.paper.listeners.PlayerListener;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
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

        Logger.info("Registering listeners...");
        registerListeners();

        Logger.info("Loading commands...");
        loadCommands();

        Logger.success("Loading done!");
    }

    @Override
    public void onDisable() {
        Logger.info("Ukončuji UzlabinaManager...");

        Logger.info("o/");
    }

    private void loadConfiguration() {
        PaperConfig.load();
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new OpeNLoginListener(), this);
    }

    private void loadCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);

        paperCommandManager.registerCommand(new UzlabinaCommand());
    }
}
