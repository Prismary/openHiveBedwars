package net.prismarray.openhivebedwars.bedwars.bridgebuilder;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.Bukkit;

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


    public static void tickUpdate() {

        List<BridgeBuilder> toRemove = instance.bridgeBuilders.stream()
                .filter(b -> Objects.isNull(b.getEntity()))
                .collect(Collectors.toList());
        instance.bridgeBuilders.removeAll(toRemove);

        instance.bridgeBuilders.forEach(BridgeBuilder::placeBlock);
    }
}