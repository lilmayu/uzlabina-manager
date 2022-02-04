package dev.mayuna.uzlabinamanager.paper.objects;

public enum ServerType {

    UNKNOWN,
    LOGIN,
    LOBBY,
    SURVIVAL,
    OLD_VANILLA;

    public static ServerType valueOfProtected(String value) {
        try {
            return ServerType.valueOf(value);
        } catch (Exception ignored) {
            return null;
        }
    }
}
