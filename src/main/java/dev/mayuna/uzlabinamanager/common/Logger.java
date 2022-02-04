package dev.mayuna.uzlabinamanager.common;

import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.velocity.VelocityMain;

public class Logger {

    public static void info(String message) {
        process(LogType.INFO, message);
    }

    public static void warning(String message) {
        process(LogType.WARNING, message);
    }

    public static void error(String message) {
        process(LogType.ERROR, message);
    }

    public static void success(String message) {
        process(LogType.SUCCESS, message);
    }

    public static void debug(String message) {
        if (Shared.isDebug())
            process(LogType.DEBUG, message);

    }

    private static void process(LogType logType, String message) {
        switch (Shared.getPluginType()) {
            case PAPER -> {
                switch (logType) {
                    case INFO -> {
                        PaperMain.getInstance().getLogger().info(message);
                    }
                    case WARNING -> {
                        PaperMain.getInstance().getLogger().warning(message);
                    }
                    case ERROR -> {
                        PaperMain.getInstance().getLogger().severe(message);
                    }
                    case SUCCESS -> {
                        PaperMain.getInstance().getLogger().info("§a[SUCCESS]§r: " + message);
                    }
                    case DEBUG -> {
                        PaperMain.getInstance().getLogger().info("§9[DEBUG]§r: " + message);
                    }
                }
            }
            case VELOCITY -> {
                var logger = VelocityMain.getLogger();
                switch (logType) {
                    case INFO -> {
                        logger.info(message);
                    }
                    case WARNING -> {
                        logger.warn(message);
                    }
                    case ERROR -> {
                        logger.error(message);
                    }
                    case SUCCESS -> {
                        logger.info("§a[SUCCESS]§r: " + message);
                    }
                    case DEBUG -> {
                        logger.info("§9[DEBUG]§r: " + message);
                    }
                }
            }
        }
    }

    private enum LogType {
        INFO,
        WARNING,
        ERROR,
        SUCCESS,
        DEBUG;
    }
}
