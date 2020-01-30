package fr.timkyl2203.enluria.listeners;

import java.util.Iterator;
import java.util.Objects;

import fr.timkyl2203.enluria.tablist.Tablist;
import fr.timkyl2203.enluria.utils.Packet;
import fr.timkyl2203.enluria.utils.TeamsTagManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener
        implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        ConfigurationSection configurationSection = Tablist.getInstance().getConfig().getConfigurationSection("list_groups");

        if (configurationSection != null) {
            for (String lg : configurationSection.getKeys(false)) {

                if ((Tablist.getInstance()).permission.playerInGroup(e.getPlayer(), lg)) {
                    TeamsTagManager.setNameTag(e.getPlayer(), lg, Tablist.getInstance().getConfig().getString("list_groups." + lg + ".prefix"), Tablist.getInstance().getConfig().getInt("list_groups." + lg + ".priority"));
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        ConfigurationSection configurationSection = Tablist.getInstance().getConfig().getConfigurationSection("list_groups");


        if (configurationSection != null) {
            for (String lg : configurationSection.getKeys(false)) {

                Iterator iterator = Bukkit.getOnlinePlayers().iterator(); if (iterator.hasNext()) { Player e = (Player)iterator.next();

                    if ((Tablist.getInstance()).permission.playerInGroup(Objects.requireNonNull(e.getPlayer()), lg))
                        TeamsTagManager.setNameTag(e.getPlayer(), lg, Tablist.getInstance().getConfig().getString("list_groups." + lg + ".prefix"), Tablist.getInstance().getConfig().getInt("list_groups." + lg + ".priority"));  }

            }
        }
    }
}
