package net.prismarray.openhivebedwars.events;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;
import java.util.stream.Collectors;

public class EvtBlockBreak extends EventBase {

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        switch (Game.getStatus()) {
            case WARMUP:
            case INGAME:
                if (event.getBlock().hasMetadata("placedBy") ||
                        OpenHiveBedwars.getBWConfig().getBreakables().contains(event.getBlock().getType())) {
                    break;
                } // else fall through to default
            default:
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockExplode(BlockExplodeEvent event) {

        if (Game.getStatus() != Status.INGAME) {
            event.setCancelled(true);
            return;
        }

        List<Block> toCancel = event.blockList().stream()
                .filter(block -> !block.hasMetadata("placedBy") && !OpenHiveBedwars.getBWConfig().getBreakables().contains(block.getType()))
                .collect(Collectors.toList());

        event.blockList().removeAll(toCancel);
    }

    @EventHandler
    public void blockEntityExplode(EntityExplodeEvent event) {

        if (Game.getStatus() != Status.INGAME) {
            event.setCancelled(true);
            return;
        }

        List<Block> toCancel = event.blockList().stream()
                .filter(block -> !block.hasMetadata("placedBy") && !OpenHiveBedwars.getBWConfig().getBreakables().contains(block.getType()))
                .collect(Collectors.toList());

        event.blockList().removeAll(toCancel);

        if (event.getEntity().hasMetadata("placingPlayer")) {

            Player p = (Player) event.getEntity().getMetadata("placingPlayer").get(0).value();
            toCancel.stream()
                    .filter(block -> block.getType() == Material.BED_BLOCK)
                    .forEach(
                            block -> ((CraftPlayer) p).getHandle().playerInteractManager.breakBlock(
                                    new BlockPosition(block.getX(), block.getY(), block.getZ())
                            )
                    );
        }
    }
}
