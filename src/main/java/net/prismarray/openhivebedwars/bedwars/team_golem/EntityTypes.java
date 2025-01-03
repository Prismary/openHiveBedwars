package net.prismarray.openhivebedwars.bedwars.team_golem;

import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static net.prismarray.openhivebedwars.bedwars.team_golem.TeamGolem.getPrivateField;

public enum EntityTypes {

    TEAM_GOLEM("Team Golem", 99, TeamGolem.class);

    private final String name;
    private final int id;
    private final Class<? extends Entity> customClass;

    private EntityTypes(String name, int id, Class<? extends Entity> custom) {

        this.name = name;
        this.id = id;
        this.customClass = custom;

        ((Map<?, ?>)getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).remove(name);
        ((Map<?, ?>)getPrivateField("e", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).remove(id);

        try {
            Method method = net.minecraft.server.v1_8_R3.EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, Integer.TYPE);
            method.setAccessible(true);
            method.invoke(null, custom, name, id);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
            throw new RuntimeException(String.format("Could not register custom Entity '%s'", name));
        }

        //addToMaps(custom, name, id);
    }

    public Entity spawn(Location location) {
        Entity entity = this.spawn(location.getWorld());
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        return entity;
    }

    public Entity spawn(World world) {
        return net.minecraft.server.v1_8_R3.EntityTypes.createEntityByName(this.name, ((CraftWorld) world).getHandle());
    }
/*
    public static void spawnEntity(Entity entity, Location loc)
    {
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
        entity.setInvisible(false);
    }

    private static void addToMaps(Class<? extends Entity> clazz, String name, int id)
    {
        ((Map)getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
        ((Map)getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        ((Map)getPrivateField("e", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
        ((Map)getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
        ((Map)getPrivateField("g", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, Integer.valueOf(id));
    }
 */

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Class<? extends Entity> getCustomClass() {
        return customClass;
    }
}
