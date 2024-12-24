package net.prismarray.openhivebedwars.events;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class EvtBedBlockPlace extends EventBase {

    @EventHandler
    public void blockPlace(PlayerInteractEvent event) {

        if (Objects.isNull(event.getClickedBlock())) {
            return;
        }
        Block clickedBlock = event.getClickedBlock();

        if (!Objects.equals(event.getClickedBlock().getType(), Material.BED_BLOCK)) {
            return;
        }

        if (!Objects.equals(event.getAction(), Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (Objects.isNull(event.getPlayer())) {
            return;
        }
        Player p = event.getPlayer();

        event.setCancelled(true);

        if (Objects.isNull(p.getItemInHand()) || Objects.isNull(CraftItemStack.asNMSCopy(p.getItemInHand()))) {
            return;
        }

        if (!p.getItemInHand().getType().isBlock()) {
            return;
        }

        boolean successful = CraftItemStack.asNMSCopy(p.getItemInHand()).placeItem(
                ((CraftPlayer) p).getHandle(),
                ((CraftWorld) p.getLocation().getWorld()).getHandle(),
                new BlockPosition(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ()),
                EnumDirection.valueOf(event.getBlockFace().toString()),
                0,
                0,
                0
        );

        if (!successful) {
            return;
        }

        if (Objects.equals(p.getItemInHand().getType(), Material.TNT)) {
            // Prevent removing double the items when placing instant TNT against a bed
            return;
        }

        if (p.getItemInHand().getAmount() > 1) {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);

        } else {
            p.setItemInHand(null);
        }
    }
}
