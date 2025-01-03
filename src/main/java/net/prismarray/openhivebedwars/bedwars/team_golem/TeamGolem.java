package net.prismarray.openhivebedwars.bedwars.team_golem;

import net.minecraft.server.v1_8_R3.*;
import net.prismarray.openhivebedwars.bedwars.Team;

import java.lang.reflect.Field;
import java.util.List;

public class TeamGolem extends EntityIronGolem {

    private Team team = null;

    public TeamGolem(World world) {
        super(world);

        List goalB = (List)getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        List goalC = (List)getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        List targetB = (List)getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        List targetC = (List)getPrivateField("c", PathfinderGoalSelector.class, targetSelector);

        goalB.clear();
        goalC.clear();
        targetB.clear();
        targetC.clear();

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

        this.targetSelector.a(2, new PathfinderTeamGolemAttack<>(team, this, EntityHuman.class, true));
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object)
    {
        Field field;
        Object o = null;
        try
        {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return o;
    }
}
