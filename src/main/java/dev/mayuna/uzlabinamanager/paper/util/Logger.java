package dev.mayuna.uzlabinamanager.paper.util;

import dev.mayuna.uzlabinamanager.paper.PaperMain;

public class Logger {

    public static void info(String s) {
        PaperMain.getInstance().getLogger().info(s);
    }

    public static void warning(String s) {
        PaperMain.getInstance().getLogger().warning(s);
    }

    public static void error(String s) {
        PaperMain.getInstance().getLogger().severe(s);
    }

    public static void success(String s) {
        info("§a[SUCCESS]§r: " + s);
    }

    public static void debug(String s) {
        if (PaperMain.isDebug())
            info("§9[DEBUG]§r: " + s);
    }
}
