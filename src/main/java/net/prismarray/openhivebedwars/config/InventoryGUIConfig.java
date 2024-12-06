package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import net.prismarray.openhivebedwars.gui.components.*;
import net.prismarray.openhivebedwars.util.Currency;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class InventoryGUIConfig extends ConfigFile {

    private final String GUIIdentifier;

    private String baseclass;
    private boolean locked;
    private Set<Integer> lockedSlots;
    private String title;
    private int size;
    private Map<Integer, InventoryGUIItemConfig> contents;

    private DyeColor frameColor;
    private boolean hasCancelButton;
    private String previousButtonDestination;
    private String nextButtonDestination;

    public InventoryGUIConfig(Logger logger, File configFile) {
        super(logger, configFile);

        this.GUIIdentifier = FilenameUtils.getBaseName(configFile.getName());
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {

        this.baseclass = parseString(yamlContent.getString("baseclass", "InventoryGUIBase"));
        this.locked = yamlContent.getBoolean("locked", false);
        this.lockedSlots = yamlContent.getIntList("locked_slots", null) == null ?
                null : new HashSet<>(yamlContent.getIntList("locked_slots", null));
        this.title = parseString(yamlContent.getString("title"));
        this.size = yamlContent.getInt("size");

        this.frameColor = parseDyeColor(yamlContent.getString("frame_color", "WHITE"));
        this.hasCancelButton = yamlContent.getBoolean("cancel_button", false);
        this.previousButtonDestination = parseString(yamlContent.getString("previous_button_destination", ""));
        this.nextButtonDestination = parseString(yamlContent.getString("previous_button_destination", ""));

        this.contents = yamlContent.getSection("contents").getKeys().stream()
                .map(o -> {
                    try {
                        return Integer.parseInt((String) o);
                    } catch (NumberFormatException | ClassCastException ignored) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Function.identity(),
                        slot -> new InventoryGUIItemConfig(yamlContent, slot)
                ));
    }

    public String getGUIIdentifier() {
        return this.GUIIdentifier;
    }

    public Function<InventoryGUIContext, ? extends InventoryGUIBase> getInventoryGUIFactroy() {
        
        if (Objects.equals(this.baseclass, "InventoryGUIFramed")) {
            return (context) -> {
                InventoryGUIFramed gui = new InventoryGUIFramed(
                        title,
                        (int) Math.ceil(size / 9.0),
                        frameColor,
                        hasCancelButton,
                        Objects.equals(previousButtonDestination, "") ? null : previousButtonDestination,
                        Objects.equals(nextButtonDestination, "") ? null : nextButtonDestination
                );

                addContents(gui, context);
                applyLockStatus(gui);

                return gui;
            };

        } else {
            return (context) -> {
                InventoryGUIBase gui = new InventoryGUIBase(title, size);

                addContents(gui, context);
                applyLockStatus(gui);

                return gui;
            };
        }
    }

    public void addContents(InventoryGUIBase gui, @Nonnull InventoryGUIContext context) {

        contents.forEach(
                (slot, config) -> config.getGUIItemFactory().apply(
                        new InventoryGUIContext(context.getOpeningPlayer(), context.getOpeningPlayerTeam(), gui, slot)
                )
        );
    }

    public void applyLockStatus(InventoryGUIBase gui) {

        if (Objects.nonNull(lockedSlots)) {
            lockedSlots.forEach(gui::lockSlot);
        }

        if (locked) {
            gui.lock();
        }
    }

    static class InventoryGUIItemConfig {

        private String baseclass;

        private Material material;
        private int amount;
        private short damage;
        private byte data;
        private String name;
        private List<String> lore;
        private boolean enchanted;
        private Set<ItemFlag> itemFlags;

        private String customHeadUrl;

        private int cost;
        private Currency currency;
        private boolean showFavStatus;
        private Material purchasedMaterial;
        private int purchasedAmount;
        private short purchasedDamage;
        private byte purchasedData;
        private String purchasedName;
        private List<String> purchasedLore;
        private Map<Enchantment, Integer> purchasedEnchantments;
        private Set<ItemFlag> purchasedItemFlags;
        private String purchasedCustomHeadUrl;

        private String destinationGUI;

        private InventoryGUIItemConfig(YamlDocument yamlContent, @Nonnull Integer slot) throws ConfigValidationException {
            try {
                this.parseAndValidateConfig(yamlContent, slot);

            } catch (ConfigValidationException e) {

                OpenHiveBedwars.getInstance().getLogger().warning(String.format("Could not parse item at slot %s. Using default placeholder...", slot));
                OpenHiveBedwars.getInstance().getLogger().warning(String.format("Error message: %s", e.getMessage()));

                baseclass = "InventoryGUICustomHead";
                customHeadUrl = "http://textures.minecraft.net/texture/bf2f871936c65aef31d715cb189271ef9113aa2eea1d7063a47c55c4692f223";

                material = Material.SKULL_ITEM;
                damage = (short) 3;
                amount = 1;
                name = "missing item";
                lore = new ArrayList<>();
                lore.add("could not parse item config");
                enchanted = false;
                itemFlags = null;
            }
        }

        private void parseAndValidateConfig(YamlDocument config, @Nonnull Integer slot) throws ConfigValidationException {

            String baseRoute = String.join(".", "contents", String.valueOf(slot));

            this.baseclass = config.getString(String.join(".", baseRoute, "baseclass"), "InventoryGUIItem");

            this.material = parseMaterial(config.getString(String.join(".", baseRoute, "material"), "AIR"));
            this.amount = config.getInt(String.join(".", baseRoute, "amount"), 1);
            this.damage = config.getShort(String.join(".", baseRoute, "damage"), (short) 0);
            this.data = config.getByte(String.join(".", baseRoute, "data"), (byte) 0);
            this.name = config.getString(String.join(".", baseRoute, "name"));
            this.lore = config.getStringList(String.join(".", baseRoute, "lore"));
            this.enchanted = config.getBoolean(String.join(".", baseRoute, "enchanted"), false);
            this.itemFlags = parseItemFlagSet(config.getStringList(String.join(".", baseRoute, "itemFlags")));

            this.customHeadUrl = config.getString(String.join(".", baseRoute, "customHeadURL"));

            this.cost = config.getInt(String.join(".", baseRoute, "cost"), 1);
            this.currency = parseCurrency(config.getString(String.join(".", baseRoute, "currency"), "IRON"));
            this.showFavStatus = config.getBoolean(String.join(".", baseRoute, "showFavStatus"), true);
            this.purchasedMaterial = parseMaterial(config.getString(String.join(".", baseRoute, "purchasedItem", "material"), material.toString()));
            this.purchasedAmount = config.getInt(String.join(".", baseRoute, "purchasedItem", "amount"), amount);
            this.purchasedDamage = config.getShort(String.join(".", baseRoute, "purchasedItem", "damage"), damage);
            this.purchasedData = config.getByte(String.join(".", baseRoute, "purchasedItem", "data"), data);
            this.purchasedName = config.getString(String.join(".", baseRoute, "purchasedItem", "name"));
            this.purchasedLore = config.getStringList(String.join(".", baseRoute, "purchasedItem", "lore"));
            this.purchasedEnchantments = parseEnchantmentMap(config.getStringList(String.join(".", baseRoute, "purchasedItem", "enchantments")));
            this.purchasedItemFlags = parseItemFlagSet(config.getStringList(String.join(".", baseRoute, "purchasedItem", "itemFlags")));
            this.purchasedCustomHeadUrl = config.getString(String.join(".", baseRoute, "purchasedItem", "customHeadURL"), customHeadUrl);

            this.destinationGUI = config.getString(String.join(".", baseRoute, "destinationGUI"));
        }

        public Function<InventoryGUIContext, InventoryGUIItem> getGUIItemFactory() {

            if (Objects.equals(this.baseclass, "InventoryGUICustomHead")) {

                return context -> new InventoryGUICustomHead(
                            context.getContainingGUI(),
                            context.getSlot(),
                            customHeadUrl,
                            amount,
                            name,
                            listToArray(lore),
                            enchanted,
                            new ArrayList<>(itemFlags)
                );

            } else if (Objects.equals(this.baseclass, "PurchasableItem")) {

                return context -> {
                    // ToDo: get the value of this variable via implementing the Favourites feature,
                    //  e.g. by combining inventory key and slot number or something similar -> data persistence?
                    boolean isFavourite = false;

                    short damage = this.damage;
                    short purchasedDamage = this.purchasedDamage;
                    if (material == Material.WOOL || material == Material.STAINED_GLASS || material == Material.STAINED_CLAY) {
                        damage = (OpenHiveBedwars.getBWConfig().getShopUseDefaultColorsForPurchasableBlocks()) ? DyeColor.WHITE.getWoolData() : context.getOpeningPlayerTeam().getColor().woolColor.getWoolData();
                        purchasedDamage = (OpenHiveBedwars.getBWConfig().getShopUseDefaultColorsForPurchasableBlocks()) ? DyeColor.WHITE.getWoolData() : context.getOpeningPlayerTeam().getColor().woolColor.getWoolData();
                    }

                    return new PurchasableItem(
                            context.getContainingGUI(),
                            context.getSlot(),
                            material,
                            damage,
                            amount,
                            enchanted,
                            name,
                            cost,
                            currency,
                            showFavStatus,
                            isFavourite,
                            listToArray(lore),
                            createItemStack(
                                    purchasedMaterial,
                                    purchasedDamage,
                                    purchasedData,
                                    purchasedAmount,
                                    purchasedName,
                                    purchasedLore,
                                    purchasedEnchantments,
                                    new ArrayList<>(purchasedItemFlags)
                            )
                    );
                };

            } else if (Objects.equals(this.baseclass, "PurchasableCustomHead")) {

                return context -> {
                    // ToDo: get the value of this variable via implementing the Favourites feature,
                    //  e.g. by combining inventory key and slot number or something similar -> data persistence?
                    boolean isFavourite = false;

                    return new PurchasableCustomHead(
                            context.getContainingGUI(),
                            context.getSlot(),
                            customHeadUrl,
                            amount,
                            enchanted,
                            name,
                            cost,
                            currency,
                            showFavStatus,
                            isFavourite,
                            listToArray(lore),
                            createCustomHead(
                                    purchasedCustomHeadUrl,
                                    purchasedAmount,
                                    purchasedName,
                                    purchasedLore,
                                    purchasedEnchantments,
                                    new ArrayList<>(purchasedItemFlags)
                            )
                    );
                };

            } else if (Objects.equals(this.baseclass, "PurchasableBridgeBuilder")) {

                return context -> {
                    // ToDo: get the value of this variable via implementing the Favourites feature,
                    //  e.g. by combining inventory key and slot number or something similar -> data persistence?
                    boolean isFavourite = false;

                    byte data = this.data;
                    byte purchasedData = this.purchasedData;
                    if (material == Material.WOOL || material == Material.STAINED_GLASS || material == Material.STAINED_CLAY) {
                        data = (OpenHiveBedwars.getBWConfig().getShopUseDefaultColorsForPurchasableBlocks()) ? DyeColor.WHITE.getWoolData() : context.getOpeningPlayerTeam().getColor().woolColor.getWoolData();
                        purchasedData = (OpenHiveBedwars.getBWConfig().getShopUseDefaultColorsForPurchasableBlocks()) ? DyeColor.WHITE.getWoolData() : context.getOpeningPlayerTeam().getColor().woolColor.getWoolData();
                    }

                    return new PurchasableCustomHead(
                            context.getContainingGUI(),
                            context.getSlot(),
                            BridgeBuilderItem.getURLForMaterial(material, data),
                            amount,
                            enchanted,
                            name,
                            cost,
                            currency,
                            showFavStatus,
                            isFavourite,
                            new BridgeBuilderItem(purchasedMaterial, purchasedAmount, purchasedData)
                    );
                };

            } else if (Objects.equals(this.baseclass, "CategorySelector")) {

                return context -> new CategorySelector(
                        context.getContainingGUI(),
                        context.getSlot(),
                        material,
                        damage,
                        name,
                        listToArray(lore),
                        destinationGUI
                );

            } else if (Objects.equals(this.baseclass, "FavouriteSlot")) {

                return context -> new DummyCustomSlot(
                        context.getContainingGUI(),
                        context.getSlot()
                );

            } else {

                return context -> new InventoryGUIItem(
                        context.getContainingGUI(),
                        context.getSlot(),
                        material,
                        damage,
                        amount,
                        name,
                        listToArray(lore),
                        enchanted,
                        new ArrayList<>(itemFlags)
                );
            }
        }
    }

    public static ItemStack createItemStack(
            Material material,
            short damage,
            byte data,
            int amount,
            String name,
            List<String> lore,
            Map<Enchantment, Integer> enchantmentLevels,
            List<ItemFlag> itemFlags) {

        ItemStack item = new ItemStack(material, amount, damage, data);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);

        enchantmentLevels.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        itemFlags.forEach(meta::addItemFlags);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createCustomHead(
            String url,
            int amount,
            String name,
            List<String> lore,
            Map<Enchantment, Integer> enchantmentLevels,
            List<ItemFlag> itemFlags) {

        ItemStack item = new InventoryGUICustomHead(
                null,
                -1,
                url,
                amount,
                name,
                null,
                false,
                null
        );
        ItemMeta meta = item.getItemMeta();

        meta.setLore(lore);

        enchantmentLevels.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        itemFlags.forEach(meta::addItemFlags);

        item.setItemMeta(meta);

        return item;
    }
}
