package net.prismarray.openhivebedwars.util;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class FloatingArmorStand extends EntityArmorStand {

    public FloatingArmorStand(World world) {
        super(((CraftWorld) world).getHandle());
        this.setGravity(true);

        this.noclip = true;
        this.n(true); // make the armor stand a marker
    }

    @Override
    public void g(float f, float f1) {
        if (!hasGravity()) { // hasGravity() actually means hasNoGravity(), probably a mistake in deobfuscating.
            super.g(f, f1);
        } else {
            move(motX, motY, motZ);
        }
    }


    @Override
    public void m() { // This method is called each tick. This also slowly multiplies each velocity by 0.98, so I just reset those values.
        if (!hasGravity()) {
            super.m();
        } else {
            double motX = this.motX, motY = this.motY, motZ = this.motZ;
            super.m();
            this.motX = motX;
            this.motY = motY;
            this.motZ = motZ;
        }
    }
}
