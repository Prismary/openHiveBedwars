package net.prismarray.openhivebedwars.util;

import net.prismarray.openhivebedwars.gui.components.SummonerUpgrade;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemNameBuilder {

    public static String getItemName(ItemStack item) {

        if (Objects.isNull(item)) {
            return "null";
        }

        if (item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }

        return Arrays.stream(item.getType().toString().split("_"))
                .map(String::toLowerCase)
                .map(s -> s.length() == 0 ? s : s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static String getPurchasableName(String name, int amount) {
        return String.format(
                "§f§l%sx §b§l%s",
                amount,
                name
        );
    }

    public static List<String> getPurchasableLore(int cost, Currency currency, boolean showFavStatus, boolean isFavorite, @Nullable List<String> bonusLore) {

        Stream<String> loreStream = Stream.of("");

        if (Objects.nonNull(bonusLore) && bonusLore.size() > 0) {
            loreStream = Stream.concat(loreStream, bonusLore.stream());
            loreStream = Stream.concat(loreStream, Stream.of(""));
        }

        loreStream = Stream.concat(loreStream, Stream.of(
                "§6§lCost",
                String.format(
                        "  %s%s %s",
                        currency.color,
                        cost,
                        currency.getChatNameForAmount(cost)
                )
        ));

        if (showFavStatus) {
            loreStream = Stream.concat(loreStream,
                    Stream.of((isFavorite) ? "§d➜ Right-Click to unfavorite" : "§d➜ Right-Click to favorite")
            );
        }

        loreStream = Stream.concat(loreStream, Stream.of("§b➜ Left-Click to purchase"));

        return loreStream.collect(Collectors.toList());
    }

    public static List<String> getEnchantableLore(List<String> initialLore) {

        Stream<String> loreStream = Stream.of("");

        if (Objects.nonNull(initialLore) && initialLore.size() > 0) {
            loreStream = Stream.concat(loreStream, initialLore.stream());
            loreStream = Stream.concat(loreStream, Stream.of(""));
        }

        loreStream = Stream.concat(loreStream, Stream.of(
                "§b► Click to Enchant"
        ));

        return loreStream.collect(Collectors.toList());
    }

    public static List<String> getCategorySelectorLore(String name, List<String> lore) {

        Stream<String> loreStream = Stream.of("");

        if (Objects.nonNull(lore) && lore.size() > 0) {
            loreStream = Stream.concat(loreStream, lore.stream());
            loreStream = Stream.concat(loreStream, Stream.of(""));
        }

        loreStream = Stream.concat(loreStream, Stream.of(
                String.format("§b► Click to view %s", name)
        ));

        return loreStream.collect(Collectors.toList());
    }

    public static String getSummonerUpgradeName(Currency currency, int level) {
        if (level > 1) {
            return String.format(
                    "§b§lLevel %s %s%s Summoner",
                    level,
                    currency.color,
                    currency.chatName
            );
        }

        return String.format(
                "§b§l%s Summoner",
                currency.chatName
        );
    }

    public static List<String> getSummonerUpgradeLore(TeamColor teamColor, Currency currency, int level, Currency purchaseCurrency, int cost) {
        if (SummonerUpgrade.isOwned(teamColor, currency, level)) {
            return Stream.of(
                    "",
                    "§a§lYou already own this.",
                    "",
                    "§b➜ §7Already owned"
            ).collect(Collectors.toList());
        }

        return Stream.of(
                "",
                "§6§lCost",
                String.format(
                        "  %s%s %s",
                        purchaseCurrency.color,
                        cost,
                        purchaseCurrency.getChatNameForAmount(cost)
                ),
                "",
                "§b➜ Left-Click to purchase"
        ).collect(Collectors.toList());
    }
}
