package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist.*;
import org.bukkit.DyeColor;

public class SpecialistRootGUI extends FramedFullRowsGUI {

    public SpecialistRootGUI() {
        super("The Specialist", 4, DyeColor.ORANGE, true, null);

        new PersonalDoggo(this, 11);
        new InstantTNT(this, 12);
        new GoldenApple(this, 13);
        new Enderpearl(this, 14);
        new TeamGolem(this, 15);

        new PoisonCure(this, 20);
        new SplashHealingPotion(this, 21);
        new SplashDamagePotion(this, 23);
        new SplashPoisonPotion(this, 24);

        lock();
    }
}
