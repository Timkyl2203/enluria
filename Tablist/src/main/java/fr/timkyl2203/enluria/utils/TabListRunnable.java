package fr.timkyl2203.enluria.utils;

import java.util.Iterator;

import fr.timkyl2203.enluria.tablist.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabListRunnable
        extends BukkitRunnable
{
    public void run() {
        ConfigurationSection configurationSection = Tablist.getInstance().getConfig().getConfigurationSection("list_groups");

        for (String lg : configurationSection.getKeys(false)) {

            Iterator iterator = Bukkit.getOnlinePlayers().iterator(); if (iterator.hasNext()) { Player e = (Player)iterator.next();

                if ((Tablist.getInstance()).permission.playerInGroup(e.getPlayer(), lg))
                    TeamsTagManager.setNameTag(e.getPlayer(), lg, Tablist.getInstance().getConfig().getString("list_groups." + lg + ".prefix"), Tablist.getInstance().getConfig().getInt("list_groups." + lg + ".priority"));  }

        }
    }
}
