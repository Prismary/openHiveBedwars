package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.config.MapConfig;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.entity.Player;

import java.util.*;

public class MapVoting {
    private final Game game;

    private final Map<String, MapConfig> mapConfigs;
    private final ArrayList<String> pool;
    private final Map<String, Integer> voteCounts; // Tracks the vote count for each map
    private final Map<UUID, String> playerVotes; // Tracks what player voted for which map

    public MapVoting(Game game) {
        this.game = game;
        this.mapConfigs = game.plugin.mapManager.getMapConfigs();
        this.pool = new ArrayList<>();
        this.voteCounts = new HashMap<>();
        this.playerVotes = new HashMap<>();

        // Get map pool
        loadMapPool();
    }


    public void onPlayerJoin(Player player) {
        sendMapPool(player);
    }

    public void vote(Player player, int index) {

        if (index < 1 || index > pool.size()) { // Check whether index exists
            Broadcast.toPlayer(player, "§cThe specified index does not exist!");
            return;
        }

        String previousVote = playerVotes.get(player.getUniqueId()); // Get previous vote

        // Check whether player has already voted for same map
        if (previousVote != null && previousVote.equals(pool.get(index - 1))) {
            Broadcast.toPlayer(player, "§cYou have already voted for this map!");
            return;
        }

        String mapID = pool.get(index - 1);

        if (previousVote != null) { // Remove previous vote if existent
            voteCounts.put(previousVote, voteCounts.get(previousVote) - 1);
        }
        voteCounts.put(mapID, voteCounts.get(mapID) + 1); // Add new vote
        playerVotes.put(player.getUniqueId(), mapID); // Update player vote record

        if (!mapID.equals("_random")) { // Notify player
            Broadcast.toPlayer(player, "§aYou voted for §f" + mapConfigs.get(mapID).getMapDisplayName() + "§a! §7[" + voteCounts.get(mapID) + " Total]");
        } else {
            Broadcast.toPlayer(player, "§aYou voted for §fRandom Map§a! §7[" + voteCounts.get(mapID) + " Total]");
        }
    }

    public MapConfig conclude() {
        String winnerID = getMostVoted();

        if (winnerID.equals("_random")) {
            winnerID = pickRandom();
        }

        Broadcast.broadcast("§3Voting has ended! §bThe map §f" + game.plugin.mapManager.getMapConfig(winnerID).getMapDisplayName() + " §bhas won!");
        return game.plugin.mapManager.getMapConfig(winnerID);
    }


    private void loadMapPool() {
        Set<String> mapIDs = mapConfigs.keySet();

        pool.addAll(mapIDs); // Populate ordered pool and shuffle
        Collections.shuffle(pool);

        while (true) { // Remove all entries after 5
            try {
                pool.remove(5);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        // Add random to pool
        pool.add("_random");

        for (String id : pool) { // Populate vote count
            voteCounts.put(id, 0);
        }
    }

    private void sendMapPool(Player player) {
        Broadcast.toPlayer(player, "§e§lVote for a map! §7Use §f/v # §7or click.");

        for (int i = 0; i < pool.size()-1; i++) {
            String voteColor = "§7";
            if (pool.get(i).equals(getMostVoted())) {
                voteColor = "§a";
            }
            String voteIndex = String.valueOf(i + 1);
            String displayName = mapConfigs.get(pool.get(i)).getMapDisplayName();
            String voteCount = String.valueOf(voteCounts.get(pool.get(i)));

            sendVoteOption(player, voteIndex, "§6", displayName, voteCount, voteColor);
        }

        // Send random map option
        String rdmVoteColor = "§7";
        if (getMostVoted().equals("_random")) {
            rdmVoteColor = "§a";
        }
        sendVoteOption(
                player,
                String.valueOf(pool.size()),
                "§c",
                "Random Map",
                String.valueOf(voteCounts.get("_random")),
                rdmVoteColor
                );
    }

    private void sendVoteOption(Player player, String voteIndex, String nameColor, String displayName, String voteCount, String voteColor) {
        Broadcast.toPlayer(player,
                "§7§l" + voteIndex + ". "+ nameColor + displayName
                        + " " + voteColor + "[§f" + voteCount + voteColor + " Votes]");
    }

    private String getMostVoted() {
        int mostVotes = 0;
        String winner = pool.get(0);

        for (String id : pool) {
            if (voteCounts.get(id) > mostVotes) {
                mostVotes = voteCounts.get(id);
                winner = id;
            }
        }

        return winner;
    }

    private String pickRandom() {
        int index = (new Random()).nextInt(mapConfigs.keySet().size());
        return (String) mapConfigs.keySet().toArray()[index];
    }
}
