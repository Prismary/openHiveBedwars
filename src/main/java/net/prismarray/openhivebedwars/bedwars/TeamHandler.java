package net.prismarray.openhivebedwars.bedwars;

import com.google.common.collect.ImmutableList;
import net.prismarray.openhivebedwars.util.GSU;
import net.prismarray.openhivebedwars.util.Mode;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TeamHandler {

    Game game;
    ArrayList<Team> teams;
    Map<Player, ArrayList<Player>> invites;
    int maxTeamCount;

    public TeamHandler(Game game) {
        this.game = game;
        teams = new ArrayList<>();
        invites = new HashMap<>();

        switch(game.mode) {
            case SOLO:
                maxTeamCount = 12;
                break;
            case DUOS:
                maxTeamCount = 8;
                break;
            case TEAMS:
                maxTeamCount = 4;
                break;
        }
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public boolean isInTeam(Player player) {
        return !(getPlayerTeam(player) == null);
    }

    public Team getPlayerTeam(Player player) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getPlayers().contains(player)) {
                return teams.get(i);
            }
        }
        return null;
    }

    public ArrayList<Team> getEmptyTeams() {
        ArrayList<Team> empty = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getPlayerCount() == 0) {
                empty.add(teams.get(i));
            }
        }
        return empty;
    }

    public ArrayList<Player> getTeamlessPlayers() {
        ImmutableList<Player> currentPlayers =  ImmutableList.copyOf(game.plugin.getServer().getOnlinePlayers());
        ArrayList<Player> teamlessPlayers = new ArrayList<>();

        for (int i = 0; i < game.plugin.getServer().getOnlinePlayers().size(); i++) {
            if (!isInTeam(currentPlayers.get(i))) {
                teamlessPlayers.add(currentPlayers.get(i));
            }
        }

        return teamlessPlayers;
    }

    public boolean newTeam(Player founder) {
        if (teams.size() >= maxTeamCount) { // return early if team limit is reached
            return false;
        }

        switch(game.mode) {
            case SOLO:
                teams.add(new Team(1));
                break;
            case DUOS:
                teams.add(new Team(2));
                break;
            case TEAMS:
                teams.add(new Team(4));
                break;
        }
        teams.get(teams.size()-1).addPlayer(founder);

        return true;
    }

    public boolean addToPlayer(Player joiningPlayer, Player invitingPlayer) {
        if (!isInTeam(invitingPlayer)) { // create team if player is in no team and return early if not successful
            if (!newTeam(invitingPlayer)) {
                return false;
            }
        }

        return getPlayerTeam(invitingPlayer).addPlayer(joiningPlayer);
    }

    public boolean removePlayer(Player player) {
        try {
            return getPlayerTeam(player).removePlayer(player);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean invitePlayer(Player inviter, Player invitee) {
        if (inviter == null || invitee == null || game.mode == Mode.SOLO) {
            return false;
        }

        if (!invites.containsKey(inviter)) {
            invites.put(inviter, new ArrayList<>());
        }
        invites.get(inviter).add(invitee);
        return true;
    }

    public boolean isInvitedBy(Player inviter, Player invitee) {
        if (!invites.containsKey(inviter)) {
            return false;
        }
        return invites.get(inviter).contains(invitee);
    }

    public boolean removeInvite(Player inviter, Player invitee) {
        if (!invites.containsKey(inviter)) {
            return false;
        }
        return invites.get(inviter).remove(invitee);
    }

    public void assignAndMerge() { // todo merge logic
        ArrayList<Player> teamless = getTeamlessPlayers();

        for (int i = 0; i < teams.size(); i++) {
            while (teams.get(i).getPlayerCount() < teams.get(i).size) {
                if (!teamless.isEmpty()) {
                    teams.get(i).addPlayer(teamless.get(0));
                    teamless.remove(0);
                }
            }
        }
    }

    public void colorize() {
        switch (game.mode) {
            case SOLO:
                for (int i = 0; i < teams.size(); i++) {
                    teams.get(i).setColor(TeamColor.getSoloModeColours()[i]);
                }
                break;
            case DUOS:
                for (int i = 0; i < teams.size(); i++) {
                    teams.get(i).setColor(TeamColor.getDuosModeColours()[i]);
                }
                break;
            case TEAMS:
                for (int i = 0; i < teams.size(); i++) {
                    teams.get(i).setColor(TeamColor.getTeamsModeColours()[i]);
                }
                break;
        }
    }

    public void tryDissolution(Team team) {
        if (team.getPlayerCount() <= 1) {
            broadcastToTeam(team, "§cYour team has been dissolved.");
            teams.remove(team);
        }
    }

    public void broadcastToTeam(Team team, String message) {
        if (team != null) {
            for (int i = 0; i < team.getPlayerCount(); i++) {
                team.getPlayer(i).sendMessage(message);
            }
        }
    }

    public void broadcastPlayerJoin(Team team, String username) {
        broadcastToTeam(team, "§2" + username + " §ahas joined your team.");
    }

    public void broadcastPlayerLeave(Team team, String username) {
        broadcastToTeam(team, "§4" + username + " §chas left your team.");
    }
}
