package fr.timkyl2203.enluria.listeners;


import fr.timkyl2203.enluria.tablist.Tablist;

public class LoadListeners
{
    public static void onLoad() { Tablist.getInstance().getServer().getPluginManager().registerEvents(new PlayerJoinListener(), Tablist.getInstance()); }
}
