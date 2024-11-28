package net.prismarray.openhivebedwars.bedwars.spectator_compass;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class SpectatorCompassInventoryGUI extends FramedFullRowsGUI {

    public static final int MAX_PAGE_SIZE = 4 * 7;

    public SpectatorCompassInventoryGUI(@Nullable List<Player> listedPlayers) {
        this(listedPlayers, null);
    }

    public SpectatorCompassInventoryGUI(@Nullable final List<Player> listedPlayers, @Nullable final Integer pageStart) {
        super(
                "Spectator Compass",
                (int) Math.ceil((double) (Objects.isNull(listedPlayers) ? 0 : listedPlayers.size()
                        - (Objects.nonNull(pageStart) ? pageStart : 0) * MAX_PAGE_SIZE) / 9) + 2,
                DyeColor.BLACK,
                true,
                (Objects.isNull(pageStart) || pageStart == 0)
                        ? null : () -> new SpectatorCompassInventoryGUI(listedPlayers, pageStart - 1),
                (
                        Objects.isNull(listedPlayers) || listedPlayers.size() <=
                                ((Objects.isNull(pageStart) ? 0 : pageStart) + 1) * MAX_PAGE_SIZE
                ) ? null : () -> new SpectatorCompassInventoryGUI(listedPlayers, pageStart + 1)
        );

        int effPageStart = 0;
        if (Objects.nonNull(pageStart)) {
            effPageStart = pageStart;
        }

        int slot = 9 * 1 + 1; // start in the second slot of the second row
        for (
                int i = effPageStart * MAX_PAGE_SIZE;
                i < (effPageStart + 1) * MAX_PAGE_SIZE && i < listedPlayers.size();
                i++
        ) {
            new PlayerTeleportButton(this, slot++, listedPlayers.get(i));
        }

        this.lock();
    }
}
