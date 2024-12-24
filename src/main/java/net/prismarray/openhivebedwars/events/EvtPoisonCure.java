package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class EvtPoisonCure extends EventBase {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        if (!Objects.equals(Game.getStatus(), Status.INGAME)) {
            return;
        }

        if (!Objects.equals(event.getAction(), Action.RIGHT_CLICK_BLOCK)
                && !Objects.equals(event.getAction(), Action.RIGHT_CLICK_AIR)) {
            return;
        }

        if (!event.hasItem()) {
            return;
        }

        if (!Objects.equals(event.getItem().getType(), Material.MAGMA_CREAM)
                || !event.getItem().hasItemMeta()
                || !event.getItem().getItemMeta().hasDisplayName()
                || !Objects.equals(event.getItem().getItemMeta().getDisplayName(), "Poison Cure")) {
            return;
        }

        if (Objects.isNull(event.getPlayer())) {
            return;
        }
        Player p = event.getPlayer();

        event.setCancelled(true);

        if (!p.hasPotionEffect(PotionEffectType.POISON)) {
            return;
        }

        p.removePotionEffect(PotionEffectType.POISON);

        if (p.getItemInHand().getAmount() > 1) {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);

        } else {
            p.setItemInHand(null);
        }
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {

        if (!Objects.equals(event.getEntityType(), EntityType.WOLF)) {
            return;
        }

        if (!event.getEntity().hasMetadata("team")) {
            return;
        }

        Team team = (Team) event.getEntity().getMetadata("team").get(0).value();

        if (!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }

        Entity damager = ((EntityDamageByEntityEvent) event).getDamager();

        if (!(damager instanceof Player)) {
            return;
        }

        Player p = (Player) damager;

        if (!Objects.equals(team, Game.getTeamHandler().getPlayerTeam(p))) {
            return;
        }

        event.setCancelled(true);
        // ToDo: maybe send a funny message
    }
}
