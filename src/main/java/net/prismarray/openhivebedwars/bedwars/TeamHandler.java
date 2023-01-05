package net.prismarray.openhivebedwars.bedwars;

import com.google.common.collect.ImmutableList;
import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Mode;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeamHandler {

    private final ArrayList<Team> teams;
    private final Map<Player, ArrayList<Player>> invites;
    private final int maxTeamCount;

    public TeamHandler() {
        teams = new ArrayList<>();
        invites = new HashMap<>();

        switch(Game.getMode()) {
            case SOLO:
                maxTeamCount = 12;
                break;
            case DUOS:
                maxTeamCount = 8;
                break;
            case TEAMS:
                maxTeamCount = 4;
                break;
            default:
                maxTeamCount = 0;
                break;
        }
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public boolean isInTeam(Player player) {
        return !(getPlayerTeam(player) == null);
    }

    public boolean areTeammates(Player player1, Player player2) {
        return getPlayerTeam(player1).getColor() == getPlayerTeam(player2).getColor();
    }

    public Team getPlayerTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }

    public ArrayList<Player> getTeamlessPlayers() {
        ImmutableList<Player> currentPlayers =  ImmutableList.copyOf(Bukkit.getServer().getOnlinePlayers());
        ArrayList<Player> teamlessPlayers = new ArrayList<>();

        for (int i = 0; i < Bukkit.getServer().getOnlinePlayers().size(); i++) {
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

        switch(Game.getMode()) {
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
        if (inviter == null || invitee == null || Game.getMode() == Mode.SOLO) {
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

    public List<Player> getInvitingPlayers(Player invitee) {

        return invites.keySet().stream()
                .filter(p -> isInvitedBy(p, invitee))
                .collect(Collectors.toList());
    }

    public boolean removeInvite(Player inviter, Player invitee) {
        if (!invites.containsKey(inviter)) {
            return false;
        }
        return invites.get(inviter).remove(invitee);
    }

    public void assignAndMerge() {
        dissolveEmptyTeams();
        ArrayList<Player> teamless = getTeamlessPlayers();

        // Assign teamless to existing teams
        for (Team team : teams) {
            while (!team.isFull()) {
                if (!teamless.isEmpty()) {
                    team.addPlayer(teamless.get(0));
                    teamless.remove(0);
                }
            }
        }

        // Create new teams with remaining players
        Team newTeam;
        while (!teamless.isEmpty()) {
            newTeam(teamless.get(0)); // create team with first player in list
            teamless.remove(0);
            newTeam = teams.get(teams.size()-1);

            while (!newTeam.isFull()) {
                if (!teamless.isEmpty()) {
                    newTeam.addPlayer(teamless.get(0));
                    teamless.remove(0);
                } else {
                    break;
                }
            }
        }

        // Merge remaining two-player teams in teams mode
        if (OpenHiveBedwars.getBWConfig().mergeTeams() && Game.getMode() == Mode.TEAMS) {
            List<Team> mergeTeams = getTwoPlayerTeams();
            while (mergeTeams.size() > 1) {
                addToPlayer(mergeTeams.get(mergeTeams.size()-1).getPlayer(0), mergeTeams.get(0).getPlayer(0));
                addToPlayer(mergeTeams.get(mergeTeams.size()-1).getPlayer(1), mergeTeams.get(0).getPlayer(0));

                teams.remove(mergeTeams.get(mergeTeams.size()-1)); // Dissolve team

                mergeTeams.remove(mergeTeams.size()-1); // Remove dissolved and full team from list
                mergeTeams.remove(0);
            }
        }
    }

    public void colorize() {
        switch (Game.getMode()) {
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

    public void playerDisconnect(Player player) {
        Team team = getPlayerTeam(player);
        if (team == null) {
            return;
        }

        switch (Game.getStatus()) {
            case LOBBY:
                Broadcast.teamLeave(team, player);
                removePlayer(player);
                tryDissolution(team);
                break;
            case WARMUP:
            case INGAME:
                Broadcast.teamLeave(team, player);
                finalKill(player);
                break;
        }
    }

    public void finalKill(Player player) {
        Team team = getPlayerTeam(player);
        removePlayer(player);
        if (team.getPlayerCount() == 0) {
            killTeam(team);
        }
    }

    public void killTeam(Team team) {
        Broadcast.teamElimination(team.getColor());
        Game.clearBed(team.getColor());
        teams.remove(team);
        tryGameEnd();
    }

    public void tryDissolution(Team team) {
        if (team.getPlayerCount() <= 1) {
            Broadcast.toTeam(team, "§cYour team has been dissolved.");
            teams.remove(team);
        }
    }

    public void dissolveEmptyTeams() {
        teams.removeAll(
                teams.stream()
                        .filter(t -> t.getPlayerCount() <= 1)
                        .collect(Collectors.toList())
        );
    }

    public void tryGameEnd() {
        if (teams.size() == 1) {
            Game.concluded(teams.get(0).getColor());
        }
    }

    public List<Team> getTwoPlayerTeams() {
        return teams.stream()
                .filter(t -> t.getPlayerCount() == 2)
                .collect(Collectors.toList());
    }
}
