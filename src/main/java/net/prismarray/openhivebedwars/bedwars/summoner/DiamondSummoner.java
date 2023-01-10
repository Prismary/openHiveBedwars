package net.prismarray.openhivebedwars.bedwars.summoner;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

public class DiamondSummoner extends SingleItemSummoner {

    public DiamondSummoner(Location location) {
        super(location, ChatColor.AQUA);
    }

    @Override
    protected void summonSetup() {
        getSummons().add(new ItemSummon( // Diamond summon
                this,
                Material.DIAMOND,
                EnumParticle.SPELL_MOB_AMBIENT,
                30
        ));
    }

    @Override
    protected void updateTitle() {
        setTitle("§bDiamond Summoner");
    }
}
