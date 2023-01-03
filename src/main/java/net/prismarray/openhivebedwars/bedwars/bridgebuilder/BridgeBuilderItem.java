package net.prismarray.openhivebedwars.bedwars.bridgebuilder;

import net.prismarray.openhivebedwars.gui.InventoryGUIPlayerHead;

public class BridgeBuilderItem extends InventoryGUIPlayerHead {

    public BridgeBuilderItem(int stock) {
        super("steve", 1, String.format("Bridge Builder [%d]", stock));
    }
}
