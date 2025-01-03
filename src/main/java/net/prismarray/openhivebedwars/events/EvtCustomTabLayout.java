package net.prismarray.openhivebedwars.events;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;
import java.util.List;

public class EvtCustomTabLayout extends EventBase {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        if (OpenHiveBedwars.getBWConfig().getTabMenuDisableCustomMenu()) {
            return;
        }

        Player player = event.getPlayer();
        EntityPlayer playerEntity = ((CraftPlayer) player).getHandle();

        List<String> headerLines = OpenHiveBedwars.getBWConfig().getTabManuHeaderLines();
        List<String> footerLines = OpenHiveBedwars.getBWConfig().getTabManuFooterLines();

        IChatBaseComponent headerText = IChatBaseComponent.ChatSerializer.a(
                String.format("{\"text\": \"%s\"}", String.join("\n§r", headerLines))
        );

        IChatBaseComponent footerText = IChatBaseComponent.ChatSerializer.a(
                String.format("{\"text\": \"%s\"}", String.join("\n§r", footerLines))
        );


        PacketPlayOutPlayerListHeaderFooter tabPacket = new PacketPlayOutPlayerListHeaderFooter(headerText);
        try {
            Field subtitleField = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("b");
            subtitleField.setAccessible(true);
            subtitleField.set(tabPacket, footerText);

        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            OpenHiveBedwars.getInstance().getLogger().warning(String.format(
                    "Could not set custom tab menu footer for player %s", player.getDisplayName()
            ));
        }

        playerEntity.playerConnection.sendPacket(tabPacket);
    }
}
