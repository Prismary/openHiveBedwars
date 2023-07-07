package net.prismarray.openhivebedwars.bedwars.stats;

public class LocalPlayerStats {

    public int tokens;
    public int points;
    public int kills;
    public int finalKills;
    public int deaths;
    public int bedsDestroyed;
    public int teamsEliminated;
    public int gamesPlayed;
    public int victories;
    public int winStreak;

    public LocalPlayerStats(int tokens, int points, int kills, int finalKills, int deaths, int bedsDestroyed,
                            int teamsEliminated, int gamesPlayed, int victories, int winStreak) {
        this.tokens = tokens;
        this.points = points;
        this.kills = kills;
        this.finalKills = finalKills;
        this.deaths = deaths;
        this.bedsDestroyed = bedsDestroyed;
        this.teamsEliminated = teamsEliminated;
        this.gamesPlayed = gamesPlayed;
        this.victories = victories;
        this.winStreak = winStreak;
    }

    public LocalPlayerStats() {
        this.tokens = 0;
        this.points = 0;
        this.kills = 0;
        this.finalKills = 0;
        this.deaths = 0;
        this.bedsDestroyed = 0;
        this.teamsEliminated = 0;
        this.gamesPlayed = 0;
        this.victories = 0;
        this.winStreak = 0;
    }
}
