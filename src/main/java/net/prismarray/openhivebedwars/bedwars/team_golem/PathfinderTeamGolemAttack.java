package net.prismarray.openhivebedwars.bedwars.team_golem;

import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PathfinderTeamGolemAttack<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T> {

    public PathfinderTeamGolemAttack(Team team, EntityCreature entitycreature, Class<T> oclass, boolean flag) {
        super(entitycreature, oclass, 10, flag, true, entity -> PathfinderTeamGolemAttack.targetPredicate(entity, team));
    }

    public static boolean targetPredicate(EntityLiving entity, Team golemTeam) {

        if (!(entity instanceof EntityHuman)) {
            return false;
        }

        Player p = (Player) entity.getBukkitEntity();
        Team team = Game.getTeamHandler().getPlayerTeam(p);

        if (Objects.isNull(team)) {
            return false;
        }

        return !Objects.equals(team, golemTeam);
    }
}
