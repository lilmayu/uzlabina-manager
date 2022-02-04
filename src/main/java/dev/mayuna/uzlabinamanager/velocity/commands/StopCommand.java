package dev.mayuna.uzlabinamanager.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import dev.mayuna.uzlabinamanager.velocity.util.VelocityMessageInfo;
import dev.mayuna.uzlabinamanager.velocity.VelocityMain;

public class StopCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();

        VelocityMessageInfo.info(source, "Stopping server...");
        VelocityMain.getProxyServer().shutdown();
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("uzlabinamanager.admin");
    }
}
