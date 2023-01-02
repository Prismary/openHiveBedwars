package net.prismarray.openhivebedwars.bedwars.summoner;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.prismarray.openhivebedwars.bedwars.Game;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

public class EmeraldSummoner extends SingleItemSummoner {

    public EmeraldSummoner(Game game, Location location) {
        super(game, location, ChatColor.GREEN);
    }

    @Override
    protected void summonSetup() {
        getSummons().add(new ItemSummon( // Emerald summon
                this,
                Material.EMERALD,
                EnumParticle.VILLAGER_HAPPY,
                10,
                3
        ));

        getSummons().get(0).enable();

        getSummons().get(0).start(); // todo start on ingame status
    }

    @Override
    protected void updateTitle() {
        setTitle("§aEmerald Summoner");
    }
}
