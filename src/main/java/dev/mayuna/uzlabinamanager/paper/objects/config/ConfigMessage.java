package dev.mayuna.uzlabinamanager.paper.objects.config;

import dev.mayuna.uzlabinamanager.paper.util.PaperMessageInfo;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("ConfigMessage")
public class ConfigMessage implements ConfigurationSerializable {

    private @Getter boolean enabled;
    private @Getter List<String> content;

    public ConfigMessage() {
    }

    public static ConfigMessage deserialize(Map<String, Object> map) {
        ConfigMessage configMessage = new ConfigMessage();

        if (map.containsKey("enabled")) {
            configMessage.enabled = (boolean) map.get("enabled");
        }

        if (map.containsKey("content")) {
            configMessage.content = (ArrayList<String>) map.get("content");
        }

        return configMessage;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        map.put("enabled", enabled);

        if (content != null) {
            map.put("content", content.toArray(new String[]{}));
        } else {
            map.put("content", null);
        }

        return map;
    }

    public void send(Player player) {
        if (enabled && content != null) {
            PaperMessageInfo.sendLines(player, content);
        }
    }
}
