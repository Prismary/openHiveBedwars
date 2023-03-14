package net.prismarray.openhivebedwars.bedwars.scoreboard;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.ScoreboardSign;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ScoreboardManager {

    private static final ScoreboardManager instance = new ScoreboardManager();
    public ScoreboardManager getInstance() {
        return instance;
    }


    private ArrayList<PlayerScoreboard> scoreboards;
    private boolean ingameScoreboardsActive;

    private ScoreboardManager() {
        scoreboards = new ArrayList<>();
        ingameScoreboardsActive = false;
    }

    public static void updateAll() {
        instance.scoreboards.forEach(PlayerScoreboard::update);
    }
    public static void updateAllTitles() {
        instance.scoreboards.forEach(PlayerScoreboard::updateTitle);
    }

    public static void setScoreboard(Player player, boolean enable) {
        PlayerScoreboard scoreboard;

        switch (Game.getStatus()) {
            case WARMUP:
            case INGAME:
            case CONCLUDED:
                scoreboard = new IngameScoreboard(player);
                break;
            case RESULTS:
                scoreboard = new LobbyScoreboard(player); // TODO change to proper type
                break;
            default:
                scoreboard = new LobbyScoreboard(player);
                break;
        }

        instance.scoreboards.add(scoreboard);

        if (enable) {
            scoreboard.create();
            scoreboard.update();
        }
    }

    public static void removeScoreboard(Player player) {
        PlayerScoreboard toBeRemoved = null;
        for (PlayerScoreboard scoreboard : instance.scoreboards) {
            if (scoreboard.getPlayer() == player) {
                toBeRemoved = scoreboard;
            }
        }

        if (toBeRemoved == null) {
            return;
        }

        toBeRemoved.destroy();
        instance.scoreboards.remove(toBeRemoved);
    }

    public static void resetScoreboards() {
        hideScoreboards();
        instance.scoreboards = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> {
            setScoreboard(player, false);
        });
        showScoreboards();
    }

    public static void showScoreboards() {
        instance.scoreboards.forEach(scoreboard -> {
            scoreboard.create();
            scoreboard.update();
        });
    }

    public static void hideScoreboards() {
        instance.scoreboards.forEach(ScoreboardSign::destroy);
    }

    public static void enableIngameScoreboards() {
        instance.ingameScoreboardsActive = true;
        updateAllTitles();
        updateAll();
    }

    public static boolean ingameScoreboardsEnabled() {
        return instance.ingameScoreboardsActive;
    }
}
