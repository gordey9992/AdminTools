package com.yourname.admintools.commands;

import com.yourname.admintools.AdminTools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminCommand implements CommandExecutor {
    
    private final AdminTools plugin;
    
    public AdminCommand(AdminTools plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            showAdminHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "fly":
            case "полет":
                toggleFly(sender, args);
                break;
            case "heal":
            case "лечение":
                healPlayer(sender, args);
                break;
            case "god":
            case "бессмертие":
                toggleGod(sender, args);
                break;
            case "feed":
            case "еда":
                feedPlayer(sender, args);
                break;
            case "repair":
            case "починка":
                repairItems(sender, args);
                break;
            default:
                sender.sendMessage("§cНеизвестная подкоманда!");
                showAdminHelp(sender);
        }
        
        return true;
    }
    
    private void showAdminHelp(CommandSender sender) {
        sender.sendMessage("§6§lАдминистративные Команды:");
        sender.sendMessage("§e/admin полет [игрок] §7- Включить/выключить полет");
        sender.sendMessage("§e/admin лечение [игрок] §7- Вылечить игрока");
        sender.sendMessage("§e/admin бессмертие [игрок] §7- Режим бога");
        sender.sendMessage("§e/admin еда [игрок] §7- Насытить игрока");
        sender.sendMessage("§e/admin починка [игрок] §7- Починить предметы");
    }
    
    private void toggleFly(CommandSender sender, String[] args) {
        Player target = getTarget(sender, args);
        if (target == null) return;
        
        target.setAllowFlight(!target.getAllowFlight());
        if (target.getAllowFlight()) {
            sender.sendMessage("§aПолет включен для §e" + target.getName());
            target.sendMessage("§aПолет включен!");
        } else {
            sender.sendMessage("§aПолет выключен для §e" + target.getName());
            target.sendMessage("§aПолет выключен!");
        }
    }
    
    private void healPlayer(CommandSender sender, String[] args) {
        Player target = getTarget(sender, args);
        if (target == null) return;
        
        target.setHealth(target.getMaxHealth());
        target.setFoodLevel(20);
        target.setSaturation(10);
        target.setFireTicks(0);
        
        sender.sendMessage("§aИгрок §e" + target.getName() + " §aисцелен");
        target.sendMessage("§aВы были исцелены!");
    }
    
    private void toggleGod(CommandSender sender, String[] args) {
        Player target = getTarget(sender, args);
        if (target == null) return;
        
        boolean godMode = target.isInvulnerable();
        target.setInvulnerable(!godMode);
        
        if (!godMode) {
            sender.sendMessage("§aРежим бога включен для §e" + target.getName());
            target.sendMessage("§aРежим бога включен!");
        } else {
            sender.sendMessage("§aРежим бога выключен для §e" + target.getName());
            target.sendMessage("§aРежим бога выключен!");
        }
    }
    
    private void feedPlayer(CommandSender sender, String[] args) {
        Player target = getTarget(sender, args);
        if (target == null) return;
        
        target.setFoodLevel(20);
        target.setSaturation(20);
        
        sender.sendMessage("§aИгрок §e" + target.getName() + " §aнасыщен");
        target.sendMessage("§aВы были насыщены!");
    }
    
    private void repairItems(CommandSender sender, String[] args) {
        Player target = getTarget(sender, args);
        if (target == null) return;
        
        boolean repaired = false;
        
        // Чиним предметы в инвентаре
        for (ItemStack item : target.getInventory().getContents()) {
            if (item != null && item.getItemMeta() instanceof Damageable) {
                Damageable meta = (Damageable) item.getItemMeta();
                if (meta.hasDamage()) {
                    meta.setDamage(0);
                    item.setItemMeta((ItemMeta) meta);
                    repaired = true;
                }
            }
        }
        
        // Чиним броню
        for (ItemStack armor : target.getInventory().getArmorContents()) {
            if (armor != null && armor.getItemMeta() instanceof Damageable) {
                Damageable meta = (Damageable) armor.getItemMeta();
                if (meta.hasDamage()) {
                    meta.setDamage(0);
                    armor.setItemMeta((ItemMeta) meta);
                    repaired = true;
                }
            }
        }
        
        if (repaired) {
            sender.sendMessage("§aПредметы починены для §e" + target.getName());
            target.sendMessage("§aВаши предметы были починены!");
        } else {
            sender.sendMessage("§cНет предметов для починки у игрока §e" + target.getName());
        }
    }
    
    private Player getTarget(CommandSender sender, String[] args) {
        if (args.length > 1) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cИгрок не найден!");
                return null;
            }
            return target;
        } else if (sender instanceof Player) {
            return (Player) sender;
        } else {
            sender.sendMessage("§cВы должны указать игрока!");
            return null;
        }
    }
}
