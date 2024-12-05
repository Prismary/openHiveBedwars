package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class InventoryGUIContext {

    private final Player openingPlayer;
    private final Team openingPlayerTeam;

    private final InventoryGUIBase containingGUI;
    private final Integer slot;


    public InventoryGUIContext(@Nonnull Player openingPlayer) {
        this(openingPlayer, Game.getTeamHandler().getPlayerTeam(openingPlayer), null, null);
    }


    public InventoryGUIContext(@Nonnull Player openingPlayer, @Nonnull Team openingPlayerTeam) {
        this(openingPlayer, openingPlayerTeam, null, null);
    }

    public InventoryGUIContext(Player openingPlayer, Team openingPlayerTeam, InventoryGUIBase containingGUI, Integer slot) {

        this.openingPlayer = openingPlayer;
        this.openingPlayerTeam = openingPlayerTeam;

        this.containingGUI = containingGUI;
        this.slot = slot;
    }

    public Player getOpeningPlayer() {
        return openingPlayer;
    }

    public Team getOpeningPlayerTeam() {
        return openingPlayerTeam;
    }

    public InventoryGUIBase getContainingGUI() {
        return containingGUI;
    }

    public Integer getSlot() {
        return slot;
    }
}
