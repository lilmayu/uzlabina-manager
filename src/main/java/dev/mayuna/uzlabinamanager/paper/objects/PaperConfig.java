package dev.mayuna.uzlabinamanager.paper.objects;

import dev.mayuna.uzlabinamanager.common.Shared;
import dev.mayuna.uzlabinamanager.paper.PaperMain;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class PaperConfig {

    private static @Getter Location lobbySpawnLocation;

    private static @Getter boolean lobbyDisabledBreakingEnabled = true;
    private static @Getter boolean lobbyDisabledBreakingIgnoreCreative = true;
    private static @Getter List<String> lobbyDisableBreakingWorlds = new ArrayList<>();

    private static @Getter boolean onAuthSoundEnabled = true;
    private static @Getter float onAuthSoundVolume = 1.0f;
    private static @Getter float onAuthSoundPitch = 1.0f;
    private static @Getter Sound onAuthSound = Sound.ENTITY_PLAYER_LEVELUP;
    private static @Getter List<String> onAuthMessage = new ArrayList<>();

    public static void load() {
        PaperMain.getInstance().getConfig().options().copyDefaults(true);
        PaperMain.getInstance().saveDefaultConfig();

        FileConfiguration config = PaperMain.getInstance().getConfig();

        // Shared
        Shared.setDebug(config.getBoolean("debug"));

        // Config
        lobbySpawnLocation = config.getLocation("lobby.spawn-location");

        lobbyDisabledBreakingEnabled = config.getBoolean("lobby.disabled-breaking.enabled");
        lobbyDisabledBreakingIgnoreCreative = config.getBoolean("lobby.disabled-breaking.ignore-creative");
        lobbyDisableBreakingWorlds = config.getStringList("lobby.disabled-breaking.worlds");

        onAuthSoundEnabled = config.getBoolean("lobby.on-auth.sound.enabled");
        onAuthSoundVolume = (float) config.getDouble("lobby.on-auth.sound.volume");
        onAuthSoundPitch = (float) config.getDouble("lobby.on-auth.sound.pitch");
        onAuthSound = Sound.valueOf(config.getString("lobby.on-auth.sound.name"));
        onAuthMessage = config.getStringList("lobby.on-auth.message");
    }

    public static void setLobbySpawnLocation(Location location) {
        lobbySpawnLocation = location;
        PaperMain.getInstance().getConfig().set("lobby.spawn-location", lobbySpawnLocation);
        PaperMain.getInstance().saveConfig();
    }
}
