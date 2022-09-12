package net.prismarray.openhivebedwars.bedwars;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class RespawnTimer extends Countdown {

    Game game;
    Player player;

    public RespawnTimer(Game game, Player player) {
        super(game.plugin, 6, 0, 1);

        this.game = game;
        this.player = player;
    }

    @Override
    public void onDecrement() {
        player.sendMessage("§9Respawn: " + String.valueOf(getCount()));
    }

    @Override
    public void onCompletion() {
        player.sendMessage("§1Respawned!");
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(game.mapConfig.getTeamSpawn(game.teamHandler.getPlayerTeam(player).getColor()));
    }
}
