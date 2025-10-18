package com.yourname.admintools.commands;

import com.yourname.admintools.AdminTools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WhoIsCommand implements CommandExecutor {
    
    private final AdminTools plugin;
    
    public WhoIsCommand(AdminTools plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cИспользование: /whois <игрок>");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cИгрок " + args[0] + " не найден или не в сети!");
            return true;
        }
        
        Location loc = target.getLocation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        
        sender.sendMessage("§6§lИнформация об игроке: §e" + target.getName());
        sender.sendMessage("§7UUID: §f" + target.getUniqueId());
        sender.sendMessage("§7Здоровье: §c" + String.format("%.1f", target.getHealth()) + "§7/§c" + String.format("%.1f", target.getMaxHealth()));
        sender.sendMessage("§7Еда: §6" + target.getFoodLevel() + "§7, Насыщение: §6" + String.format("%.1f", target.getSaturation()));
        sender.sendMessage("§7Координаты: §a" + loc.getBlockX() + "§7, §a" + loc.getBlockY() + "§7, §a" + loc.getBlockZ());
        sender.sendMessage("§7Мир: §b" + loc.getWorld().getName());
        sender.sendMessage("§7Режим игры: §e" + getGamemodeRussian(target.getGameMode()));
        sender.sendMessage("§7Уровень: §b" + target.getLevel() + "§7, Опыт: §b" + String.format("%.1f", target.getExp() * 100) + "%");
        sender.sendMessage("§7Пинг: §a" + target.getPing() + "мс");
        sender.sendMessage("§7IP: §f" + target.getAddress().getAddress().getHostAddress());
        sender.sendMessage("§7Первый вход: §f" + dateFormat.format(new Date(target.getFirstPlayed())));
        
        return true;
    }
    
    private String getGamemodeRussian(org.bukkit.GameMode gamemode) {
        switch (gamemode) {
            case SURVIVAL: return "Выживание";
            case CREATIVE: return "Творчество";
            case ADVENTURE: return "Приключение";
            case SPECTATOR: return "Наблюдатель";
            default: return "Неизвестно";
        }
    }
}
