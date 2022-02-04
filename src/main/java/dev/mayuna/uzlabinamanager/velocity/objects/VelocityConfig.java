package dev.mayuna.uzlabinamanager.velocity.objects;

import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.velocity.util.VelocityUtils;
import lombok.Getter;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VelocityConfig {

    // Config
    private static @Getter boolean autologinEnabled = false;
    private static @Getter String lobbyServerName = "lobby";

    private static @Getter boolean kickOnInvalidLobbyServer = true;

    public static void load() {
        CommentedConfigurationNode node = null;

        try {
            node = createConfigFile().load();

            assert node != null;
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.error("Unable to load config!");
        }

        autologinEnabled = node.node("autologinEnabled").getBoolean(false);
        lobbyServerName = node.node("lobbyServerName").getString("lobby");

        kickOnInvalidLobbyServer = node.node("kickOnInvalidLobbyServer").getBoolean(false);
    }

    private static ConfigurationLoader<CommentedConfigurationNode> createConfigFile() {
        File configFilesLocation = VelocityUtils.getPluginFolder();
        Path configFileLocation = Paths.get(configFilesLocation + "/config.yml");

        if (!Files.exists(configFileLocation)) {
            try {
                try (InputStream is = VelocityConfig.class.getClassLoader().getResourceAsStream("velocity-config.yml")) {
                    Files.copy(is, configFileLocation);
                }
            } catch (IOException e) {
                throw new IllegalStateException("Unable to create default config!");
            }
        }

        return YamlConfigurationLoader.builder().path(configFileLocation).nodeStyle(NodeStyle.BLOCK).build();
    }
}
