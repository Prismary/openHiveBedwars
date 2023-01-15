package net.prismarray.openhivebedwars.bedwars.scoreboard;

import org.bukkit.entity.Player;

public class IngameScoreboard extends PlayerScoreboard {

    public IngameScoreboard(Player player) {
        super(player, "Ingame Scoreboard Test");
    }

    @Override
    public void update() {
        setLine(0, "line 6 test");
        setLine(1, "-------");
        setLine(2, "sus");
    }
}
