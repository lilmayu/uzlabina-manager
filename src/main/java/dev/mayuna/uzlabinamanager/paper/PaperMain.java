package dev.mayuna.uzlabinamanager.paper;

import dev.mayuna.uzlabinamanager.paper.util.Logger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperMain extends JavaPlugin {

    private static @Getter PaperMain instance;
    private static @Getter boolean debug;

    @Override
    public void onEnable() {
        instance = this;

        Logger.info("> UzlabinaManager - Paper @ " + this.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        Logger.info("Ukončuji UzlabinaManager...");

        Logger.info("o/");
    }
}
