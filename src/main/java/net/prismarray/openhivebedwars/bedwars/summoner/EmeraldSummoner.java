package net.prismarray.openhivebedwars.bedwars.summoner;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

public class EmeraldSummoner extends SingleItemSummoner {

    public EmeraldSummoner(Location location) {
        super(location, ChatColor.GREEN);
    }

    @Override
    protected void summonSetup() {
        getSummons().add(new ItemSummon( // Emerald summon
                this,
                Material.EMERALD,
                EnumParticle.VILLAGER_HAPPY,
                10
        ));

        getSummons().get(0).enable(); // todo enable 30s in
    }

    @Override
    protected void updateTitle() {
        setTitle("§aEmerald Summoner");
    }
}
