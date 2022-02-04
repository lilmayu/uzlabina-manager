package dev.mayuna.uzlabinamanager.velocity.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.mayuna.uzlabinamanager.common.Logger;

import java.io.File;
import java.nio.file.Paths;

public class VelocityUtils {

    public static File getPluginFolder() {
        File pluginFolder = Paths.get("plugins/UzlabinaManager").toFile();

        if (!pluginFolder.exists()) {
            if (!pluginFolder.mkdirs()) {
                Logger.error("Could not create plugin folder!");
            }
        }

        return pluginFolder;
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
}
