package dev.mayuna.uzlabinamanager.paper.objects.config;

import dev.mayuna.uzlabinamanager.common.Logger;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SerializableAs("ConfigDisableInteraction")
public class ConfigDisableInteraction implements ConfigurationSerializable {

    private @Getter boolean enabled;
    private @Getter boolean ignoreCreative;
    private @Getter ListType listType;
    private List<String> entries;

    public static ConfigDisableInteraction deserialize(Map<String, Object> map) {
        ConfigDisableInteraction configDisableInteraction = new ConfigDisableInteraction();

        if (map.containsKey("enabled")) {
            configDisableInteraction.enabled = (boolean) map.get("enabled");
        }

        if (map.containsKey("ignore-creative")) {
            configDisableInteraction.ignoreCreative = (boolean) map.get("ignore-creative");
        }

        if (map.containsKey("list-type")) {
            try {
                configDisableInteraction.listType = ListType.valueOf((String) map.get("list-type"));
            } catch (Exception exception) {
                exception.printStackTrace();
                Logger.error("Invalid ListType member while deserializing: " + map.get("list-type"));
            }
        }

        if (map.containsKey("entries")) {
            configDisableInteraction.entries = (ArrayList<String>) map.get("entries");
        }

        return configDisableInteraction;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        map.put("enabled", enabled);
        map.put("ignore-creative", ignoreCreative);
        map.put("list-type", listType.name());

        if (entries != null) {
            map.put("entries", entries.toArray(new String[]{}));
        } else {
            map.put("entries", null);
        }

        return map;
    }

    public List<String> getEntries() {
        if (entries != null) {
            return entries;
        } else {
            return new ArrayList<>();
        }
    }

    public enum ListType {
        WHITELIST,
        BLACKLIST;
    }
}