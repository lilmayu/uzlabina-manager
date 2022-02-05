package dev.mayuna.uzlabinamanager.paper.listeners;

import dev.mayuna.uzlabinamanager.common.Logger;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (PaperConfig.isChatEnabled()) {
            Player player = event.getPlayer();

            if (event.message() instanceof TextComponent textComponent) {
                String originalMessage = textComponent.content();
                String chatStyle = PlaceholderAPI.setPlaceholders(player, PaperConfig.getChatStyle());

                chatStyle = chatStyle.replace("{rank}", getRank(player));
                chatStyle = chatStyle.replace("{name}", player.getName());
                chatStyle = chatStyle.replace("{message}", originalMessage.replace("&", "§"));

                event.setCancelled(true);

                Logger.info("[CHAT] " + chatStyle);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(chatStyle);
                }
            }
        }
    }

    private String getRank(Player player) {
        String rank = PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%");

        if (rank.isEmpty()) {
            return "§8Unknown";
        }

        return rank;
    }
}
