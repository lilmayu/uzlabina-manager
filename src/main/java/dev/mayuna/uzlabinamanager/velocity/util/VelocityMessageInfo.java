package dev.mayuna.uzlabinamanager.velocity.util;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.velocitypowered.api.command.CommandSource;
import dev.mayuna.uzlabinamanager.common.SharedMessageInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.List;

public class VelocityMessageInfo {

    /**
     * Color: §7
     */
    public static void info(CommandSource commandSource, String message) {
        send(commandSource, SharedMessageInfo.info(message));
    }

    /**
     * Color: §6
     */
    public static void warning(CommandSource commandSource, String message) {
        send(commandSource, SharedMessageInfo.warning(message));
    }

    /**
     * Color: §c
     */
    public static void error(CommandSource commandSource, String message) {
        send(commandSource, SharedMessageInfo.error(message));
    }


    /**
     * Color: §a
     */
    public static void success(CommandSource commandSource, String message) {
        send(commandSource, SharedMessageInfo.success(message));
    }

    /**
     * Color: §b
     */
    public static void debug(CommandSource commandSource, String message) {
        send(commandSource, SharedMessageInfo.debug(message));
    }

    private static void send(CommandSource commandSource, String message) {
        commandSource.sendMessage(Component.text(message));
    }

    public static void sendLines(CommandSource commandSource, List<String> lines) {
        for (String line : lines) {
            send(commandSource, line);
        }
    }
}
