package net.prismarray.openhivebedwars.bedwars.summoner;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.prismarray.openhivebedwars.util.Format;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.Location;
import org.bukkit.Material;

public class TeamSummoner extends Summoner {

    private final TeamColor teamColor;

    private final int[] summonLevels;

    public TeamSummoner(Location location, TeamColor teamColor) {
        super(location);

        this.teamColor = teamColor;

        summonLevels = new int[]{1, 0, 0};

        updateTitle();
        updateSubtitle();
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public int[] getSummonLevels() {
        return summonLevels;
    }

    @Override
    public void enable() {
        getSummons().get(0).enable();
    }

    @Override
    public void disable() {
        getSummons().forEach(ItemSummon::disable);
    }

    public void upgrade(int index) {
        if (summonLevels[index] >= 3) {
            return;
        }

        summonLevels[index]++;

        if (summonLevels[index] != 1) {
            getSummons().get(index).changeDelay(-2);
        } else {
            getSummons().get(index).enable();
        }

        updateSubtitle();
    }

    @Override
    protected void summonSetup() {
        getSummons().add(new ItemSummon( // Iron summon
                this,
                Material.IRON_INGOT,
                EnumParticle.SMOKE_NORMAL,
                5
        ));

        getSummons().add(new ItemSummon( // Gold summon
                this,
                Material.GOLD_INGOT,
                EnumParticle.FLAME,
                5
        ));

        getSummons().add(new ItemSummon( // Diamond summon
                this,
                Material.DIAMOND,
                EnumParticle.SPELL_MOB_AMBIENT,
                5
        ));
    }

    protected void updateTitle() {
        setTitle(String.format(
                "%s%s's Summoner",
                teamColor.chatColor.toString(),
                teamColor.chatName
        ));
    }

    protected void updateSubtitle() {
        // Prepare subtitle
        String newSubtitle = String.format(
                "§f%c §lIron",
                Format.getRoundNumeral(summonLevels[0])
        );

        if (summonLevels[1] != 0) {
            newSubtitle = newSubtitle + String.format(
                    " §e%c §lGold",
                    Format.getRoundNumeral(summonLevels[1])
            );
        }

        if (summonLevels[2] != 0) {
            newSubtitle = newSubtitle + String.format(
                    " §b%c §lDiamond",
                    Format.getRoundNumeral(summonLevels[2])
            );
        }

        // Set subtitle
        setSubtitle(newSubtitle);
    }
}
