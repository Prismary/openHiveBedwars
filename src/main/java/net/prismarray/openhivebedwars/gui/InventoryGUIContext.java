package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.apache.commons.text.StringSubstitutor;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InventoryGUIContext {

    private final Player openingPlayer;
    private final Team openingPlayerTeam;

    private final InventoryGUIBase containingGUI;
    private final Integer slot;

    private final Map<String, String> placeholders;


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

        this.placeholders = generatePlaceholders();
    }

    private Map<String, String> generatePlaceholders() {

        Map<String, String> result = new HashMap<>();

        result.put("team_chat_color", openingPlayerTeam.getColor().chatColor.toString());
        result.put("team_color_chat_name", openingPlayerTeam.getColor().chatName);
        result.put("team_color_dye_name", openingPlayerTeam.getColor().woolColor.toString());

        return result;
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

    public String parseStringPlaceholders(String input) {

        if (Objects.isNull(input)) {
            return null;
        }

        StringSubstitutor sub = new StringSubstitutor(placeholders, "%(", ")");
        try {
            return sub.replace(input);
        } catch (Exception ignored) {
            return input;
        }
    }
}
