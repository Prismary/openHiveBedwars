package net.prismarray.openhivebedwars.bedwars;

import com.google.common.collect.ImmutableList;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Mode;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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

    public boolean areTeammates(Player player1, Player player2) {
        if (getPlayerTeam(player1).getColor() == getPlayerTeam(player2).getColor()) {
            return true;
        }
        return false;
    }

    public Team getPlayerTeam(Player player) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getPlayers().contains(player)) {
                return teams.get(i);
            }
        }
        return null;
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
        if (game.plugin.config.mergeTeams() && game.mode == Mode.TEAMS) {
            ArrayList<Team> mergeTeams = getTwoPlayerTeams();
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

    public void playerDisconnect(Player player) {
        Team team = getPlayerTeam(player);
        if (team == null) {
            return;
        }

        switch (game.status) {
            case LOBBY:
                Broadcast.playerLeave(team, player.getName());
                removePlayer(player);
                tryDissolution(team);
                break;
            case WARMUP:
            case INGAME:
                Broadcast.playerLeave(team, player.getName());
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
        game.clearBed(team.getColor());
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
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getPlayerCount() <= 1) {
                teams.remove(teams.get(i));
            }
        }
    }

    public void tryGameEnd() {
        if (teams.size() == 1) {
            game.concluded(teams.get(0).getColor());
        }
    }

    public ArrayList<Team> getTwoPlayerTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getPlayerCount() == 2) {
                teams.add(teams.get(i));
            }
        }
        return teams;
    }
}
