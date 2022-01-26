package dev.mayuna.uzlabinamanager.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.mayuna.mayusjsonutils.JsonUtil;
import dev.mayuna.mayusjsonutils.objects.MayuJson;
import dev.mayuna.uzlabinamanager.common.util.JsonUtils;
import dev.mayuna.uzlabinamanager.paper.objects.ServerType;
import lombok.Getter;
import org.bukkit.Location;

public class SharedConfig {

    public static final String main = "./uzlabinamanager.json";
    public static final String paper = "./uzlabinamanager_paper.json";
    public static final String velocity = "./uzlabinamanager_velocity.json";

    private static @Getter boolean debug = false;

    public static void load() {
        loadMain();

        switch (Shared.getPluginType()) {
            case PAPER -> {
                PaperConfig.load();
            }
            case VELOCITY -> {
                VelocityConfig.load();
            }
        }
    }

    private static void loadMain() {
        MayuJson mayuJson;

        try {
            mayuJson = JsonUtil.createOrLoadJsonFromFile(main);
        } catch (Exception exception) {
            throw new RuntimeException("Unable to load Main Config: " + main, exception);
        }

        debug = mayuJson.getOrCreate("debug", new JsonPrimitive(debug)).getAsBoolean();

        try {
            mayuJson.saveJson();
        } catch (Exception exception) {
            throw new RuntimeException("Unable to save Main Config: " + main, exception);
        }
    }

    public static class PaperConfig {

        private static @Getter ServerType serverType = ServerType.UNKNOWN;
        private static @Getter Location lobbySpawnLocation = null;

        protected static void load() {
            MayuJson mayuJson;

            try {
                mayuJson = JsonUtil.createOrLoadJsonFromFile(paper);
            } catch (Exception exception) {
                throw new RuntimeException("Unable to load Paper Config: " + paper, exception);
            }

            loadMain(mayuJson);
            loadLobby(mayuJson);

            try {
                mayuJson.saveJson();
            } catch (Exception exception) {
                throw new RuntimeException("Unable to save Paper Config: " + paper, exception);
            }
        }

        private static void loadMain(MayuJson mayuJson) {
            serverType = ServerType.valueOfProtected(mayuJson.getOrCreate("serverType", new JsonPrimitive(serverType.name())).getAsString());
        }

        private static void loadLobby(MayuJson mayuJson) {
            MayuJson lobbyJson = new MayuJson(mayuJson.getOrCreate("lobby", new JsonObject()).getAsJsonObject());

            lobbySpawnLocation = JsonUtils.locationFromJsonObject(lobbyJson.getOrCreate("spawnLocation", JsonUtils.locationToJsonObject(lobbySpawnLocation)).getAsJsonObject());

            mayuJson.add("lobby", lobbyJson.getJsonObject());
        }
    }

    public static class VelocityConfig {

        protected static void load() {
            MayuJson mayuJson;

            try {
                mayuJson = JsonUtil.createOrLoadJsonFromFile(paper);
            } catch (Exception exception) {
                throw new RuntimeException("Unable to load Velocity Config: " + velocity, exception);
            }

            //

            try {
                mayuJson.saveJson();
            } catch (Exception exception) {
                throw new RuntimeException("Unable to save Velocity Config: " + paper, exception);
            }
        }
    }
}
