package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Status;
import net.prismarray.openhivebedwars.util.TeamMetadataValue;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class EvtTeamGolem extends EventBase {

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

        if (!Objects.equals(event.getItem().getType(), Material.PUMPKIN)) {
            return;
        }

        if (!event.getItem().hasItemMeta()
                || !event.getItem().getItemMeta().hasDisplayName()
                || !Objects.equals(event.getItem().getItemMeta().getDisplayName(), "Team Golem")) {
            return;
        }

        if (Objects.isNull(event.getPlayer())) {
            return;
        }
        Player p = event.getPlayer();

        event.setCancelled(true);

        long teamGolems = p.getWorld().getEntitiesByClasses(Golem.class).stream()
                .map(entity -> (Golem) entity)
                .filter(golem -> golem.hasMetadata("team"))
                .filter(golem -> Objects.equals(golem.getMetadata("team").get(0).value(), Game.getTeamHandler().getPlayerTeam(p)))
                .count();

        if (teamGolems > 0) {
            // ToDo: send error message
            return;
        }

        // ToDo: implement custom entity type extending Golem, which modifies the pathfinding such that the golem
        //  attacks any player of competing teams in a certain range (https://www.spigotmc.org/threads/force-iron-golems-to-attack-players.482652/)
        Golem golem = (Golem) event.getPlayer().getWorld().spawnEntity(
                clickedBlock.getLocation()
                        .add(new Vector(0.5, 0, 0.5))
                        .add(new Vector(
                                event.getBlockFace().getModX(),
                                event.getBlockFace().getModY(),
                                event.getBlockFace().getModZ()
                        )),
                EntityType.IRON_GOLEM
        );
        golem.setMetadata("team", new TeamMetadataValue(Game.getTeamHandler().getPlayerTeam(p)));

        golem.setCustomName(String.format(
                "%s%s's Protector",
                Game.getTeamHandler().getPlayerTeam(p).getColor().chatColor,
                Game.getTeamHandler().getPlayerTeam(p).getColor().chatName
        ));
        golem.setCustomNameVisible(true);
        golem.setMaxHealth(OpenHiveBedwars.getBWConfig().getSpecialistTeamGolemHealth());
        golem.setHealth(OpenHiveBedwars.getBWConfig().getSpecialistTeamGolemHealth());

        Broadcast.toTeam(
                Game.getTeamHandler().getPlayerTeam(p),
                String.format(
                        "§6A Team Golem was spawned by %s%s",
                        Game.getTeamHandler().getPlayerTeam(p).getColor().chatColor,
                        p.getDisplayName()
                )
        );

        if (p.getItemInHand().getAmount() > 1) {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);

        } else {
            p.setItemInHand(null);
        }
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {

        if (!Objects.equals(event.getEntityType(), EntityType.IRON_GOLEM)) {
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
