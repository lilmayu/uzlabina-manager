package dev.mayuna.uzlabinamanager.paper;

import dev.mayuna.uzlabinamanager.common.SLogger;
import dev.mayuna.uzlabinamanager.common.Shared;
import dev.mayuna.uzlabinamanager.common.SharedConfig;
import dev.mayuna.uzlabinamanager.common.util.PluginType;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperMain extends JavaPlugin {

    private static @Getter PaperMain instance;
    private static @Getter boolean debug;

    @Override
    public void onEnable() {
        instance = this;
        Shared.setPluginType(PluginType.PAPER);

        SLogger.info("> UzlabinaManager - Paper @ " + this.getDescription().getVersion());

        SLogger.info("Loading config...");
        SharedConfig.load();

        SLogger.success("Loading done!");
    }

    @Override
    public void onDisable() {
        SLogger.info("Ukončuji UzlabinaManager...");

        SLogger.info("o/");
    }
}
