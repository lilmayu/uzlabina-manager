package dev.mayuna.uzlabinamanager.common;

public class SharedMessageInfo {

    /**
     * Color: §7
     */
    public static String info(String message) {
        return "§7§l>§7 " + message;
    }

    /**
     * Color: §6
     */
    public static String warning(String message) {
        return "§6§l[!]§6 " + message;
    }

    /**
     * Color: §c
     */
    public static String error(String message) {
        return "§c§l[!!]§c " + message;
    }


    /**
     * Color: §a
     */
    public static String success(String message) {
        return "§a§l>>§a " + message;
    }

    /**
     * Color: §b
     */
    public static String debug(String message) {
        return "§b§l[D]§b " + message;
    }
}
