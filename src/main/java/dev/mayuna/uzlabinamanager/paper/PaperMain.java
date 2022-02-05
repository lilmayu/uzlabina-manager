package dev.mayuna.uzlabinamanager.paper;

import co.aikar.commands.PaperCommandManager;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.common.Shared;
import dev.mayuna.uzlabinamanager.common.util.PluginType;
import dev.mayuna.uzlabinamanager.common.util.Utils;
import dev.mayuna.uzlabinamanager.paper.commands.*;
import dev.mayuna.uzlabinamanager.paper.listeners.*;
import dev.mayuna.uzlabinamanager.paper.managers.EconomyManager;
import dev.mayuna.uzlabinamanager.paper.managers.ScoreboardManager;
import dev.mayuna.uzlabinamanager.paper.managers.UzlabinaPlayerManager;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigDisableInteraction;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigMessage;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigScoreboard;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigSound;
import dev.mayuna.uzlabinamanager.paper.util.BukkitUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperMain extends JavaPlugin {

    private static @Getter PaperMain instance;
    private static @Getter boolean crashed;

    @Override
    public void onEnable() {
        instance = this;
        try {
            Shared.setPluginType(PluginType.PAPER);

            Utils.printLogo();
            Logger.info("> UzlabinaManager - Paper @ " + this.getDescription().getVersion());
            Logger.info("> Made by mayuna#8016");

            Logger.info("Loading config...");
            loadConfiguration();
            Logger.info("Server is recognized as " + PaperConfig.getServerType() + "!");

            Logger.info("Registering listeners...");
            registerListeners();

            Logger.info("Loading managers...");
            loadManagers();

            Logger.info("Loading commands...");
            loadCommands();

            Logger.success("Loading done!");
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.error("Exception occurred while loading plugin!");
            crashed = true;
            this.setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
        Logger.info("Shutting down UzlabinaManager...");

        this.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord");

        if (!crashed) {
            UzlabinaPlayerManager.save();
        }

        Logger.info("o/");
        instance = null;
    }

    private void loadManagers() {
        UzlabinaPlayerManager.init();
        ScoreboardManager.init();
        EconomyManager.init();
    }

    private void loadConfiguration() {
        ConfigurationSerialization.registerClass(ConfigSound.class, "ConfigSound");
        ConfigurationSerialization.registerClass(ConfigMessage.class, "ConfigMessage");
        ConfigurationSerialization.registerClass(ConfigScoreboard.class, "ConfigScoreboard");
        ConfigurationSerialization.registerClass(ConfigDisableInteraction.class, "ConfigDisableInteraction");

        PaperConfig.load();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerJoinQuitListener(), this);
        pluginManager.registerEvents(new PlayerBlockBreakListener(), this);
        pluginManager.registerEvents(new PressurePlateListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new PlayerDisableInteractionListener(), this);

        if (BukkitUtils.isPluginEnabled("OpeNLogin")) {
            Logger.debug("OpeNLogin is loaded! Registering listener...");
            pluginManager.registerEvents(new OpeNLoginListener(), this);
        } else {
            Logger.warning("OpeNLogin is not installed!");
        }
    }

    private void loadCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);

        paperCommandManager.registerCommand(new UzlabinaCommand());
        paperCommandManager.registerCommand(new ServerCommand());
        paperCommandManager.registerCommand(new DiscordCommand());
        paperCommandManager.registerCommand(new PremiumWarningCommand());

        if (EconomyManager.isEconomyEnabled()) {
            Logger.info("Economy is enabled - Registering economy command...");
            paperCommandManager.registerCommand(new EconomyCommand());
        }
    }
}
