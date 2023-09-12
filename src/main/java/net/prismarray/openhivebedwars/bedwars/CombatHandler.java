package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.scoreboard.ScoreboardManager;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CombatHandler {

    private final Map<Player, ArrayList<Player>> attackers;

    public CombatHandler() {
        attackers = new HashMap<>();
    }

    public void playerDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
                !(((EntityDamageByEntityEvent) event).getDamager() instanceof Player)) {
            return;
        }

        Player damager = (Player) ((EntityDamageByEntityEvent) event).getDamager();
        Player damaged = (Player) event.getEntity();

        if (Game.getTeamHandler().areTeammates(damager, damaged) || !Game.getTeamHandler().isInTeam(damager)) { // Cancel if players are teammates or player is spectator
            event.setCancelled(true);
            return;
        }

        if (!attackers.containsKey(damaged)) { // Add attacker to list
            attackers.put(damaged, new ArrayList<>());
        }
        attackers.get(damaged).add(0, damager);

        // Schedule entry removal
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
                OpenHiveBedwars.getInstance(), () -> attackers.get(damaged).remove(damager), 200
        );
    }

    public void playerDeath(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();

        if (Game.getTeamHandler().getPlayerTeam(player).hasBed()) { // In case of intact bed
            if (lastAttackerPresent(player)) {
                Game.getStatsManager().kill(attackers.get(player).get(0), player);
                Broadcast.kill(
                        attackers.get(player).get(0),
                        Game.getTeamHandler().getPlayerTeam(attackers.get(player).get(0)).getColor(),
                        player,
                        Game.getTeamHandler().getPlayerTeam(player).getColor()
                );

                sendEnemyHealth(player, attackers.get(player).get(0));

            } else {
                Broadcast.death(
                        player,
                        Game.getTeamHandler().getPlayerTeam(player).getColor()
                );
                Game.getStatsManager().addDeath(player);
            }
            ScoreboardManager.updateAll();

            dropInventory(player, false);
            Game.respawnPlayer(player);

        } else { // In case of broken bed
            if (lastAttackerPresent(player)) {
                Game.getStatsManager().finalKill(attackers.get(player).get(0), player);
                Broadcast.finalKill(
                        attackers.get(player).get(0),
                        Game.getTeamHandler().getPlayerTeam(attackers.get(player).get(0)).getColor(),
                        player,
                        Game.getTeamHandler().getPlayerTeam(player).getColor()
                );
                sendEnemyHealth(player, attackers.get(player).get(0));
            } else {
                Broadcast.death(
                        player,
                        Game.getTeamHandler().getPlayerTeam(player).getColor()
                );
                Game.getStatsManager().addDeath(player);
            }
            dropInventory(player, true);
            Game.getTeamHandler().finalKill(player);
            Game.setSpectatorPlayer(player);
        }
    }

    public void dropInventory(Player player, boolean fullDrop) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) {
                continue;
            }
            player.getWorld().dropItem(player.getLocation(), itemStack).setVelocity(new Vector(0, 0, 0));
            player.getInventory().remove(itemStack);
        }
        if (fullDrop) { // Also drop armor on full drop
            for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                if (itemStack == null || itemStack.getData().getItemType() == Material.AIR) {
                    continue;
                }
                player.getWorld().dropItem(player.getLocation(), itemStack).setVelocity(new Vector(0, 0, 0));
            }
            player.getInventory().setArmorContents(null);
        }
    }

    public boolean lastAttackerPresent(Player player) {
        return attackers.containsKey(player) && !attackers.get(player).isEmpty();
    }

    public void sendEnemyHealth(Player recipient, Player enemy) {
        Broadcast.toPlayer(recipient, "§7Your killer had §c" + enemy.getHealth() / 2 + " hearts §7left.");
    }
}
