package dev.mayuna.uzlabinamanager.common.util;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class MessageInfo {

    /**
     * Color: §7
     */
    public static void info(Object target, String message) {
        send(target, "§7§l>§7 " + message);
    }

    /**
     * Color: §6
     */
    public static void warning(Object target, String message) {
        send(target, "§6§l[!]§6 " + message);
    }

    /**
     * Color: §c
     */
    public static void error(Object target, String message) {
        send(target, "§c§l[!!]§c " + message);
    }


    /**
     * Color: §a
     */
    public static void success(Object target, String message) {
        send(target, "§a§l>>§a " + message);
    }

    /**
     * Color: §b
     */
    public static void debug(Object target, String message) {
        send(target, "§b§l[D]§b " + message);
    }

    private static void send(Object target, String message) {
        if (target instanceof CommandSource commandSource) {
            sendMessageVelocity(commandSource, message);
        } else if (target instanceof CommandSender commandSender) {
            sendMessagePaper(commandSender, message);
        }
    }

    private static void sendMessageVelocity(CommandSource commandSource, String message) {
        commandSource.sendMessage(Component.text(message));
    }

    private static void sendMessagePaper(CommandSender commandSender, String message) {
        commandSender.sendMessage(message);
    }
}
