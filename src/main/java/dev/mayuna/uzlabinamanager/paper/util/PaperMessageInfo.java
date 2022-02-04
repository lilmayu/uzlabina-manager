package dev.mayuna.uzlabinamanager.paper.util;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import dev.mayuna.uzlabinamanager.common.SharedMessageInfo;
import org.bukkit.command.CommandSender;

import java.util.List;

public class PaperMessageInfo {

    /**
     * Color: §7
     */
    public static void info(CommandSender commandSender, String message) {
        send(commandSender, SharedMessageInfo.info(message));
    }

    /**
     * Color: §6
     */
    public static void warning(CommandSender commandSender, String message) {
        send(commandSender, SharedMessageInfo.warning(message));
    }

    /**
     * Color: §c
     */
    public static void error(CommandSender commandSender, String message) {
        send(commandSender, SharedMessageInfo.error(message));
    }


    /**
     * Color: §a
     */
    public static void success(CommandSender commandSender, String message) {
        send(commandSender, SharedMessageInfo.success(message));
    }

    /**
     * Color: §b
     */
    public static void debug(CommandSender commandSender, String message) {
        send(commandSender, SharedMessageInfo.debug(message));
    }

    private static void send(CommandSender commandSender, String message) {
        commandSender.sendMessage(IridiumColorAPI.process(message));
    }

    public static void sendLines(CommandSender sender, List<String> lines) {
        for (String line : lines) {
            send(sender, line);
        }
    }
}
