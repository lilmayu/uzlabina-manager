package dev.mayuna.uzlabinamanager.paper.objects.config;

import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("ConfigScoreboard")
public class ConfigScoreboard implements ConfigurationSerializable {

    private @Getter boolean enabled = false;
    private @Getter String title = "§e§lDefault";
    private @Getter List<String> lines = new ArrayList<>();

    public ConfigScoreboard() {
    }

    public static ConfigScoreboard deserialize(Map<String, Object> map) {
        ConfigScoreboard configScoreboard = new ConfigScoreboard();

        if (map.containsKey("enabled")) {
            configScoreboard.enabled = (boolean) map.get("enabled");
        }

        if (map.containsKey("title")) {
            configScoreboard.title = (String) map.get("title");
        }

        if (map.containsKey("lines")) {
            configScoreboard.lines = (ArrayList<String>) map.get("lines");
        }

        return configScoreboard;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        map.put("enabled", enabled);
        map.put("title", title);

        if (lines != null) {
            map.put("lines", lines.toArray(new String[]{}));
        } else {
            map.put("lines", null);
        }

        return map;
    }
}
