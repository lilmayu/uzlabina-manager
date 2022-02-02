package dev.mayuna.uzlabinamanager.paper.objects.config;

import dev.mayuna.uzlabinamanager.common.Logger;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("ConfigSound")
public class ConfigSound implements ConfigurationSerializable {

    private @Getter boolean enabled;
    private @Getter Sound sound;
    private @Getter float volume;
    private @Getter float pitch;

    public ConfigSound() {
        enabled = false;
    }

    public ConfigSound(boolean enabled, Sound sound, float volume, float pitch) {
        this.enabled = enabled;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public ConfigSound(ConfigurationSection section) {
        enabled = section.getBoolean("enabled");
        volume = (float) section.getDouble("volume");
        pitch = (float) section.getDouble("pitch");

        try {
            sound = Sound.valueOf(section.getString("name"));
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.error("Invalid sound config: " + section.getCurrentPath() + ".name: " + section.getString("name"));
        }
    }

    public static ConfigSound deserialize(Map<String, Object> map) {
        ConfigSound configSound = new ConfigSound();

        if (map.containsKey("enabled")) {
            configSound.enabled = (boolean) map.get("enabled");
        }

        if (map.containsKey("name")) {
            try {
                configSound.sound = Sound.valueOf((String) map.get("name"));
            } catch (Exception exception) {
                exception.printStackTrace();
                Logger.error("Invalid sound member while deserializing: " + map.get("sound"));
            }
        }

        if (map.containsKey("pitch")) {
            configSound.pitch = ((Double) map.get("pitch")).floatValue();
        }

        if (map.containsKey("volume")) {
            configSound.volume = ((Double) map.get("volume")).floatValue();
        }

        return configSound;
    }

    public void play(Player player) {
        if (enabled && sound != null) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        map.put("enabled", enabled);
        map.put("volume", volume);
        map.put("pitch", pitch);

        if (sound != null) {
            map.put("name", sound.name());
        } else {
            map.put("name", null);
        }

        return map;
    }

    @Override
    public String toString() {
        return "ConfigSound{" +
                "enabled=" + enabled +
                ", sound=" + sound +
                ", volume=" + volume +
                ", pitch=" + pitch +
                '}';
    }
}
