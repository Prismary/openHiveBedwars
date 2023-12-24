package net.prismarray.openhivebedwars.bedwars.bridgebuilder;

import net.prismarray.openhivebedwars.gui.InventoryGUICustomHead;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BridgeBuilderItem extends InventoryGUICustomHead {

    private static final Pattern NAMING_PATTERN = Pattern.compile("(.+) Bridge Builder \\[(\\d+)]");
    private static final String NAMING_FORMAT = "%s Bridge Builder [%d]";

    private final int remainingBlocks;
    private final Material blockType;

    public BridgeBuilderItem(Material blockType, int remainingBlocks) {
        super(getURLForMaterial(blockType), 1, String.format(NAMING_FORMAT, blockType.name(), remainingBlocks));
        this.remainingBlocks = remainingBlocks;
        this.blockType = blockType;
    }


    private static String getURLForMaterial(Material material) {

        if (Objects.equals(material, Material.WOOL)) {

            //ToDo: add links for other wool colors
            return "http://textures.minecraft.net/texture/9b5dfe704b6cec405ad79138f61cd702305bebb88d20f78c65397d485d162cd5";
        }

        switch (material) {
            case WOOD:
                return "http://textures.minecraft.net/texture/33be3b56c130d87f0e4657775e190139c2f24be530cf47f2e76ccddc35212af3";
            case STONE: // actually andesite, but who's counting...
                return "http://textures.minecraft.net/texture/d0b279e8b8a0c6eac838be33662dbbfd1ac71bfa9f96e3e80e886caf883da541";
            case ENDER_STONE:
                return "http://textures.minecraft.net/texture/3ae3591adaa2f3db578178cd8ca466a5d4ba4de98b214d6e5c94087598ec1522";

            // ToDo: Update the following skins to include the commandblock buttons
            case PRISMARINE:
                return "http://textures.minecraft.net/texture/97e56140686e476aef5520acbabc239535ff97e24b14d87f4982f13675c";
            case OBSIDIAN:
                return "http://textures.minecraft.net/texture/4de719b72909efa097815a63380f4456af9e4afebdd894e5b58b7c9e05675577";
            case BEDROCK:
                return "http://textures.minecraft.net/texture/bf7a422db35d28cfb67e6c1615cdac4d73007247187740ba8653899a44b7b520";
            default: // also Material.COMMAND
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
