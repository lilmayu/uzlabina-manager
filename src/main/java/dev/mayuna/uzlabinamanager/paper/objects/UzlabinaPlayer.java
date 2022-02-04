package dev.mayuna.uzlabinamanager.paper.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UzlabinaPlayer {

    // Data
    private @Getter String uuid;

    // Economy
    private @Getter @Setter double money;

    // Settings
    private @Getter @Setter boolean togglePremiumWarningMessage;

    public UzlabinaPlayer() {
    }

    public UzlabinaPlayer(UUID uuid) {
        this.uuid = uuid.toString();
    }

    // Misc

    public boolean is(String uuid) {
        return this.uuid.equals(uuid);
    }

    public boolean is(UUID uuid) {
        return is(uuid.toString());
    }

    public boolean is(Player player) {
        return is(player.getUniqueId());
    }
}
