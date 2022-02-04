package dev.mayuna.uzlabinamanager.paper.util;

import org.bukkit.entity.Player;

public class ChatUtils {

    public static void sendAutologinInfo(Player player) {
        player.sendMessage("");
        player.sendMessage("     §c§l§k!!!! §c§lPOZOR! §c§l§k!!!!");
        player.sendMessage("§cVypadá to, že máš §aPremium MC§c, ale nemáš zapnutý §eAutologin§c!");
        player.sendMessage("§cPokud se chceš připojovat bez přihlášení, použij §e/autologin");
        player.sendMessage("");
        PaperMessageInfo.warning(player, "Pokud nevlastníš §aPremium MC§6, tak tento příkaz nepoužívej!");
        PaperMessageInfo.info(player, "Pokud chceš tuto zprávu vypnout, použij §e/premiumwarning");
    }
}
