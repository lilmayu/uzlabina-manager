package dev.mayuna.uzlabinamanager.common;

import dev.mayuna.uzlabinamanager.common.util.PluginType;
import lombok.Getter;
import lombok.Setter;

public class Shared {

    private static @Getter @Setter PluginType pluginType;
    private static @Getter @Setter boolean debug;

}
