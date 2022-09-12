package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {

    TeamColor color;
    int size;
    ArrayList<Player> players;
    boolean bedIntact;

    public Team(int size) {
        this.color = null;
        this.size = size;

        players = new ArrayList<>();

        bedIntact = true;
    }

    public TeamColor getColor() {
        return color;
    }

    public void setColor(TeamColor color) {
        this.color = color;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public boolean isFull() {
        return players.size() >= size;
    }

    public boolean addPlayer(Player player) {
        // Early return if team is full
        if (players.size() >= size) {
            return false;
        }

        players.add(player);
        return true;
    }

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }


    public boolean hasBed() {
        return bedIntact;
    }

    public void breakBed() {
        bedIntact = false;
    }
}
