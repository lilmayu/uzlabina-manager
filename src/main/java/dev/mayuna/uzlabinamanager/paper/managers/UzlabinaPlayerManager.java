package dev.mayuna.uzlabinamanager.paper.managers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.mayuna.mayusjsonutils.JsonUtil;
import dev.mayuna.mayusjsonutils.objects.MayuJson;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.paper.objects.UzlabinaPlayer;
import dev.mayuna.uzlabinamanager.paper.util.BukkitUtils;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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

    public static boolean hasData(OfflinePlayer offlinePlayer) {
        return getPlayer(offlinePlayer) != null;
    }

    public static boolean hasDataByName(String name) {
        return getPlayerByName(name) != null;
    }

    public static boolean hasDataByUuid(UUID uuid) {
        return hasData(Bukkit.getOfflinePlayer(uuid));
    }

    public static UzlabinaPlayer getPlayer(OfflinePlayer offlinePlayer) {
        for (UzlabinaPlayer uzlabinaPlayer : players) {
            if (uzlabinaPlayer.is(offlinePlayer)) {
                return uzlabinaPlayer;
            }
        }

        return null;
    }

    public static UzlabinaPlayer getPlayerByName(String name) {
        for (UzlabinaPlayer uzlabinaPlayer : players) {
            if (uzlabinaPlayer.isByName(name)) {
                return uzlabinaPlayer;
            }
        }

        return null;
    }

    public static UzlabinaPlayer getPlayerByUuid(UUID uuid) {
        return getPlayer(Bukkit.getOfflinePlayer(uuid));
    }

    public static UzlabinaPlayer getOrCreatePlayer(OfflinePlayer offlinePlayer) {
        UzlabinaPlayer uzlabinaPlayer = getPlayer(offlinePlayer);

        if (uzlabinaPlayer == null) {
            uzlabinaPlayer = getPlayerByName(offlinePlayer.getName());
        }

        if (uzlabinaPlayer == null) {
            uzlabinaPlayer = new UzlabinaPlayer(offlinePlayer);

            players.add(uzlabinaPlayer);
        } else {
            if (!uzlabinaPlayer.isByName(offlinePlayer.getName()) && uzlabinaPlayer.isByUuid(offlinePlayer.getUniqueId())) {
                Logger.info("Hráč " + uzlabinaPlayer.getName() + " si změnil nick na " + offlinePlayer.getName() + " -> Přenastavuji...");
                uzlabinaPlayer.setName(offlinePlayer.getName());
            } else if (!uzlabinaPlayer.isByUuid(offlinePlayer.getUniqueId())) {
                if (BukkitUtils.isPremium(offlinePlayer)) {
                    Logger.info("Hráč " + uzlabinaPlayer.getName() + "(" + uzlabinaPlayer.getUuid() + ") se připojil jako originálka, přenastavuji mu UUID na " + offlinePlayer.getUniqueId() + "...");
                    uzlabinaPlayer.setUuid(offlinePlayer.getUniqueId().toString());
                } else if (!BukkitUtils.isPremium(offlinePlayer.getName(), offlinePlayer.getUniqueId()) && BukkitUtils.isPremium(uzlabinaPlayer.getName(), UUID.fromString(uzlabinaPlayer.getUuid()))) {
                    Logger.error("Hráč " + offlinePlayer.getName() + "(" + offlinePlayer.getUniqueId() + ") se snaží připojit na originální účet " + uzlabinaPlayer.getName() + "(" + uzlabinaPlayer.getUuid() + ")!");
                    if (offlinePlayer instanceof Player player) {
                        player.kick(Component.text("§cProsím, zkus se znovu připojit. Pokud tato chyba přetrvává a máš Premium MC, zkus použít /autologin a připojit se znovu."));
                    }
                }
            }
        }

        return uzlabinaPlayer;
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

        try {
            return JsonUtil.createOrLoadJsonFromFile(dataFile);
        } catch (Exception exception) {
            throw new RuntimeException("Could not create player data file: " + dataFile.getPath(), exception);
        }
    }
}
