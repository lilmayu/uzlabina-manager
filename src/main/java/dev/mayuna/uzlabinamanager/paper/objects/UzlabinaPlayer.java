package dev.mayuna.uzlabinamanager.paper.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class UzlabinaPlayer {

    // Data
    private @Getter @Setter String name;
    private @Getter @Setter String uuid;

    // Economy
    private @Getter @Setter double money;

    // Settings
    private @Getter @Setter boolean togglePremiumWarningMessage;

    public UzlabinaPlayer() {
    }

    public UzlabinaPlayer(OfflinePlayer offlinePlayer) {
        this.name = offlinePlayer.getName();
        this.uuid = offlinePlayer.getUniqueId().toString();
    }

    // Misc

    public boolean is(OfflinePlayer offlinePlayer) {
        return isByName(offlinePlayer.getName()) && isByUuid(offlinePlayer.getUniqueId().toString());
    }

    public boolean isByName(String name) {
        return this.name.equals(name);
    }

    public boolean isByUuid(String uuid) {
        return this.uuid.equals(uuid);
    }

    public boolean isByUuid(UUID uuid) {
        return isByUuid(uuid.toString());
    }
}
