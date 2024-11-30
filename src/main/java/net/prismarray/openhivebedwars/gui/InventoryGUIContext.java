package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class InventoryGUIContext {

    private final Player openingPlayer;
    private final Team openingPlayerTeam;


    public InventoryGUIContext(@Nonnull Player openingPlayer) {
        this(openingPlayer, Game.getTeamHandler().getPlayerTeam(openingPlayer));
    }

    public InventoryGUIContext(Player openingPlayer, Team openingPlayerTeam) {

        this.openingPlayer = openingPlayer;
        this.openingPlayerTeam = openingPlayerTeam;
    }

    public Player getOpeningPlayer() {
        return openingPlayer;
    }

    public Team getOpeningPlayerTeam() {
        return openingPlayerTeam;
    }
}
