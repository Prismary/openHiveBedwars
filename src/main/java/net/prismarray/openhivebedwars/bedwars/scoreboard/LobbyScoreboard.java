package net.prismarray.openhivebedwars.bedwars.scoreboard;

import org.bukkit.entity.Player;

public class LobbyScoreboard extends PlayerScoreboard {

    public LobbyScoreboard(Player player) {
        super(player, "§6§lHive§e§lMC");
    }

    @Override
    public void update() {
        setLine(0, " ");
        setLine(1, "§a§lTokens");
        setLine(2, "§7?");
        setLine(3, "  ");
        setLine(4, "§e§lYour Stats");
        setLine(5, "§3Points: §b?");
        setLine(6, "§3Games Played: §b?");
        setLine(7, "§3Victories: §b?");
        setLine(8, "§3Win Streak: §b?");
        setLine(9, "§3Kills: §b?");
        setLine(10, "§3Deaths: §b?");
        setLine(11, "§3Beds Destroyed: §b?");
        setLine(12, "   ");
        setLine(13, "§8§l§m----------");
        setLine(14, "§6play.§eHiveMC§6.com");
    }

    @Override
    public void updateTitle() {
        setObjectiveName("§6§lHive§e§lMC");
    }
}
