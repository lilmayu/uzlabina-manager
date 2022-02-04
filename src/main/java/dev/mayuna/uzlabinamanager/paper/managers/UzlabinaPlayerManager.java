package dev.mayuna.uzlabinamanager.paper.managers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.mayuna.mayusjsonutils.JsonUtil;
import dev.mayuna.mayusjsonutils.objects.MayuJson;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.paper.objects.UzlabinaPlayer;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UzlabinaPlayerManager {

    public static final String filePath = "./plugins/{plugin}/player_data.json";

    private static final @Getter List<UzlabinaPlayer> players = new ArrayList<>(100);

    public static void init() {
        players.clear();

        load();
    }

    // Utils

    public static UzlabinaPlayer getPlayer(UUID uuid) {
        for (UzlabinaPlayer uzlabinaPlayer : players) {
            if (uzlabinaPlayer.is(uuid)) {
                return uzlabinaPlayer;
            }
        }

        return null;
    }

    public static UzlabinaPlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    public static UzlabinaPlayer getOrCreatePlayer(UUID uuid) {
        UzlabinaPlayer uzlabinaPlayer = getPlayer(uuid);

        if (uzlabinaPlayer == null) {
            uzlabinaPlayer = new UzlabinaPlayer(uuid);

            players.add(uzlabinaPlayer);
        }

        return uzlabinaPlayer;
    }

    public static UzlabinaPlayer getOrCreatePlayer(Player player) {
        return getOrCreatePlayer(player.getUniqueId());
    }

    // Data

    private static void load() {
        MayuJson mayuJson = getMayuJson();

        for (JsonElement jsonElement : mayuJson.getOrCreate("players", new JsonArray()).getAsJsonArray()) {
            try {
                players.add(new Gson().fromJson(jsonElement, UzlabinaPlayer.class));
            } catch (Exception exception) {
                exception.printStackTrace();
                Logger.error("Error while loading player with JSON '" + jsonElement + "'!");
            }
        }

        Logger.success("Loaded " + players.size() + " players!");
    }

    public static void save() {
        MayuJson mayuJson = getMayuJson();

        JsonArray playersArray = new JsonArray();
        for (UzlabinaPlayer uzlabinaPlayer : players) {
            playersArray.add(new Gson().toJsonTree(uzlabinaPlayer));
        }
        mayuJson.add("players", playersArray);

        try {
            mayuJson.saveJson();
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.error("Could not save player data file!");
        }
    }

    private static MayuJson getMayuJson() {
        File dataFile = new File(filePath.replace("{plugin}", PaperMain.getInstance().getDataFolder().getName()));
        MayuJson mayuJson = null;

        try {
            return JsonUtil.createOrLoadJsonFromFile(dataFile);
        } catch (Exception exception) {
            throw new RuntimeException("Could not create player data file: " + dataFile.getPath(), exception);
        }
    }
}
