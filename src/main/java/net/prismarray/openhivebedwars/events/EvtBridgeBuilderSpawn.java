package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilder;
import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class EvtBridgeBuilderSpawn extends EventBase {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        if (!Objects.equals(Game.getStatus(), Status.INGAME)) {
            return;
        }

        BridgeBuilderItem bbItem = BridgeBuilderItem.fromItemStack(e.getItemInHand());
        if (Objects.isNull(bbItem)) {
            return;
        }

        e.setBuild(false);
        e.setCancelled(true);

        Player player = e.getPlayer();

        Location playerLocation = player.getLocation();
        Location blockLocation = e.getBlockPlaced().getLocation();

        Location spawnLocation = new Location(
                playerLocation.getWorld(),
                blockLocation.getBlockX() + 0.5,
                blockLocation.getBlockY() - 1.25, // -1.435 for perfect leveling on the ground
                blockLocation.getBlockZ() + 0.5,
                playerLocation.getYaw(),
                playerLocation.getPitch()
        );

        BridgeBuilder bridgeBuilder = new BridgeBuilder(bbItem.getBlockType(), bbItem.getRemainingBlocks(), spawnLocation, player);
        bridgeBuilder.spawn();

        ItemStack placedBuilder = e.getItemInHand();

        if (placedBuilder.getAmount() > 1) {
            placedBuilder.setAmount(placedBuilder.getAmount() - 1);

        } else {
            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
        }
    }
}
