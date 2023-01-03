package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.shop.ShopManager;
import net.prismarray.openhivebedwars.bedwars.shop.npc.Shop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EvtNPCInteraction extends EventBase {

    @EventHandler
    public void npcInteraction(PlayerInteractEntityEvent event) {
        Shop entityShop = ShopManager.getInstance().getShop(event.getRightClicked());

        if (entityShop == null) { // Return if entity shop is null
            return;
        }

        event.setCancelled(true);

        entityShop.openShop(event.getPlayer());
    }
}
