package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CombatHandler {

    Game game;
    Map<Player, ArrayList<Player>> attackers;

    public CombatHandler(Game game) {
        this.game = game;
        attackers = new HashMap<>();
    }

    public void playerDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
                !(((EntityDamageByEntityEvent) event).getDamager() instanceof Player)) {
            return;
        }

        Player damager = (Player) ((EntityDamageByEntityEvent) event).getDamager();
        Player damaged = (Player) event.getEntity();

        if (game.getTeamHandler().areTeammates(damager, damaged) || !game.getTeamHandler().isInTeam(damager)) { // Cancel if players are teammates or player is spectator
            event.setCancelled(true);
            return;
        }

        if (!attackers.containsKey(damaged)) { // Add attacker to list
            attackers.put(damaged, new ArrayList<>());
        }
        attackers.get(damaged).add(0, damager);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(game.plugin, new Runnable() { // Schedule entry removal
            @Override
            public void run() {
                attackers.get(damaged).remove(damager);
            }
        }, 200);
    }

    public void playerDeath(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();

        if (game.getTeamHandler().getPlayerTeam(player).hasBed()) { // In case of intact bed
            if (lastAttackerPresent(player)) {
                Broadcast.kill(
                        attackers.get(player).get(0).getName(),
                        game.getTeamHandler().getPlayerTeam(attackers.get(player).get(0)).getColor(),
                        player.getName(),
                        game.getTeamHandler().getPlayerTeam(player).getColor()
                );
                sendEnemyHealth(player, attackers.get(player).get(0));
            } else {
                Broadcast.death(
                        player.getName(),
                        game.getTeamHandler().getPlayerTeam(player).getColor()
                );
            }
            dropInventory(player, false);
            game.respawnPlayer(player);

        } else { // In case of broken bed
            if (lastAttackerPresent(player)) {
                Broadcast.finalKill(
                        attackers.get(player).get(0).getName(),
                        game.getTeamHandler().getPlayerTeam(attackers.get(player).get(0)).getColor(),
                        player.getName(),
                        game.getTeamHandler().getPlayerTeam(player).getColor()
                );
                sendEnemyHealth(player, attackers.get(player).get(0));
            } else {
                Broadcast.death(
                        player.getName(),
                        game.getTeamHandler().getPlayerTeam(player).getColor()
                );
            }
            dropInventory(player, true);
            game.getTeamHandler().finalKill(player);
            game.setSpectatorPlayer(player);
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
        if (attackers.containsKey(player)) {
            if (!attackers.get(player).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void sendEnemyHealth(Player recipient, Player enemy) {
        Broadcast.toPlayer(recipient, "§7Your killer had §c" + enemy.getHealth() / 2 + " hearts §7left.");
    }
}
