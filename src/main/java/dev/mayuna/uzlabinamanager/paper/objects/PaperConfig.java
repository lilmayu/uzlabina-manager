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
    private static @Getter boolean onAuthMoveToServerEnabled;
    private static @Getter String onAuthServerName;

    // On join
    private static @Getter ConfigSound onJoinSound;
    private static @Getter ConfigMessage onJoinMessage;
    private static @Getter Location onJoinSpawnLocation;
    private static @Getter boolean onJoinInformPlayerAboutAutologin;

    // Scoreboard
    private static @Getter ConfigScoreboard scoreboard;

    // Economy
    private static @Getter boolean economyEnabled;
    private static @Getter String economyCurrency;

    // Misc
    private static @Getter String miscDiscordInviteLink;

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
        onJoinInformPlayerAboutAutologin = config.getBoolean("on-join.inform-player-about-autologin");

        // On auth
        onAuthSound = config.getObject("on-auth.sound", ConfigSound.class);
        onAuthMessage = config.getObject("on-auth.message", ConfigMessage.class);
        onAuthMoveToServerEnabled = config.getBoolean("on-auth.move-to-server.enabled");
        onAuthServerName = config.getString("on-auth.move-to-server.server-name");

        // Scoreboard
        scoreboard = config.getObject("scoreboard", ConfigScoreboard.class);

        // Economy
        economyEnabled = config.getBoolean("economy.enabled");
        economyCurrency = config.getString("economy.currency");

        // Misc
        miscDiscordInviteLink = config.getString("misc.discord-invite-link");
    }

    public static void setOnJoinSpawnLocation(Location location) {
        onJoinSpawnLocation = location;
        PaperMain.getInstance().getConfig().set("on-join.spawn-location", onJoinSpawnLocation);
        PaperMain.getInstance().saveConfig();
    }
}
