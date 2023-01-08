package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools.*;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class ItemsWeaponsGUI extends FramedFullRowsGUI {

    public ItemsWeaponsGUI(TeamColor teamColor) {
        super("Weapons & Tools", 6, DyeColor.BLUE, true, () -> new ItemsRootGUI(teamColor));

        new StoneSword(this, 10);
        new StonePickaxe(this, 19);
        new StoneAxe(this, 28);
        // todo stone shovel

        new IronSword(this, 12);
        new IronPickaxe(this, 21);
        new IronAxe(this, 30);
        // todo iron shovel

        new DiamondSword(this, 14);
        new DiamondPickaxe(this, 23);
        new DiamondAxe(this, 32);
        // todo diamond shovel

        // todo compass
        // todo shears
        new Bow(this, 34);
        new Arrow(this, 43);

        lock();
    }
}
