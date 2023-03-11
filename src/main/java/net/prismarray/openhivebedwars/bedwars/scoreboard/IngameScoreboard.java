package net.prismarray.openhivebedwars.bedwars.scoreboard;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.bedwars.TeamHandler;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.entity.Player;

public class IngameScoreboard extends PlayerScoreboard {

    public IngameScoreboard(Player player) {
        super(player, "§6§lBed§e§lWars");
    }

    @Override
    public void update() {
        setLine(0, " ");
        setLine(1, "§e§lTeams Left");
        setLine(2, String.format("%s", getRemainingTeams()));
        setLine(3, "  ");
        setLine(4, "§r§lYour Team");
        setLine(5, String.format("§7Color: %s", getTeam()));
        setLine(6, String.format("§7Bed: %s", getBedStatus()));
        setLine(7, "   ");
        setLine(8, "§c§lYour Stats");
        setLine(9, String.format("§7Kills: §r%s", getKills()));
        setLine(10, String.format("§7Deaths: §r%s", getDeaths()));
        setLine(11, String.format("§7Beds: §r%s", getBeds()));
        setLine(12, "    ");
        setLine(13, "§8§l§m          ");
        setLine(14, "§6play.§eHiveMC§6.com");
    }

    public String getRemainingTeams() {
        if (!ScoreboardManager.ingameScoreboardsEnabled()) {
            return "§8Waiting...";
        }

        String output = "";
        for (Team team : Game.getTeamHandler().getTeams()) {
            output += team.getColor().chatColor + "█";
        }

        return output;
    }

    public String getTeam() {
        TeamColor color = Game.getTeamHandler().getPlayerTeam(getPlayer()).getColor();
        return color.chatColor + color.chatName;
    }

    public String getBedStatus() {
        if (!ScoreboardManager.ingameScoreboardsEnabled()) {
            return "§8Waiting...";
        }

        if (Game.getTeamHandler().getPlayerTeam(player).hasBed()) {
            return "§aAlive";
        } else {
            return "§cDestroyed";
        }
    }

    public String getKills() {
        if (!ScoreboardManager.ingameScoreboardsEnabled()) {
            return "§8Waiting...";
        }

        return "0"; // TODO get stats
    }

    public String getDeaths() {
        if (!ScoreboardManager.ingameScoreboardsEnabled()) {
            return "§8Waiting...";
        }

        return "0"; // TODO get stats
    }

    public String getBeds() {
        if (!ScoreboardManager.ingameScoreboardsEnabled()) {
            return "§8Waiting...";
        }

        return "0"; // TODO get stats
    }
}
