package net.prismarray.openhivebedwars.util;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class PlayerMetadataValue implements MetadataValue {

    private Player value;

    public PlayerMetadataValue(Player value) {
        this.value = value;
    }

    @Override
    public Player value() {
        return this.value;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public float asFloat() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public long asLong() {
        return 0;
    }

    @Override
    public short asShort() {
        return 0;
    }

    @Override
    public byte asByte() {
        return 0;
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @Override
    public String asString() {
        return this.value.getDisplayName();
    }

    @Override
    public Plugin getOwningPlugin() {
        return OpenHiveBedwars.getInstance();
    }

    @Override
    public void invalidate() {
        this.value = null;
    }
}
