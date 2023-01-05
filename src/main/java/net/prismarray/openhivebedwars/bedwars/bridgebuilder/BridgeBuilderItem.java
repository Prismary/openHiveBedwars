package net.prismarray.openhivebedwars.bedwars.bridgebuilder;

import net.prismarray.openhivebedwars.gui.InventoryGUICustomHead;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BridgeBuilderItem extends InventoryGUICustomHead {

    private static final Pattern NAMING_PATTERN = Pattern.compile("(.+) Bridge Builder \\[(\\d+)]");

    private final int remainingBlocks;
    private final Material blockType;

    public BridgeBuilderItem(Material blockType, int remainingBlocks) {
        super(getURLForMaterial(blockType), 1, String.format("%s Bridge Builder [%d]", blockType.name(), remainingBlocks));
        this.remainingBlocks = remainingBlocks;
        this.blockType = blockType;
    }


    private static String getURLForMaterial(Material material) {

        switch (material) {
            case WOOL:
                return "http://textures.minecraft.net/texture/3faf4c29f1e7405f4680c5c2b03ef9384f1aecfe2986ad50138c605fefff2f15";
            case ENDER_STONE:
                return "http://textures.minecraft.net/texture/42813cf8c4fd013baf5bf55a8c93121c482da1cf6e1054c180ff3e81727b65dd";
            case WOOD:
                return "http://textures.minecraft.net/texture/993993674424e62f0a461c6268f8854541e024c7d3b416a250b2b4d11b50179d";
            case STONE: // actually andesite, but who's counting...
                return "http://textures.minecraft.net/texture/adb7bf059a62d27b1e1e2f34394f3f38ed8cda45471f6f4d5b47c3912d181135";
            case PRISMARINE:
                return "http://textures.minecraft.net/texture/97e56140686e476aef5520acbabc239535ff97e24b14d87f4982f13675c";
            case OBSIDIAN:
                return "http://textures.minecraft.net/texture/4de719b72909efa097815a63380f4456af9e4afebdd894e5b58b7c9e05675577";
            case BEDROCK:
                return "http://textures.minecraft.net/texture/bf7a422db35d28cfb67e6c1615cdac4d73007247187740ba8653899a44b7b520";
            default: // also Material.COMMAND; TODO: replace broken link or investigate
                return "http://textures.minecraft.net/texture/5f4c21d17ad636387ea3c736bff6ade897317e1374cd5d9b1c15e6e8953432";
        }
    }

    public static BridgeBuilderItem fromItemStack(ItemStack item) {

        if (!Objects.equals(item.getType(), Material.SKULL_ITEM)) {
            return null;
        }

        if (!item.hasItemMeta() || Objects.isNull(item.getItemMeta().getDisplayName())) {
            return null;
        }

        Matcher matcher = NAMING_PATTERN.matcher(item.getItemMeta().getDisplayName());

        if (!matcher.matches()) {
            return null;
        }

        Material material;
        int remainingBlocks;
        try {
            material = Material.valueOf(matcher.group(1));
            remainingBlocks = Integer.parseInt(matcher.group(2));

        } catch(IllegalStateException | IndexOutOfBoundsException | IllegalArgumentException e) {
            return null;
        }

        return new BridgeBuilderItem(material, remainingBlocks);
    }

    public int getRemainingBlocks() {
        return remainingBlocks;
    }

    public Material getBlockType() {
        return blockType;
    }
}
