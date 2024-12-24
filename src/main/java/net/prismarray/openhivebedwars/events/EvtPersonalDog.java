package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.util.Status;
import net.prismarray.openhivebedwars.util.TeamMetadataValue;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class EvtPersonalDog extends EventBase {

    @EventHandler
    public void onSpawnEggClick(PlayerInteractEvent event) {

        if (!Objects.equals(Game.getStatus(), Status.INGAME)) {
            return;
        }

        if (!Objects.equals(event.getAction(), Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (Objects.isNull(event.getClickedBlock())) {
            return;
        }
        Block clickedBlock = event.getClickedBlock();

        if (!event.hasItem()) {
            return;
        }

        if (!Objects.equals(event.getItem().getType(), Material.MONSTER_EGG)) {
            return;
        }

        if (event.getItem().getDurability() != 95) {
            return;
        }

        if (Objects.isNull(event.getPlayer())) {
            return;
        }
        Player p = event.getPlayer();

        event.setCancelled(true);

        long personalDogs = p.getWorld().getEntitiesByClasses(Wolf.class).stream()
                .map(entity -> (Wolf) entity)
                .filter(wolf -> Objects.equals(wolf.getOwner(), p))
                .count();

        if (personalDogs > 0) {
            // ToDo: send error message
            return;
        }

        Wolf dog = (Wolf) event.getPlayer().getWorld().spawnEntity(
                clickedBlock.getLocation()
                        .add(new Vector(0.5, 0, 0.5))
                        .add(new Vector(
                                event.getBlockFace().getModX(),
                                event.getBlockFace().getModY(),
                                event.getBlockFace().getModZ()
                        )),
                EntityType.WOLF
        );
        dog.setMetadata("team", new TeamMetadataValue(Game.getTeamHandler().getPlayerTeam(p)));

        dog.setCollarColor(Game.getTeamHandler().getPlayerTeam(p).getColor().woolColor);
        dog.setCustomName(String.format(
                "%s§l%s's §b§lPersonal Doggo",
                Game.getTeamHandler().getPlayerTeam(p).getColor().chatColor,
                p.getDisplayName()
        ));
        dog.setCustomNameVisible(true);
        dog.setOwner(p);
        dog.setTamed(true);
        dog.setSitting(false);

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
