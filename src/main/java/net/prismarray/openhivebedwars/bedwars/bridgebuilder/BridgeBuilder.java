package net.prismarray.openhivebedwars.bedwars.bridgebuilder;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.FloatingArmorStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.AbstractProjectile;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
    private final byte blockTypeData;
    private final Location spawnLocation;
    private final Vector direction;
    private final Vector strictDirection;
    private final Player owner;

    private int remainingBlocks;
    private Location lastPlacedLocation;
    private FloatingArmorStand entity;


    public BridgeBuilder(Material blockType, int remainingBlocks, Location spawnLocation, Player owner) {
        this(blockType, remainingBlocks, spawnLocation, owner, (byte) 0);
    }

    public BridgeBuilder(Material blockType, int remainingBlocks, Location spawnLocation, Player owner, byte blockTypeData) {
        this(blockType, remainingBlocks, spawnLocation, owner, blockTypeData, OpenHiveBedwars.getBWConfig().getBridgeBuilderMovementSpeed());
    }

    public BridgeBuilder(Material blockType, int remainingBlocks, Location spawnLocation, Player owner, byte blockTypeData, double speed) {

        this.speed = speed;
        this.blockType = blockType;
        this.blockTypeData = blockTypeData;
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

        entity.setEquipment(4, CraftItemStack.asNMSCopy(new BridgeBuilderItem(blockType, 0, blockTypeData)));
        entity.setLocation(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), spawnLocation.getYaw(), spawnLocation.getPitch());

        ((CraftWorld) spawnLocation.getWorld()).getHandle().addEntity(entity);
        BridgeBuilderManager.registerBridgeBuilder(this);

        entity.getBukkitEntity().setVelocity(direction.clone().multiply(speed / 20));
    }


    public void destroy() {

        //TODO: add particle effect (and possibly sound effect) on destruction

        boolean headCollidesWithEntity = headCollidesWithEntity();
        ((CraftWorld) spawnLocation.getWorld()).getHandle().removeEntity(entity);
        entity = null;

        if (remainingBlocks <= 0) {
            Broadcast.toPlayer(owner, "I'm done with this shit"); // TODO: display correct message
            return;
        }

        BridgeBuilderItem bbItem = new BridgeBuilderItem(blockType, remainingBlocks, blockTypeData);
        Map<Integer, ItemStack> notAdded = owner.getInventory().addItem(bbItem);

        Location location = owner.getLocation();
        notAdded.values().forEach(i -> location.getWorld().dropItemNaturally(location, i));

        if (headCollidesWithEntity) {
            Broadcast.toPlayer(owner, "I'm silly and got stuck on a fucking entity. Here's the rest of my blocks."); // TODO: display correct message

        } else {
            Broadcast.toPlayer(owner, "I'm silly and got stuck. Here's the rest of my blocks."); // TODO: display correct message
        }
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

        if (headCollidesWithEntity() || headCollidesWithExistingBlock()) {
            destroy();
            return;
        }

        if (!isReplaceable(block)) {
            if (collidesWithExistingBlock()) {
                destroy();
            }
            return;
        }

        block.setType(blockType);
        block.setData(blockTypeData); // ToDo: add config option to override wool and stained clay color based on the owner's team color
        block.setMetadata("placedBy", new FixedMetadataValue(OpenHiveBedwars.getInstance(), owner.getUniqueId()));
        remainingBlocks--;
        lastPlacedLocation = block.getLocation();

        // TODO: add particle effect (and possibly sound effect) on placement
    }

    public Location getLocation() {
        return Objects.isNull(entity) ? null : entity.getBukkitEntity().getLocation();
    }

    public Block getBlock() {

        Location location = getLocation().subtract(direction.clone().multiply(0.5).setY(-1));

        if (OpenHiveBedwars.getBWConfig().bridgeBuilderUseOptimizedPlacement()) {
            return projectToOptimizedPath(location).getBlock();
        } else {
            return location.getBlock();
        }
    }

    public Block getHeadBlock() {
        return getLocation().add(0, 2, 0).getBlock();
    }

    public Material getBlockType() {
        return blockType;
    }
    public byte getBlockTypeData() {
        return blockTypeData;
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

    private static boolean isReplaceable(Block block) {
        return Objects.equals(block.getType(), Material.AIR)
                || OpenHiveBedwars.getBWConfig().getBridgeBuilderReplaceableBlocks().contains(block.getType());
    }

    private boolean collidesWithExistingBlock() {

        Block block = getBlock();

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

    private boolean headCollidesWithExistingBlock() {

        if (OpenHiveBedwars.getBWConfig().bridgeBuilderNoHeadCollisionsWithBlocks()) {
            return false;
        }

        Block headBlock = getHeadBlock();

        if (isReplaceable(headBlock)) {
            return false;
        }

        if (OpenHiveBedwars.getBWConfig().bridgeBuilderNoCollisionsWithPlayerPlacedBlocks() && !headBlock.hasMetadata("placedBy")) {
            return true;
        }

        return !OpenHiveBedwars.getBWConfig().bridgeBuilderNoCollisionsWithPlayerPlacedBlocks();
    }

    private boolean headCollidesWithEntity() {

        if (OpenHiveBedwars.getBWConfig().bridgeBuilderNoHeadCollisionsWithEntities()) {
            return false;
        }

        Location blockCenterLocation = getHeadBlock().getLocation().add(0.5, 0.5, 0.5);
        long numberOfEntities = blockCenterLocation.getWorld().getNearbyEntities(blockCenterLocation, 0.5, 0.5,0.5).stream()
                .filter(this::isCollidingEntity)
                .count();

        return numberOfEntities > 0;
    }

    private boolean isCollidingEntity(Entity entity) {

        if (OpenHiveBedwars.getBWConfig().getBridgeBuilderNonCollidingEntityTypes().contains(entity.getType())) {
            return false;
        }

        return !(entity instanceof Projectile && ((AbstractProjectile) entity).getHandle().inBlock());
    }
}
