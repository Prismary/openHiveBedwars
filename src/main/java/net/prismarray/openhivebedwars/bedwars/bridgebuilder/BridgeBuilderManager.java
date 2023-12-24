package net.prismarray.openhivebedwars.bedwars.bridgebuilder;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.*;
import java.util.stream.Collectors;

public class BridgeBuilderManager {

    private static final BridgeBuilderManager instance = new BridgeBuilderManager();

    private final List<BridgeBuilder> bridgeBuilders = new ArrayList<>();

    private BridgeBuilderManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                OpenHiveBedwars.getInstance(), BridgeBuilderManager::tickUpdate, 1L, 0L
        );
    }

    public static BridgeBuilderManager getInstance() {
        return instance;
    }


    public static void registerBridgeBuilder(BridgeBuilder builder) {
        instance.bridgeBuilders.add(builder);
    }


    public static boolean isRegisteredBridgeBuilder(Entity entity) {
        return instance.bridgeBuilders.stream().anyMatch(b -> b.getEntity().getId() == entity.getEntityId());
    }


    public static BridgeBuilder getRegisteredBridgeBuilder(Entity entity) {
        return instance.bridgeBuilders.stream()
                .filter(b -> b.getEntity().getId() == entity.getEntityId())
                .findFirst()
                .orElse(null);
    }


    public static void tickUpdate() {

        List<BridgeBuilder> toRemove = instance.bridgeBuilders.stream()
                .filter(b -> Objects.isNull(b.getEntity()))
                .collect(Collectors.toList());
        instance.bridgeBuilders.removeAll(toRemove);

        instance.bridgeBuilders.forEach(BridgeBuilder::placeBlock);
    }
}
