package net.prismarray.openhivebedwars.bedwars.bridgebuilder;

import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.prismarray.openhivebedwars.gui.InventoryGUICustomHead;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BridgeBuilderItem extends InventoryGUICustomHead {

    private static final Pattern NAMING_PATTERN = Pattern.compile("§.§.(.+) §.Bridge Builder §.\\[§.(\\d+)§.]");
    private static final String NAMING_FORMAT = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "%s " + ChatColor.GREEN + "Bridge Builder " + ChatColor.DARK_GRAY + "["  + ChatColor.YELLOW + "%d"  + ChatColor.DARK_GRAY + "]";

    private final int remainingBlocks;
    private final Material blockType;

    public BridgeBuilderItem(Material blockType, int remainingBlocks) {
        this(blockType, remainingBlocks, (byte) 0);
    }

    public BridgeBuilderItem(Material blockType, int remainingBlocks, byte blockTypeData) {
        super(
                getURLForMaterial(blockType, blockTypeData),
                1,
                String.format(NAMING_FORMAT, getBridgeBuilderNameFromMaterial(blockType, blockTypeData), remainingBlocks),
                new String[]{
                        "",
                        ChatColor.GRAY + "Builds a bridge in the",
                        ChatColor.GRAY + "direction you were looking.",
                        "",
                        ChatColor.GREEN + "" + ChatColor.BOLD + "How to use",
                        ChatColor.GRAY + "Place the builder at the",
                        ChatColor.GRAY + "end of a block in the",
                        ChatColor.GRAY + "direction you'd like it to",
                        ChatColor.GRAY + "build, then watch!",
                        "",
                        ChatColor.AQUA + "" + ChatColor.BOLD + "Blocks left",
                        ChatColor.GRAY + String.valueOf(remainingBlocks)
                }
        );

        this.remainingBlocks = remainingBlocks;
        this.blockType = blockType;

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(this);

        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.set("blockTypeData", new NBTTagByte(blockTypeData));
        nmsStack.setTag(compound);

        ItemMeta taggedMeta = CraftItemStack.asBukkitCopy(nmsStack).getItemMeta();
        this.setItemMeta(taggedMeta);
    }


    public static String getURLForMaterial(Material material) {
        return getURLForMaterial(material, (byte) 0);
    }

    public static String getURLForMaterial(Material material, byte blockTypeData) {

        if (Objects.equals(material, Material.WOOL)) {

            switch(blockTypeData) {
                case 0:
                    return "http://textures.minecraft.net/texture/9b5dfe704b6cec405ad79138f61cd702305bebb88d20f78c65397d485d162cd5";
                case 1:
                    return "http://textures.minecraft.net/texture/69b5cc41d993ba9a1e49a5f47131083d9d8069ba47415471c9055b23f5c19e5b";
                case 2:
                    return "http://textures.minecraft.net/texture/6c52dcd359d79a73346be81c380bfc3752c47db0365caf47d29082df25a3c60d";
                case 3:
                    return "http://textures.minecraft.net/texture/c712d71b39593a670da81f194011995f4f1f996eb7473a64d9f3b8eb950eb588";
                case 4:
                    return "http://textures.minecraft.net/texture/cb567d7c06445b0862d72f9fdd8c31b73b18446ee773f4e2acfaa75ee743fda6";
                case 5:
                    return "http://textures.minecraft.net/texture/eb492b627e0515d22f51b3447ab8390bab764e78b53e2613ad9850edb9e27116";
                case 6:
                    return "http://textures.minecraft.net/texture/12bf686ac0ca584a8041b8a813f5228c18af1b218aaabeabffa294202c15d57b";
                case 7:
                    return "http://textures.minecraft.net/texture/b04553c0d2fa717ab427bc475765faed552c8ed1eee29cf64684a8668ccbec1c";
                case 8:
                    return "http://textures.minecraft.net/texture/bb1c5a93bb03b348f863a9dc6319de2c8058fe6e56a2b91b55e3ca866a1c1df6";
                case 9:
                    return "http://textures.minecraft.net/texture/68c5f601b173d60b9ac5d053c78cbd2128750362d372ed4343bdbc5edfc4faa3";
                case 10:
                    return "http://textures.minecraft.net/texture/98451e809d039211ae5bf0ecd2895f9a32394fcd300da212fbae4f7a5a679d2e";
                case 11:
                    return "http://textures.minecraft.net/texture/1c149a613041366e8dc8aec43331ebbcc8f0d74c58a1571c01bd387ab1484962";
                case 12:
                    return "http://textures.minecraft.net/texture/b38a294610c2a353b45113a27c9b48f84a5f06c31b7acb3d45c5983f8774eff2";
                case 13:
                    return "http://textures.minecraft.net/texture/1a6c3b29098f650b46f3928c892b5fecf40af0535d00be9c3712bd1017cb4fa8";
                case 14:
                    return "http://textures.minecraft.net/texture/96efb49e12b1f484ab441a733739f4e6a91e3ffda751b26b3fddb96439564ae9";
                case 15:
                    return "http://textures.minecraft.net/texture/6823f741ba1bd6b06319cec36766a2ea7a2d2bf88ad415821e64a036acd18a55";
                default:
                    return "http://textures.minecraft.net/texture/9b5dfe704b6cec405ad79138f61cd702305bebb88d20f78c65397d485d162cd5";
            }
        }

        if (Objects.equals(material, Material.STAINED_CLAY)) {

            switch(blockTypeData) {
                case 0:
                    return "http://textures.minecraft.net/texture/4206ea1dc0dd59ec3f7b16b3dbaf7053928b8c06e23aaf33ae4efbe77f01101a";
                case 1:
                    return "http://textures.minecraft.net/texture/7c4ebb704286ab375d2b6e3d1055ac3996f294a987ff943ecaada3bdbdbb9067";
                case 2:
                    return "http://textures.minecraft.net/texture/657014647b3581e306a7acb4570d1ca9982caf64c828b7cc2bafe746016d0384";
                case 3:
                    return "http://textures.minecraft.net/texture/61436c8b1948e1b9b6a8ccfd84932bc1c5916692c2679dc8a8c529416746ef7b";
                case 4:
                    return "http://textures.minecraft.net/texture/cf2439bf06f8af272dbff5e926d2bd5effb69a3f85e1058dd4c1229604650242";
                case 5:
                    return "http://textures.minecraft.net/texture/217b15883511d6b582bdd4afc97adb5eeb3a836cfd9284dc5a2a888e0d8191f";
                case 6:
                    return "http://textures.minecraft.net/texture/92bfcadd421306ea2feeb8f6b6b74ee29611be1d5e1b228aecb99700592fd041";
                case 7:
                    return "http://textures.minecraft.net/texture/476dced0d4bddfd89d0df5517dc4e2f4484205becdfa997163cbdf1df126c47d";
                case 8:
                    return "http://textures.minecraft.net/texture/9561279aab39fff49c896d8a48454744ef3d87f56114748ac5e328ef78087e8";
                case 9:
                    return "http://textures.minecraft.net/texture/33c6c251b233be1381245fddd3d2391855b27afc37f0c17f74bcd52f8092ace4";
                case 10:
                    return "http://textures.minecraft.net/texture/3e90847074404da5cc9539af98ac337d0ab4593221c3dcab8d1cac0b7fa98d16";
                case 11:
                    return "http://textures.minecraft.net/texture/7dc4f76c687312548d4ed7fbdfde9c1e39f4c1e5b8b6916db94f40ba076309c8";
                case 12:
                    return "http://textures.minecraft.net/texture/909847ed6644a38c90240ec6857d95e9736e5850ee9d267623b08a7307339859";
                case 13:
                    return "http://textures.minecraft.net/texture/700c4672c750eef54e50697cdcee996975daedf1cd9904ee42d95257dbb7ffb4";
                case 14:
                    return "http://textures.minecraft.net/texture/1c5b7f20a7cd1ffc510b43f740a09339c9b76df33defe5b5edf4cf0a580989cf";
                case 15:
                    return "http://textures.minecraft.net/texture/c8d98adb84dc9bdbec80e94f81a2e1a12bb49f6b2856f291356d64177dd2cc8b";
                default:
                    return "http://textures.minecraft.net/texture/4206ea1dc0dd59ec3f7b16b3dbaf7053928b8c06e23aaf33ae4efbe77f01101a";
            }
        }

        switch (material) {
            case STONE:
                return "http://textures.minecraft.net/texture/d0b279e8b8a0c6eac838be33662dbbfd1ac71bfa9f96e3e80e886caf883da541";
            case WOOD:
                return "http://textures.minecraft.net/texture/33be3b56c130d87f0e4657775e190139c2f24be530cf47f2e76ccddc35212af3";
            case ENDER_STONE:
                return "http://textures.minecraft.net/texture/3ae3591adaa2f3db578178cd8ca466a5d4ba4de98b214d6e5c94087598ec1522";
            case PRISMARINE:
                return "http://textures.minecraft.net/texture/fdc049c78b9785a5b092cf0dd3f402c513f8486262b8afb139aa37930f3c8f9";
            case OBSIDIAN:
                return "http://textures.minecraft.net/texture/1d853f249fe3476d62419d2c21ca305e1a85f693603403e64cfec383c1b5667f";
            case BEDROCK:
                return "http://textures.minecraft.net/texture/2067c067bb0f7bdbaa4a55c601af7dd566c7f10f63675a34e934b24ded262f6e";
            default: // also Material.COMMAND
                return "http://textures.minecraft.net/texture/5f4c21d17ad636387ea3c736bff6ade897317e1374cd5d9b1c15e6e8953432";
        }
    }

    public static String getBridgeBuilderNameFromMaterial(Material material) {
        return getBridgeBuilderNameFromMaterial(material, (byte) 0);
    }

    public static String getBridgeBuilderNameFromMaterial(Material material, byte data) {

        if (Objects.equals(material, Material.STONE) && data == 5) {
            return "Andesite";
        }

        String name = material.toString().replace("_", " ");
        return name.toUpperCase().charAt(0) + name.toLowerCase().substring(1);
    }

    public static Material getMaterialFromBridgeBuilderName(String name) {

        if (Objects.equals(name, "Andesite")) {
            return Material.STONE;
        }

        Material material = null;
        try {
            material = Material.valueOf(name.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException ignored) {}

        return material;
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
            material = getMaterialFromBridgeBuilderName(matcher.group(1));
            if (Objects.isNull(material)) {
                return null;
            }
            remainingBlocks = Integer.parseInt(matcher.group(2));

        } catch(IllegalStateException | IndexOutOfBoundsException | IllegalArgumentException e) {
            return null;
        }

        return new BridgeBuilderItem(material, remainingBlocks, getBlockTypeData(item));
    }

    public int getRemainingBlocks() {
        return remainingBlocks;
    }

    public Material getBlockType() {
        return blockType;
    }

    public byte getBlockTypeData() {
        return getBlockTypeData(this);
    }

    public static byte getBlockTypeData(ItemStack item) {

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        if (!nmsStack.hasTag()) {
            return 0;
        }

        if (!nmsStack.getTag().hasKeyOfType("blockTypeData", (new NBTTagByte((byte) 0)).getTypeId())) {
            return 0;
        }

        return nmsStack.getTag().getByte("blockTypeData");
    }
}
