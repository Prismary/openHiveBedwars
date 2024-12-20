package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import net.prismarray.openhivebedwars.gui.components.SummonerUpgrade;
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

    private String frameColor;
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

        this.frameColor = yamlContent.getString("frame_color", "WHITE");
        this.hasCancelButton = yamlContent.getBoolean("cancel_button", false);
        this.previousButtonDestination = yamlContent.getString("previous_button_destination");
        this.nextButtonDestination = yamlContent.getString("next_button_destination");

        if (Objects.isNull(yamlContent.getSection("contents"))) {
            this.contents = new HashMap<>();

        } else {
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
    }

    public String getGUIIdentifier() {
        return this.GUIIdentifier;
    }

    public Function<InventoryGUIContext, ? extends InventoryGUIBase> getInventoryGUIFactroy() {
        
        if (Objects.equals(this.baseclass, "InventoryGUIFramed")) {
            return (context) -> {
                InventoryGUIFramed gui = new InventoryGUIFramed(
                        context.parseStringPlaceholders(title),
                        (int) Math.ceil(size / 9.0),
                        parseDyeColorOrDefault(context.parseStringPlaceholders(frameColor), DyeColor.WHITE),
                        hasCancelButton,
                        previousButtonDestination,
                        nextButtonDestination
                );

                addContents(gui, context);
                applyLockStatus(gui);

                return gui;
            };

        } else {
            return (context) -> {
                InventoryGUIBase gui = new InventoryGUIBase(
                        context.parseStringPlaceholders(title),
                        size
                );

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
        private int purchasedBridgeBuilderBlocks;

        private String destinationGUI;

        private Currency summonerCurrency;
        private int summonerUpgradeLevel;

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
            this.purchasedBridgeBuilderBlocks = config.getInt(String.join(".", baseRoute, "purchasedItem", "bridgeBuilderBlocks"), 32);

            this.destinationGUI = config.getString(String.join(".", baseRoute, "destinationGUI"));

            this.summonerCurrency = parseCurrency(config.getString(String.join(".", baseRoute, "summonerCurrency"), "IRON"));
            this.summonerUpgradeLevel = config.getInt(String.join(".", baseRoute, "summonerUpgradeLevel"), 1);
        }

        public Function<InventoryGUIContext, InventoryGUIItem> getGUIItemFactory() {

            if (Objects.equals(this.baseclass, "InventoryGUICustomHead")) {

                return context -> new InventoryGUICustomHead(
                            context.getContainingGUI(),
                            context.getSlot(),
                            customHeadUrl,
                            amount,
                            context.parseStringPlaceholders(name),
                            lore.stream().map(context::parseStringPlaceholders).collect(Collectors.toList()),
                            enchanted,
                            new ArrayList<>(itemFlags)
                );

            } else if (Objects.equals(this.baseclass, "PurchasableItem")) {

                return context -> {
                    // ToDo: get the value of this variable via implementing the Favourites feature,
                    //  e.g. by combining inventory key and slot number or something similar -> data persistence?
                    boolean isFavourite = false;

                    short damage = getTeamSpecificDamageValue(context, material, this.damage);
                    short purchasedDamage = getTeamSpecificDamageValue(context, purchasedMaterial, this.purchasedDamage);
                    byte purchasedData = (byte) purchasedDamage;

                    return new PurchasableItem(
                            context.getContainingGUI(),
                            context.getSlot(),
                            material,
                            damage,
                            amount,
                            enchanted,
                            context.parseStringPlaceholders(name),
                            cost,
                            currency,
                            showFavStatus,
                            isFavourite,
                            lore.stream().map(context::parseStringPlaceholders).collect(Collectors.toList()),
                            createItemStack(
                                    purchasedMaterial,
                                    purchasedDamage,
                                    purchasedData,
                                    purchasedAmount,
                                    context.parseStringPlaceholders(purchasedName),
                                    purchasedLore.stream().map(context::parseStringPlaceholders).collect(Collectors.toList()),
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
                            context.parseStringPlaceholders(name),
                            cost,
                            currency,
                            showFavStatus,
                            isFavourite,
                            lore.stream().map(context::parseStringPlaceholders).collect(Collectors.toList()),
                            createCustomHead(
                                    purchasedCustomHeadUrl,
                                    purchasedAmount,
                                    context.parseStringPlaceholders(purchasedName),
                                    purchasedLore.stream().map(context::parseStringPlaceholders).collect(Collectors.toList()),
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

                    short damage = getTeamSpecificDamageValue(context, material, this.damage);
                    short purchasedDamage = getTeamSpecificDamageValue(context, purchasedMaterial, this.purchasedDamage);

                    return new PurchasableCustomHead(
                            context.getContainingGUI(),
                            context.getSlot(),
                            BridgeBuilderItem.getURLForMaterial(material, (byte) damage),
                            amount,
                            enchanted,
                            context.parseStringPlaceholders(name),
                            cost,
                            currency,
                            showFavStatus,
                            isFavourite,
                            lore.stream().map(context::parseStringPlaceholders).collect(Collectors.toList()),
                            new BridgeBuilderItem(purchasedMaterial, purchasedBridgeBuilderBlocks, (byte) purchasedDamage, purchasedAmount)
                    );
                };

            } else if (Objects.equals(this.baseclass, "CategorySelector")) {

                return context -> new CategorySelector(
                        context.getContainingGUI(),
                        context.getSlot(),
                        material,
                        damage,
                        context.parseStringPlaceholders(name),
                        lore.stream().map(context::parseStringPlaceholders).collect(Collectors.toList()),
                        destinationGUI
                );

            } else if (Objects.equals(this.baseclass, "FavouriteSlot")) {

                return context -> new DummyCustomSlot(
                        context.getContainingGUI(),
                        context.getSlot()
                );

            } else if (Objects.equals(this.baseclass, "SummonerUpgrade")) {

                return context -> new SummonerUpgrade(
                        context.getContainingGUI(),
                        context.getSlot(),
                        context.getOpeningPlayerTeam().getColor(),
                        summonerCurrency,
                        summonerUpgradeLevel,
                        currency,
                        cost,
                        destinationGUI
                );

            } else {

                return context -> new InventoryGUIItem(
                        context.getContainingGUI(),
                        context.getSlot(),
                        material,
                        damage,
                        amount,
                        context.parseStringPlaceholders(name),
                        lore.stream().map(context::parseStringPlaceholders).collect(Collectors.toList()),
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

    public static short getTeamSpecificDamageValue(InventoryGUIContext context, Material configuredMaterial, short configuredValue) {

        if (!(
                configuredMaterial == Material.WOOL
                        || configuredMaterial == Material.STAINED_GLASS
                        || configuredMaterial == Material.STAINED_CLAY
        )) {
            return configuredValue;
        }

        if (OpenHiveBedwars.getBWConfig().getShopUseDefaultColorsForPurchasableBlocks()) {
            return DyeColor.WHITE.getWoolData();

        } else {
            return context.getOpeningPlayerTeam().getColor().woolColor.getWoolData();
        }
    }
}
