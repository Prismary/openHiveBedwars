package net.prismarray.openhivebedwars.teamChest;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TeamChestManager {

    public static final TeamChestManager instance = new TeamChestManager();
    private final Map<TeamColor, Inventory> teamInventories;

    private TeamChestManager() {

        this.teamInventories = new HashMap<>();
        resetAllChests();
    }

    public void resetAllChests() {
        for (TeamColor teamColor : TeamColor.values()) {
            this.teamInventories.put(teamColor, Bukkit.createInventory(null, 3*9, teamColor.chatColor + "Team Chest"));
        }
    }

    public void openTeamChest(Player player, TeamColor teamColor) {

        if (Objects.isNull(teamColor) || Objects.isNull(this.teamInventories.get(teamColor))) {
            return;
        }

        player.openInventory(this.teamInventories.get(teamColor));
    }

    public void openTeamChest(Player player) {
        openTeamChest(player, Game.getTeamHandler().getPlayerTeamColor(player));
    }
}
