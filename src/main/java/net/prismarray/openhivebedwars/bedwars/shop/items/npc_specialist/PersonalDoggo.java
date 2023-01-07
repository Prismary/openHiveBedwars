package net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class PersonalDoggo extends PurchasableItem {

    public PersonalDoggo() {
        super(
                Material.MONSTER_EGG,
                (short) 95,
                1,
                false,
                "Personal Doggo",
                1,
                Currency.EMERALD,
                false,
                false
        );
    }
}
