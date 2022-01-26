package dev.mayuna.uzlabinamanager.common.util;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class JsonUtils {

    public static Location locationFromJsonObject(JsonObject jsonObject) {
        try {
            String world = jsonObject.get("world").getAsString();
            double x = jsonObject.get("x").getAsDouble();
            double y = jsonObject.get("y").getAsDouble();
            double z = jsonObject.get("z").getAsDouble();
            float yaw = jsonObject.get("yaw").getAsFloat();
            float pitch = jsonObject.get("pitch").getAsFloat();

            return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        } catch (UnsupportedOperationException ignored) {
            return null;
        }
    }

    public static JsonObject locationToJsonObject(Location location) {
        JsonObject jsonObject = new JsonObject();

        if (location == null) {
            jsonObject.add("world", JsonNull.INSTANCE);
            jsonObject.add("x", JsonNull.INSTANCE);
            jsonObject.add("y", JsonNull.INSTANCE);
            jsonObject.add("z", JsonNull.INSTANCE);
            jsonObject.add("yaw", JsonNull.INSTANCE);
            jsonObject.add("pitch", JsonNull.INSTANCE);
        } else {
            jsonObject.addProperty("world", location.getWorld().getName());
            jsonObject.addProperty("x", location.getWorld().getName());
            jsonObject.addProperty("y", location.getWorld().getName());
            jsonObject.addProperty("z", location.getWorld().getName());
            jsonObject.addProperty("yaw", location.getWorld().getName());
            jsonObject.addProperty("pitch", location.getWorld().getName());
        }

        return jsonObject;
    }
}
