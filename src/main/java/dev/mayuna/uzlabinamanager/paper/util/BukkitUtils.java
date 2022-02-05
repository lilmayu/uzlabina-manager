package dev.mayuna.uzlabinamanager.paper.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.mayuna.uzlabinamanager.common.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class BukkitUtils {

    public static boolean isPluginEnabled(String pluginName) {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }

    public static boolean isPremiumNickWithOfflineUUID(String name, UUID uuid) {
        JsonObject jsonObject = requestMineTools(name);

        if (jsonObject == null) {
            return false;
        }

        if (jsonObject.get("id").isJsonNull() || jsonObject.get("name").isJsonNull()) {
            return false;
        }

        String responseName = jsonObject.get("name").getAsString();
        String responseUuid = jsonObject.get("id").getAsString();

        return responseName.equals(name) && !uuid.toString().replace("-", "").equals(responseUuid);
    }

    public static boolean isPremium(String name, UUID uuid) {
        JsonObject jsonObject = requestMineTools(name);

        if (jsonObject == null) {
            return false;
        }

        if (jsonObject.get("id").isJsonNull() || jsonObject.get("name").isJsonNull()) {
            return false;
        }

        return jsonObject.get("id").getAsString().equals(uuid.toString().replace("-", ""));
    }

    public static boolean isPremium(OfflinePlayer offlinePlayer) {
        return isPremium(offlinePlayer.getName(), offlinePlayer.getUniqueId());
    }

    public static JsonObject requestMineTools(String name) {
        return requestMineToolsEx(name);
    }

    public static JsonObject requestMineTools(UUID uuid) {
        return requestMineToolsEx(uuid.toString());
    }

    private static JsonObject requestMineToolsEx(String string) {
        try {
            String url = "https://api.minetools.eu/uuid/" + string;

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() != 200) {
                return null;
            }

            return JsonParser.parseString(httpResponse.body()).getAsJsonObject();
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.error("Unable to request MineTools with " + string + "!");
            return null;
        }
    }
}
