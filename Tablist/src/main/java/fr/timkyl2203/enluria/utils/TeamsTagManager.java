package fr.timkyl2203.enluria.utils;

import java.util.Collection;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamsTagManager
{
    private String prefix;
    private String suffix;
    private Team team;
    public static Scoreboard scoreboard;

    public TeamsTagManager(String name, String prefix, String suffix, int priority, Scoreboard current) throws Exception {
        this.prefix = prefix;
        this.suffix = suffix;
        String teammate = priority + name;
        this.team = current.getTeam(teammate);
        if (this.team == null) {
            this.team = current.registerNewTeam(teammate);
        }
        scoreboard = current;
        this.team.setCanSeeFriendlyInvisibles(false);
        this.team.setAllowFriendlyFire(false);


        int prefixLength = 0;
        int suffixLength = 0;
        if (prefix != null) {
            prefixLength = prefix.length();
        }
        if (suffix != null) {
            suffixLength = suffix.length();
        }
        if (prefixLength + suffixLength >= 32) {
            throw new Exception("prefix and suffix lenghts are greater than 16");
        }
        if (suffix != null) {
            this.team.setSuffix(suffix);
        }
        if (prefix != null) {
            this.team.setPrefix(prefix);
        }
    }


    public TeamsTagManager(String name, String prefix, String suffix, int priority) throws Exception { this(name, prefix, suffix, priority, Bukkit.getScoreboardManager().getMainScoreboard()); }



    public void set(Player player) {
        this.team.addPlayer(player);
        player.setScoreboard(scoreboard);
    }



    public void remove(Player player) { this.team.removePlayer(player); }



    public void resetTagUtils(UUID uuid) { remove(Bukkit.getPlayer(uuid)); }


    public void setAll(Collection<Player> players) {
        for (Player player : players) {
            set(player);
        }
    }

    public void setAll(Player[] players) {
        Player[] arrayOfPlayer;
        int j = players.length;
        arrayOfPlayer = players;
        for (int i = 0; i < j; i++) {
            Player player = arrayOfPlayer[i];
            set(player);
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        this.team.setPrefix(this.prefix);
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
        this.team.setSuffix(this.suffix);
    }


    public String getPrefix() { return this.prefix; }



    public String getSuffix() { return this.suffix; }



    public Team getTeam() { return this.team; }



    public void removeTeam() { this.team.unregister(); }



    public Scoreboard getScoreboard() { return scoreboard; }


    public static void setNameTag(Player player, String name, String prefix, int priority) {
        try {
            TeamsTagManager golemaplayer = new TeamsTagManager(name, prefix, null, priority);
            golemaplayer.set(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
