package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.apache.commons.text.StringSubstitutor;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InventoryGUIContext {

    private final Player openingPlayer;
    private final Team openingPlayerTeam;

    private final InventoryGUIBase containingGUI;
    private final Integer slot;

    private final Map<String, String> placeholders;

    private final Map<String, String> additionalContext;


    public InventoryGUIContext(@Nonnull Player openingPlayer) {
        this(openingPlayer, Game.getTeamHandler().getPlayerTeam(openingPlayer), null, null, null);
    }

    public InventoryGUIContext(@Nonnull Player openingPlayer, @Nonnull Team openingPlayerTeam) {
        this(openingPlayer, openingPlayerTeam, null, null, null);
    }

    public InventoryGUIContext(
            @Nullable Player openingPlayer,
            @Nullable Team openingPlayerTeam,
            @Nullable InventoryGUIBase containingGUI,
            @Nullable Integer slot
    ) {
        this(openingPlayer, openingPlayerTeam, containingGUI, slot, null);
    }

    public InventoryGUIContext(
            @Nullable Player openingPlayer,
            @Nullable Team openingPlayerTeam,
            @Nullable InventoryGUIBase containingGUI,
            @Nullable Integer slot,
            @Nullable Map<String, String> additionalContext
    ) {

        this.openingPlayer = openingPlayer;
        this.openingPlayerTeam = Objects.nonNull(openingPlayerTeam) ? openingPlayerTeam :
                (Objects.isNull(openingPlayer) ? null : Game.getTeamHandler().getPlayerTeam(openingPlayer));

        this.containingGUI = containingGUI;
        this.slot = slot;

        this.placeholders = generatePlaceholders();

        if (Objects.isNull(additionalContext)) {
            this.additionalContext = new HashMap<>();
        } else {
            this.additionalContext = additionalContext;
        }
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

    public String getAdditionalContext(String key, String defaultValue) {
        return this.additionalContext.getOrDefault(key, defaultValue);
    }
}
