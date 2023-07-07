package net.prismarray.openhivebedwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;

public class EvtFormAndFade extends EventBase {

    @EventHandler
    public void blockForm(BlockFormEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void blockFade(BlockFadeEvent event) {
        event.setCancelled(true);
    }
}
