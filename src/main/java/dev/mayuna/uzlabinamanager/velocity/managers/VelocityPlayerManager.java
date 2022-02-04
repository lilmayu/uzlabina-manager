package dev.mayuna.uzlabinamanager.velocity.managers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.mayuna.mayusjsonutils.JsonUtil;
import dev.mayuna.mayusjsonutils.objects.MayuJson;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.velocity.objects.VelocityPlayer;
import dev.mayuna.uzlabinamanager.velocity.util.VelocityUtils;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VelocityPlayerManager {

    private static final @Getter List<VelocityPlayer> players = new ArrayList<>();

    public static void init() {
        players.clear();

        loadDataFile();
    }

    private static void loadDataFile() {
        MayuJson mayuJson = getDataFile();

        for (JsonElement jsonElement : mayuJson.getOrCreate("players", new JsonArray()).getAsJsonArray()) {
            players.add(new Gson().fromJson(jsonElement, VelocityPlayer.class));
        }
    }

    public static void save() {
        MayuJson mayuJson = getDataFile();

        // players
        JsonArray playersArray = new JsonArray();
        for (VelocityPlayer player : players) {
            playersArray.add(VelocityUtils.getGson().toJsonTree(player));
        }
        mayuJson.add("players", playersArray);

        // Saving
        try {
            mayuJson.saveJson();
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.error("Could not save data file: " + VelocityUtils.getPluginFolder().getPath() + "/data.json");
        }
    }

    private static MayuJson getDataFile() {
        File dataFile = new File(VelocityUtils.getPluginFolder().getPath() + "/data.json");

        MayuJson mayuJson = null;
        try {
            mayuJson = JsonUtil.createOrLoadJsonFromFile(dataFile);
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.error("Could not create data file: " + VelocityUtils.getPluginFolder().getPath() + "/data.json");
        }

        return mayuJson;
    }

    public static VelocityPlayer getPlayer(String name) {
        synchronized (players) {
            for (VelocityPlayer velocityPlayer : players) {
                if (velocityPlayer.is(name)) {
                    return velocityPlayer;
                }
            }

            return null;
        }
    }

    public static VelocityPlayer getOrCreatePlayer(String name) {
        VelocityPlayer velocityPlayer = getPlayer(name);

        if (velocityPlayer == null) {
            velocityPlayer = new VelocityPlayer(name);
            players.add(velocityPlayer);
        }

        return velocityPlayer;
    }
}
