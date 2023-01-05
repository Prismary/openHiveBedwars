package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.util.Format;
import net.prismarray.openhivebedwars.util.Title;
import org.bukkit.entity.Player;

public class RespawnTimer extends Countdown {

    private final Player player;

    public RespawnTimer(Player player) {
        super(6, 0, 1);

        this.player = player;
    }

    @Override
    public void onDecrement() {
        Title.send(player, "", Format.getRoundNumeral(getCount()) + " §7seconds until respawn...", 0, 30, 0);
    }

    @Override
    public void onCompletion() {
        player.sendMessage("§1Respawned!");
        Game.spawnPlayer(player);
    }
}
