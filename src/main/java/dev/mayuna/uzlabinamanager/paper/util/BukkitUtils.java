package dev.mayuna.uzlabinamanager.paper.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.mayuna.uzlabinamanager.common.Logger;
import org.bukkit.Bukkit;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class BukkitUtils {

    public static boolean isPluginEnabled(String pluginName) {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }

    public static boolean isPremiumNickWithOfflineUUID(String nick, UUID uuid) {
        try {
            String url = "https://api.minetools.eu/uuid/" + nick;

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() != 200) {
                return false;
            }

            JsonObject jsonObject = JsonParser.parseString(httpResponse.body()).getAsJsonObject();

            if (jsonObject.get("id").isJsonNull() || jsonObject.get("name").isJsonNull()) {
                return false;
            }

            String responseName = jsonObject.get("name").getAsString();
            String responseUuid = jsonObject.get("id").getAsString();

            return responseName.equals(nick) && !uuid.toString().replace("-", "").equals(responseUuid);
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.error("Unable to check for premium nick with offline UUID!");
            return false;
        }
    }
}
