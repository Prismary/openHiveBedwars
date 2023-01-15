package net.prismarray.openhivebedwars.bedwars.scoreboard;

import net.prismarray.openhivebedwars.util.ScoreboardSign;
import org.bukkit.entity.Player;

public abstract class PlayerScoreboard extends ScoreboardSign {

    Player player;

    public PlayerScoreboard(Player player, String header) {
        super(player, header);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void update();
}
