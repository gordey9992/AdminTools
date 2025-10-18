package com.yourname.admintools.commands;

import com.yourname.admintools.AdminTools;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {
    
    private final AdminTools plugin;
    
    public NickCommand(AdminTools plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭту команду могут использовать только игроки!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("admintools.nick")) {
            player.sendMessage("§cУ вас нет разрешения на использование этой команды!");
            return true;
        }
        
        if (args.length == 0) {
            player.sendMessage("§cИспользование: /nick <ник> [цвет]");
            player.sendMessage("§eЦвета: &agreen, &bblue, &cred, &6gold, &dpink, &eyellow, &fwhite");
            return true;
        }
        
        String nick = args[0];
        
        if (nick.length() > 16) {
            player.sendMessage("§cНикнейм слишком длинный! Максимум 16 символов");
            return true;
        }
        
        String color = "&f"; // белый по умолчанию
        if (args.length > 1) {
            color = getColorCode(args[1]);
        }
        
        // Преобразуем цветовые коды
        String coloredNick = ChatColor.translateAlternateColorCodes('&', color + nick);
        
        player.setDisplayName(coloredNick);
        player.setPlayerListName(coloredNick);
        player.sendMessage("§aВаш никнейм изменен на: " + coloredNick);
        
        return true;
    }
    
    private String getColorCode(String colorName) {
        switch (colorName.toLowerCase()) {
            case "green": case "зеленый": return "&a";
            case "blue": case "синий": return "&b";
            case "red": case "красный": return "&c";
            case "gold": case "золотой": return "&6";
            case "pink": case "розовый": return "&d";
            case "yellow": case "желтый": return "&e";
            case "white": case "белый": return "&f";
            default: return "&f";
        }
    }
}
