package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableCustomHead;
import net.prismarray.openhivebedwars.util.Currency;

public class WoodBridgeBuilder extends PurchasableCustomHead {

    public WoodBridgeBuilder() {
        super(
                "http://textures.minecraft.net/texture/2705fd94a0c431927fb4e639b0fcfb49717e412285a02b439e0112da22b2e2ec",
                1,
                false,
                "Wood Bridge Builder",
                30,
                Currency.IRON,
                false,
                false
        );
    }
}
