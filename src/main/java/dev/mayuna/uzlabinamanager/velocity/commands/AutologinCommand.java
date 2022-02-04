package dev.mayuna.uzlabinamanager.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.velocity.managers.VelocityPlayerManager;
import dev.mayuna.uzlabinamanager.velocity.objects.VelocityPlayer;
import dev.mayuna.uzlabinamanager.velocity.util.VelocityMessageInfo;
import net.kyori.adventure.text.Component;

public class AutologinCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();
        String[] args = invocation.arguments();

        // /autologin -> zapne / vypne autologin
        // /autologin <enable|disable|status> <nick> -> zapne / vypne autologin pro hráče s nickem
        //  ^ Jen lidi s permissí uzlabinamanager.admin

        // /autologin
        if (args.length == 0) {
            if (commandSource instanceof Player player) {
                VelocityPlayer velocityPlayer = VelocityPlayerManager.getOrCreatePlayer(player.getUsername());

                changeAutologinSettings(commandSource, player.getUsername(), !velocityPlayer.isAutologinEnabled());
            } else {
                Logger.error("Tento příkaz je jen pro hráče!");
            }

            return;
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "reload" -> {
                    VelocityMessageInfo.info(commandSource, "Reloading...");

                    VelocityPlayerManager.init();
                    return;
                }
            }
        }

        // /autologin <enable|disable|status> <nick>
        if (args.length == 2) {
            if (!commandSource.hasPermission("uzlabinamanager.admin")) {
                VelocityMessageInfo.error(commandSource, "Na tento příkaz nemáš práva!");
                return;
            }

            switch (args[0]) {
                case "enable" -> {
                    changeAutologinSettings(commandSource, args[1], true);
                }
                case "disable" -> {
                    changeAutologinSettings(commandSource, args[1], false);
                }
                case "create" -> {
                    VelocityPlayerManager.getOrCreatePlayer(args[1]);
                    VelocityPlayerManager.save();

                    VelocityMessageInfo.success(commandSource, "Úspěšně jsi vytvořil hráče §e" + args[1] + "§a!");
                }
                case "status" -> {
                    VelocityPlayer velocityPlayer = VelocityPlayerManager.getPlayer(args[1]);

                    if (velocityPlayer == null) {
                        VelocityMessageInfo.error(commandSource, "Tento hráč neexistuje!");
                    } else {
                        VelocityMessageInfo.info(commandSource, "Hráč §e" + velocityPlayer.getName() + "§7 má " + (velocityPlayer.isAutologinEnabled() ? "§aZAPNUTÝ" : "§cVYPNUTÝ") + "§7 autologin.");
                    }
                }
                default -> {
                    VelocityMessageInfo.error(commandSource, "Nesprávný syntax! /autologin <enable|disable|status> <nick>");
                }
            }

            return;
        }

        VelocityMessageInfo.error(commandSource, "Nesprávný syntax! /autologin [<enable|disable|status|reload>] [<nick>]");
    }

    private void changeAutologinSettings(CommandSource commandSource, String nick, boolean enableAutologin) {
        VelocityPlayer velocityPlayer = VelocityPlayerManager.getOrCreatePlayer(nick);
        velocityPlayer.setAutologinEnabled(enableAutologin);
        VelocityPlayerManager.save();

        if (commandSource instanceof Player player) {
            VelocityMessageInfo.success(commandSource, "Úspěšně sis " + (enableAutologin ? "§eZAPNUL" : "§cVYPNUL") + "§a autologin! Prosím, odpoj se a připoj.");
        } else {
            VelocityMessageInfo.success(commandSource, "Úspěšně jsi nastavil hráči §e" + nick + "§a autologin na " + (enableAutologin ? "§eZAPNUTÝ" : "§cVYPNUTÝ") + "!");
        }
    }
}
