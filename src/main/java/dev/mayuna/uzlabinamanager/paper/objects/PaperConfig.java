package dev.mayuna.uzlabinamanager.paper.objects;

import dev.mayuna.uzlabinamanager.common.Shared;
import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigMessage;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigScoreboard;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigSound;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class PaperConfig {

    private static @Getter ServerType serverType = ServerType.UNKNOWN;

    // Anti block break
    private static @Getter boolean antiBlockBreakEnabled = true;
    private static @Getter boolean antiBlockBreakIgnoreCreative = true;
    private static @Getter List<String> antiBlockBreakWhitelistedWorlds = new ArrayList<>();

    // On auth
    private static @Getter ConfigSound onAuthSound;
    private static @Getter ConfigMessage onAuthMessage;

    // On join
    private static @Getter ConfigSound onJoinSound;
    private static @Getter ConfigMessage onJoinMessage;
    private static @Getter Location onJoinSpawnLocation;

    // Scoreboard
    private static @Getter ConfigScoreboard scoreboard;

    // Pressure plate boosts


    public static void load() {
        PaperMain.getInstance().getConfig().options().copyDefaults(true);
        PaperMain.getInstance().saveDefaultConfig();

        FileConfiguration config = PaperMain.getInstance().getConfig();

        // == Shared
        Shared.setDebug(config.getBoolean("debug"));

        // == Config
        serverType = ServerType.valueOfProtected(config.getString("server-type"));

        // Anti block break
        antiBlockBreakEnabled = config.getBoolean("anti-block-break.enabled");
        antiBlockBreakIgnoreCreative = config.getBoolean("anti-block-break.ignore-creative");
        antiBlockBreakWhitelistedWorlds = config.getStringList("anti-block-break.whitelisted-worlds");

        // On join
        onJoinSound = config.getObject("on-join.sound", ConfigSound.class);
        onJoinMessage = config.getObject("on-join.message", ConfigMessage.class);
        onJoinSpawnLocation = config.getLocation("on-join.spawn-location");

        // On auth
        onAuthSound = config.getObject("on-auth.sound", ConfigSound.class);
        onAuthMessage = config.getObject("on-auth.message", ConfigMessage.class);

        // Scoreboard
        scoreboard = config.getObject("scoreboard", ConfigScoreboard.class);
    }

    public static void setOnJoinSpawnLocation(Location location) {
        onJoinSpawnLocation = location;
        PaperMain.getInstance().getConfig().set("on-join.spawn-location", onJoinSpawnLocation);
        PaperMain.getInstance().saveConfig();
    }
}
