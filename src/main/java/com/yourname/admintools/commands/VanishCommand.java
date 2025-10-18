package com.yourname.admintools.commands;

import com.yourname.admintools.AdminTools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    
    private final AdminTools plugin;
    
    public VanishCommand(AdminTools plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭту команду могут использовать только игроки!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("admintools.vanish")) {
            player.sendMessage("§cУ вас нет разрешения на использование этой команды!");
            return true;
        }
        
        boolean vanished = isVanished(player);
        
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online != player && !online.hasPermission("admintools.seevanished")) {
                if (vanished) {
                    online.showPlayer(plugin, player);
                } else {
                    online.hidePlayer(plugin, player);
                }
            }
        }
        
        if (vanished) {
            player.sendMessage("§aРежим невидимки §cвыключен");
        } else {
            player.sendMessage("§aРежим невидимки §aвключен");
            player.sendMessage("§7Вы видимы для администраторов");
        }
        
        return true;
    }
    
    private boolean isVanished(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.canSee(player)) return true;
        }
        return false;
    }
}
