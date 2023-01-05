package net.prismarray.openhivebedwars.util;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Particles {

    public static void player(Player player, Location location, EnumParticle particleType) {
        ((CraftPlayer) player).getHandle().playerConnection
                .sendPacket(new PacketPlayOutWorldParticles(
                        particleType,
                        true,
                        (float) location.getX(),
                        (float) location.getY(),
                        (float) location.getZ(),
                        0f,
                        0f,
                        0f,
                        1f,
                        6
                ));
    }

    public static void global(Location location, EnumParticle particleType) {
        Bukkit.getOnlinePlayers().forEach(player -> player(player, location, particleType));
    }

}
