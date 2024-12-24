package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.Mode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Config extends ConfigFile {

    private Mode mode;

    private boolean mergeTeams;

    private String prefix;
    private String lobbyName;
    private String arenaName;

    private Set<Material> breakables;

    private boolean bridgeBuilderUseOptimizedPlacement;
    private boolean bridgeBuilderNoCollisionsWithPlayerPlacedBlocks;
    private boolean bridgeBuilderNoHeadCollisionsWithBlocks;
    private boolean bridgeBuilderNoHeadCollisionsWithEntities;
    private double bridgeBuilderMovementSpeed;
    private Set<Material> bridgeBuilderReplaceableBlocks;
    private Set<EntityType> bridgeBuilderNonCollidingEntityTypes;
    private boolean bridgeBuilderUseDefaultColorForPlacedBlocks;
    private boolean bridgeBuilderUseDefaultColorForPlaceableBridgeBuilders;
    private boolean shopUseDefaultColorForPurchasableBridgeBuilders;

    private boolean shopUseDefaultColorsForPurchasableBlocks;

    private boolean enchanterAllowMultipleEnchantmentsPerItem;
    private boolean enchanterAddPreviousAndCancelButtons;

    private double specialistPersonalDogHealth;
    private double specialistTeamGolemHealth;

    private Set<Material> unbreakableTools;

    private EnchantableItemsConfig enchanterEnchantableItems;


    public Config(Logger logger, File configFile) {
        super(logger, configFile);
    }

    public Mode getMode() {
        return this.mode;
    }

    public boolean mergeTeams() {
        return this.mergeTeams;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getLobbyName() {
        return this.lobbyName;
    }

    public String getArenaName() {
        return this.arenaName;
    }

    public Set<Material> getBreakables() {
        return this.breakables;
    }

    public boolean bridgeBuilderUseOptimizedPlacement() {
        return bridgeBuilderUseOptimizedPlacement;
    }

    public boolean bridgeBuilderNoCollisionsWithPlayerPlacedBlocks() {
        return bridgeBuilderNoCollisionsWithPlayerPlacedBlocks;
    }

    public double getBridgeBuilderMovementSpeed() {
        return bridgeBuilderMovementSpeed;
    }

    public Set<Material> getBridgeBuilderReplaceableBlocks() {
        return bridgeBuilderReplaceableBlocks;
    }

    public boolean bridgeBuilderNoHeadCollisionsWithBlocks() {
        return bridgeBuilderNoHeadCollisionsWithBlocks;
    }

    public boolean bridgeBuilderNoHeadCollisionsWithEntities() {
        return bridgeBuilderNoHeadCollisionsWithEntities;
    }

    public Set<EntityType> getBridgeBuilderNonCollidingEntityTypes() {
        return bridgeBuilderNonCollidingEntityTypes;
    }

    public boolean getBridgeBuilderUseDefaultColorForPlacedBlocks() {
        return this.bridgeBuilderUseDefaultColorForPlacedBlocks;
    }
    public boolean getBridgeBuilderUseDefaultColorForPlaceableBridgeBuilders() {
        return this.bridgeBuilderUseDefaultColorForPlaceableBridgeBuilders;
    }
    public boolean getShopUseDefaultColorForPurchasableBridgeBuilders() {
        return this.shopUseDefaultColorForPurchasableBridgeBuilders;
    }

    public boolean getShopUseDefaultColorsForPurchasableBlocks() {
        return this.shopUseDefaultColorsForPurchasableBlocks;
    }

    public boolean getEnchanterAllowMultipleEnchantmentsPerItem() {
        return this.enchanterAllowMultipleEnchantmentsPerItem;
    }

    public boolean getEnchanterAddPreviousAndCancelButtons() {
        return this.enchanterAddPreviousAndCancelButtons;
    }

    public EnchantableItemsConfig getEnchanterEnchantableItems() {
        return this.enchanterEnchantableItems;
    }

    public double getSpecialistPersonalDogHealth() {
        return specialistPersonalDogHealth;
    }

    public double getSpecialistTeamGolemHealth() {
        return specialistTeamGolemHealth;
    }

    public Set<Material> getUnbreakableTools() {
        return this.unbreakableTools;
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {

        this.mode = parseMode(yamlContent.getString("mode"));
        this.prefix = parseString(yamlContent.getString("prefix"));
        this.lobbyName = parseString(yamlContent.getString("lobby_name"));
        this.arenaName = parseString(yamlContent.getString("arena_name"));

        this.mergeTeams = yamlContent.getBoolean("merge_teams");

        this.breakables = parseMaterialSet(yamlContent.getStringList("breakables"));

        this.bridgeBuilderUseOptimizedPlacement = yamlContent.getBoolean("bridge_builder.use_optimized_placement");
        this.bridgeBuilderNoCollisionsWithPlayerPlacedBlocks = yamlContent.getBoolean("bridge_builder.no_collisions_with_player_placed_blocks");
        this.bridgeBuilderNoHeadCollisionsWithBlocks = yamlContent.getBoolean("bridge_builder.no_head_collisions_with_blocks");
        this.bridgeBuilderNoHeadCollisionsWithEntities = yamlContent.getBoolean("bridge_builder.no_head_collisions_with_entities");
        this.bridgeBuilderMovementSpeed = yamlContent.getDouble("bridge_builder.movement_speed");
        this.bridgeBuilderReplaceableBlocks = parseMaterialSet(yamlContent.getStringList("bridge_builder.replaceable_blocks"));
        this.bridgeBuilderNonCollidingEntityTypes = parseEntityTypeSet(yamlContent.getStringList("bridge_builder.non_colliding_entity_types"));
        this.bridgeBuilderUseDefaultColorForPlacedBlocks = yamlContent.getBoolean("bridge_builder.use_default_color.placed_blocks");
        this.bridgeBuilderUseDefaultColorForPlaceableBridgeBuilders = yamlContent.getBoolean("bridge_builder.use_default_color.placeable_bridge_builders");
        this.shopUseDefaultColorForPurchasableBridgeBuilders = yamlContent.getBoolean("shop.use_default_color.purchasable_bridge_builders");
        this.shopUseDefaultColorsForPurchasableBlocks = yamlContent.getBoolean("shop.use_default_color.purchasable_blocks");

        this.enchanterAllowMultipleEnchantmentsPerItem = yamlContent.getBoolean("enchanter.allow_multiple_enchantments_per_item");
        this.enchanterAddPreviousAndCancelButtons = yamlContent.getBoolean("enchanter.add_previous_and_cancel_buttons", true);
        this.enchanterEnchantableItems = new EnchantableItemsConfig(yamlContent, "enchanter.enchantable_items");

        this.specialistPersonalDogHealth = yamlContent.getDouble("specialist.personal_dog_health", 20.0);
        this.specialistTeamGolemHealth = yamlContent.getDouble("specialist.team_golem_health", 100.0);

        this.unbreakableTools = parseMaterialSet(yamlContent.getStringList("unbreakable_tools"));
    }

    public static class EnchantableItemsConfig {

        private Map<Material, List<EnchantmentSpecification>> enchantableItems;

        public EnchantableItemsConfig(@Nonnull YamlDocument yamlDocument, @Nonnull String route) {
            parseAndValidateConfig(yamlDocument, route);
        }

        private void parseAndValidateConfig(@Nonnull YamlDocument yamlDocument, @Nonnull String route) {

            if (Objects.isNull(yamlDocument.getSection(route))) {
                this.enchantableItems = new HashMap<>();

            } else {

                this.enchantableItems = yamlDocument.getSection(route).getKeys().stream()
                        .map(o -> (String) o)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(
                                ConfigFile::parseMaterial,
                                materialString -> {
                                    Section section = yamlDocument.getSection(String.join(".", route, materialString));
                                    if (Objects.isNull(section)) {
                                        return new ArrayList<>();
                                    } else {
                                        return section.getKeys().stream()
                                                .map(o -> (String) o)
                                                .filter(Objects::nonNull)
                                                .map(s -> new EnchantmentSpecification(
                                                        yamlDocument, String.join(".", route, materialString, s
                                                )))
                                                .collect(Collectors.toList());
                                    }
                                }
                        ));
            }
        }

        public boolean isEnchantableMaterial(Material material) {
            return this.enchantableItems.containsKey(material);
        }

        public List<EnchantmentSpecification> getSupportedEnchantmens(Material material) {
            return this.isEnchantableMaterial(material) ? this.enchantableItems.get(material) : new ArrayList<>();
        }

        public Map<Material, List<EnchantmentSpecification>> getEnchantableItems() {
            return this.enchantableItems;
        }

        public static class EnchantmentSpecification {

            private Enchantment enchantment;
            private String displayName;
            private int level;
            private int cost;
            private Currency currency;

            public EnchantmentSpecification(@Nonnull YamlDocument yamlDocument, @Nonnull String route) {
                parseAndValidateConfig(yamlDocument, route);
            }

            private void parseAndValidateConfig(
                    @Nonnull YamlDocument yamlDocument, @Nonnull String route
            ) throws ConfigValidationException {

                String[] routeParts = route.split("\\.");

                this.enchantment = ConfigFile.parseEnchantment(routeParts[routeParts.length - 1]);
                this.displayName = ConfigFile.parseString(
                        yamlDocument.getString(String.join(".", route, "display_name"))
                );
                this.level = yamlDocument.getInt(String.join(".", route, "level"), 1);
                this.cost = yamlDocument.getInt(String.join(".", route, "cost"), 1);
                this.currency = ConfigFile.parseCurrency(
                        yamlDocument.getString(String.join(".", route, "currency"), "EMERALD")
                );
            }

            public Enchantment getEnchantment() {
                return this.enchantment;
            }

            public String getDisplayName() {
                return this.displayName;
            }

            public int getLevel() {
                return this.level;
            }

            public int getCost() {
                return this.cost;
            }

            public Currency getCurrency() {
                return this.currency;
            }
        }
    }
}
