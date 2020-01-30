package fr.timkyl2203.enluria.tablist;

import fr.timkyl2203.enluria.listeners.LoadListeners;
import fr.timkyl2203.enluria.utils.Packet;
import fr.timkyl2203.enluria.utils.Placeholders;
import fr.timkyl2203.enluria.utils.TabListRunnable;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Tablist extends JavaPlugin {
        private static Tablist instance;

        public Permission permission = null;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        setupPermissions();

        (new TabListRunnable()).runTaskTimerAsynchronously(this, 300L, 300L);
        Bukkit.getScheduler().runTaskTimer(this, () -> {

            for(Player p : Bukkit.getOnlinePlayers()){
                setPlayerTablistWithAutoMessages(p);
            }

        }, 0, 20);

        LoadListeners.onLoad();
    }



    public void onDisable() {}



    private void setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null)
            this.permission = permissionProvider.getProvider();
    }

    public static Tablist getInstance() { return instance; }
    public Permission getPermission() { return this.permission; }

    public void setPlayerTablistWithAutoMessages(Player p){

        Placeholders placeholders = new Placeholders();
        placeholders.init(p);

        List<String> header = getConfig().getStringList("edit.header");
        List<String> footer = getConfig().getStringList("edit.footer");

        int headerCurrent = 0, footerCurrent = 0;

        StringBuilder header_ = new StringBuilder();
        StringBuilder footer_ = new StringBuilder();

        for (String s : header) {
            headerCurrent++;
            StringBuilder sentance = new StringBuilder();
            int length = 0;
            for(String word : s.split(" ")) {
                length++;
                for (String placeholder : placeholders.getPlaceholders()) {
                    if (word.equalsIgnoreCase(placeholder)) {
                        word = placeholders.getValueFor(placeholder);
                    }
                }
                sentance.append(word).append(length == s.split(" ").length ? "" : " ");
            }
            header_.append(ChatColor.translateAlternateColorCodes('&', sentance.toString())).append(headerCurrent == header.size() ? "" : "\n");
        }
        for (String s : footer) {
            footerCurrent++;
            StringBuilder sentance = new StringBuilder();
            int length = 0;
            for(String word : s.split(" ")) {
                length++;
                for (String placeholder : placeholders.getPlaceholders()) {
                    if (word.equalsIgnoreCase(placeholder)) {
                        word = placeholders.getValueFor(placeholder);
                    }
                }
                sentance.append(word).append(length == s.split(" ").length ? "" : " ");
            }
            footer_.append(ChatColor.translateAlternateColorCodes('&', sentance.toString())).append(footerCurrent == footer.size() ? "" : "\n");
        }

        Packet.setPlayerList(p, header_.toString(), footer_.toString());
    }

}
