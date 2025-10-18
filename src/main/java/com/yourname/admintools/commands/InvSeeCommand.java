package com.yourname.admintools.commands;

import com.yourname.admintools.AdminTools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvSeeCommand implements CommandExecutor {
    
    private final AdminTools plugin;
    
    public InvSeeCommand(AdminTools plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭту команду могут использовать только игроки!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("admintools.invsee")) {
            player.sendMessage("§cУ вас нет разрешения на использование этой команды!");
            return true;
        }
        
        if (args.length == 0) {
            player.sendMessage("§cИспользование: /invsee <игрок>");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§cИгрок " + args[0] + " не найден!");
            return true;
        }
        
        Inventory targetInventory = Bukkit.createInventory(null, 45, "Инвентарь: " + target.getName());
        
        // Копируем основной инвентарь
        for (int i = 0; i < 36; i++) {
            targetInventory.setItem(i, target.getInventory().getItem(i));
        }
        
        // Копируем броню
        targetInventory.setItem(36, target.getInventory().getHelmet());
        targetInventory.setItem(37, target.getInventory().getChestplate());
        targetInventory.setItem(38, target.getInventory().getLeggings());
        targetInventory.setItem(39, target.getInventory().getBoots());
        
        // Копируем предмет в руке
        targetInventory.setItem(40, target.getInventory().getItemInMainHand());
        
        player.openInventory(targetInventory);
        player.sendMessage("§aОткрываю инвентарь игрока §e" + target.getName());
        
        return true;
    }
}
