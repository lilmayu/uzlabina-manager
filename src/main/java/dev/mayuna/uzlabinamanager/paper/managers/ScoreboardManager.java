package dev.mayuna.uzlabinamanager.paper.managers;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import dev.mayuna.uzlabinamanager.paper.PaperMain;
import dev.mayuna.uzlabinamanager.paper.objects.PaperConfig;
import dev.mayuna.uzlabinamanager.paper.objects.config.ConfigScoreboard;
import fr.mrmicky.fastboard.FastBoard;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ScoreboardManager {

    private static final @Getter Map<UUID, FastBoard> boards = new HashMap<>();

    public static void init() {
        if (isScoreboardEnabled()) {
            Bukkit.getScheduler().runTaskTimer(PaperMain.getInstance(), ScoreboardManager::updateAll, 20, 20);
        }
    }

    public static void setupPlayer(Player player) {
        if (!isScoreboardEnabled()) {
            return;
        }

        synchronized (boards) {
            FastBoard board = new FastBoard(player);
            update(player.getUniqueId(), board);
            boards.put(player.getUniqueId(), board);
        }
    }

    public static void removePlayer(Player player) {
        if (!isScoreboardEnabled()) {
            return;
        }

        synchronized (boards) {
            boards.remove(player.getUniqueId());
        }
    }

    private static void updateAll() {
        synchronized (boards) {


            for (Map.Entry<UUID, FastBoard> entry : boards.entrySet()) {
                update(entry.getKey(), entry.getValue());
            }
        }
    }

    private static synchronized void update(UUID playerUuid, FastBoard board) {
        ConfigScoreboard configScoreboard = PaperConfig.getScoreboard();
        Player player = Bukkit.getPlayer(playerUuid);

        if (player == null) {
            return;
        }

        if (configScoreboard == null) {
            return;
        }

        board.updateTitle(configScoreboard.getTitle());
        board.updateLines(configScoreboard.getLines().stream().map(line -> IridiumColorAPI.process(PlaceholderAPI.setPlaceholders(player, line))).collect(Collectors.toList()));
    }

    public static boolean isScoreboardEnabled() {
        return PaperConfig.getScoreboard() != null && PaperConfig.getScoreboard().isEnabled();
    }
}
