package dev.mayuna.uzlabinamanager.paper.util;

import org.bukkit.command.CommandSender;

public class PaperMessageInfo {

    /**
     * Color: §7
     */
    public static void info(CommandSender commandSender, String message) {
        send(commandSender, "§7§l>§7 " + message);
    }

    /**
     * Color: §6
     */
    public static void warning(CommandSender commandSender, String message) {
        send(commandSender, "§6§l[!]§6 " + message);
    }

    /**
     * Color: §c
     */
    public static void error(CommandSender commandSender, String message) {
        send(commandSender, "§c§l[!!]§c " + message);
    }


    /**
     * Color: §a
     */
    public static void success(CommandSender commandSender, String message) {
        send(commandSender, "§a§l>>§a " + message);
    }

    /**
     * Color: §b
     */
    public static void debug(CommandSender commandSender, String message) {
        send(commandSender, "§b§l[D]§b " + message);
    }

    private static void send(CommandSender commandSender, String message) {
        commandSender.sendMessage(message);
    }
}
