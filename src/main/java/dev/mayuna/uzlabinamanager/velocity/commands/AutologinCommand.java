package dev.mayuna.uzlabinamanager.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.mayuna.uzlabinamanager.common.Logger;

public class AutologinCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();
        String[] args = invocation.arguments();

        if (args.length == 0) {
            if (commandSource instanceof Player player) {
                sendAutologinStatus(player);
            } else {
                Logger.error("Tento příkaz je jen pro hráče!");
            }

            return;
        }

        switch (args[0]) {
            case "enable" -> {
                if (commandSource instanceof Player player) {
                    enableAutologin(player);
                } else {
                    if (args.length != 2) {
                        Logger.error("Musíš specifikovat nick!");
                    } else {
                        // AuthManager.enableNick(args[1])
                    }
                }
            }

            case "disable" -> {
                if (commandSource instanceof Player player) {
                    disableAutologin(player);
                } else {
                    if (args.length != 2) {
                        Logger.error("Musíš specifikovat nick!");
                    } else {
                        // AuthManager.enableNick(args[1])
                    }
                }
            }
        }

    }

    private void sendAutologinStatus(Player player) {

    }

    private void enableAutologin(Player player) {

    }

    private void disableAutologin(Player player) {

    }
}
