package net.prismarray.openhivebedwars.bedwars.bridgebuilder;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.FloatingArmorStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.Objects;

public class BridgeBuilder {

    /**
     * Speed of this BridgeBuilder in blocks per second
     */
    private final double speed;
    private final Material blockType;
    private final Location spawnLocation;
    private final Vector direction;
    private final Vector strictDirection;
    private final Player owner;

    private int remainingBlocks;
    private Location lastPlacedLocation;
    private FloatingArmorStand entity;


    public BridgeBuilder(Material blockType, int remainingBlocks, Location spawnLocation, Player owner) {
        this(blockType, remainingBlocks, spawnLocation, owner, OpenHiveBedwars.getBWConfig().getBridgeBuilderMovementSpeed());
    }

    public BridgeBuilder(Material blockType, int remainingBlocks, Location spawnLocation, Player owner, double speed) {

        this.speed = speed;
        this.blockType = blockType;
        this.spawnLocation = spawnLocation.clone();
        this.spawnLocation.setPitch(0);
        this.direction = spawnLocation.getDirection().setY(0.0).normalize();
        this.strictDirection = calculateStrictDirection(direction);
        this.owner = owner;

        this.remainingBlocks = remainingBlocks;
        this.lastPlacedLocation = null;
        this.entity = null;
    }

    public void spawn() {

        entity = new FloatingArmorStand(spawnLocation.getWorld());
        lastPlacedLocation = null;
        entity.setInvisible(true);

        entity.setEquipment(4, CraftItemStack.asNMSCopy(new BridgeBuilderItem(blockType, 0)));
        entity.setLocation(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), spawnLocation.getYaw(), spawnLocation.getPitch());

        ((CraftWorld) spawnLocation.getWorld()).getHandle().addEntity(entity);
        BridgeBuilderManager.registerBridgeBuilder(this);

        entity.getBukkitEntity().setVelocity(direction.clone().multiply(speed / 20));
    }


    public void destroy() {

        //TODO: add particle effect (and possibly sound effect) on destruction

        ((CraftWorld) spawnLocation.getWorld()).getHandle().removeEntity(entity);
        entity = null;

        if (remainingBlocks <= 0) {
            Broadcast.toPlayer(owner, "I'm done with this shit"); // TODO: display correct message
            return;
        }

        BridgeBuilderItem bbItem = new BridgeBuilderItem(blockType, remainingBlocks);
        Map<Integer, ItemStack> notAdded = owner.getInventory().addItem(bbItem);

        Location location = owner.getLocation();
        notAdded.values().forEach(i -> location.getWorld().dropItemNaturally(location, i));

        Broadcast.toPlayer(owner, "I'm silly and got stuck. Here's the rest of my blocks."); // TODO: display correct messagea
    }


    public void placeBlock() {

        if (Objects.isNull(entity)) {
            return;
        }

        if (remainingBlocks <= 0) {
            destroy();
            return;
        }

        Block block = getBlock();

        if (!isReplaceable(block)) {
            if (collidesWithExistingBlock(block)) {
                destroy();
            }
            return;
        }

        block.setType(blockType);
        block.setMetadata("placedBy", new FixedMetadataValue(OpenHiveBedwars.getInstance(), owner.getUniqueId()));
        remainingBlocks--;
        lastPlacedLocation = block.getLocation();

        // TODO: add particle effect (and possibly sound effect) on placement
    }

    public Location getLocation() {
        return Objects.isNull(entity) ? null : entity.getBukkitEntity().getLocation();
    }

    public Block getBlock() {

        Location location = getLocation();
        location.setY(location.getY() + 1);
        location.subtract(direction.clone().multiply(0.5));

        if (OpenHiveBedwars.getBWConfig().bridgeBuilderUseOptimizedPlacement()) {
            return projectToOptimizedPath(location).getBlock();
        } else {
            return location.getBlock();
        }
    }

    public Material getBlockType() {
        return blockType;
    }

    public int getRemainingBlocks() {
        return remainingBlocks;
    }

    public FloatingArmorStand getEntity() {
        return entity;
    }

    private Location projectToOptimizedPath(Location location) {

        if (Objects.isNull(lastPlacedLocation)) {
            return location.clone();
        }

        if (strictDirection.getZ() == 0 && location.getBlockX() == lastPlacedLocation.getBlockX()) {
            return lastPlacedLocation.clone();
        }

        if (strictDirection.getX() == 0 && location.getBlockZ() == lastPlacedLocation.getBlockZ()) {
            return lastPlacedLocation.clone();
        }

        if (Math.abs(strictDirection.getX()) == Math.abs(strictDirection.getZ())
                && !Objects.equals(location, lastPlacedLocation.clone().add(strictDirection))) {
            return lastPlacedLocation.clone();
        }

        return location.clone();
    }

    private static Vector calculateStrictDirection(Vector direction) {

        Vector temp = direction.clone().normalize();

        if (temp.getX() > Math.abs(direction.getZ())) {
            return new Vector(1, 0, 0);

        } else if (-temp.getX() > Math.abs(direction.getZ())) {
            return new Vector(-1, 0, 0);

        } else if (temp.getZ() > Math.abs(direction.getX())) {
            return new Vector(0, 0, 1);

        } else if (-temp.getZ() > Math.abs(direction.getX())) {
            return new Vector(0, 0, -1);
        }

        // return the normalized input vector, if the x and z component are equal in their absolute value
        return temp;
    }

    private boolean isReplaceable(Block block) {
        return Objects.equals(block.getType(), Material.AIR)
                || OpenHiveBedwars.getBWConfig().getBridgeBuilderReplaceableBlocks().contains(block.getType());
    }

    private boolean collidesWithExistingBlock(Block block) {

        if (isReplaceable(block)) {
            return false;
        }

        if (!Objects.nonNull(lastPlacedLocation)) {
            return false;
        }

        if (OpenHiveBedwars.getBWConfig().bridgeBuilderNoCollisionsWithPlayerPlacedBlocks() && !block.hasMetadata("placedBy")) {
            return true;
        }

        return !OpenHiveBedwars.getBWConfig().bridgeBuilderNoCollisionsWithPlayerPlacedBlocks();
    }
}
