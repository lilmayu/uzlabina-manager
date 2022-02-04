package dev.mayuna.uzlabinamanager.velocity.objects;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class VelocityPlayer {

    // Data
    private @Getter @SerializedName("name") String name;

    // Player config
    private @Getter @Setter @SerializedName("autologinEnabled") boolean autologinEnabled;

    public VelocityPlayer() {
    }

    public VelocityPlayer(String name) {
        this.name = name;
    }

    // Misc

    public boolean is(String name) {
        return this.name.equals(name);
    }
}
