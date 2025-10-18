package com.yourname.admintools.commands;

import com.yourname.admintools.AdminTools;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class ParticleCommand implements CommandExecutor {
    
    private final AdminTools plugin;
    
    public ParticleCommand(AdminTools plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭту команду могут использовать только игроки!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("admintools.particles")) {
            player.sendMessage("§cУ вас нет разрешения на использование этой команды!");
            return true;
        }
        
        if (args.length == 0) {
            showParticleMenu(player);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "list":
            case "список":
                showParticleList(player);
                break;
            case "stop":
            case "стоп":
                stopParticles(player);
                break;
            case "heart":
            case "сердце":
                startHeartParticles(player);
                break;
            case "flame":
            case "огонь":
                startFlameParticles(player);
                break;
            case "rainbow":
            case "радуга":
                startRainbowParticles(player);
                break;
            case "halo":
            case "нимб":
                startHaloParticles(player);
                break;
            case "magic":
            case "магия":
                startMagicParticles(player);
                break;
            case "water":
            case "вода":
                startWaterParticles(player);
                break;
            case "lava":
            case "лава":
                startLavaParticles(player);
                break;
            default:
                player.sendMessage("§cНеизвестный тип эффекта! Используйте §e/particles список");
        }
        
        return true;
    }
    
    private void showParticleMenu(Player player) {
        player.sendMessage("§6§lСистема Эффектов Частиц");
        player.sendMessage("§e/particles список §7- Показать все эффекты");
        player.sendMessage("§e/particles стоп §7- Остановить эффекты");
        player.sendMessage("§e/particles <тип> §7- Запустить эффект");
    }
    
    private void showParticleList(Player player) {
        player.sendMessage("§6§lДоступные Эффекты Частиц:");
        player.sendMessage("§aсердце §7- Сердца вокруг вас");
        player.sendMessage("§aогонь §7- Огненный след");
        player.sendMessage("§aрадуга §7- Радужная спираль");
        player.sendMessage("§aнимб §7- Ангельский нимб");
        player.sendMessage("§aмагия §7- Магический вихрь");
        player.sendMessage("§aвода §7- Водная аура");
        player.sendMessage("§aлава §7- Капли лавы");
    }
    
    private void stopParticles(Player player) {
        Bukkit.getScheduler().cancelTasks(plugin);
        player.sendMessage("§aВсе эффекты частиц остановлены!");
    }
    
    private void startHeartParticles(Player player) {
        new BukkitRunnable() {
            public void run() {
                Location loc = player.getLocation().add(0, 2, 0);
                player.getWorld().spawnParticle(Particle.HEART, loc, 5, 0.5, 0.5, 0.5, 0.1);
            }
        }.runTaskTimer(plugin, 0L, 10L);
        player.sendMessage("§aЭффект частиц §eсердце §aвключен!");
    }
    
    private void startFlameParticles(Player player) {
        new BukkitRunnable() {
            public void run() {
                Location loc = player.getLocation();
                player.getWorld().spawnParticle(Particle.FLAME, loc, 10, 0.2, 0.2, 0.2, 0.05);
            }
        }.runTaskTimer(plugin, 0L, 5L);
        player.sendMessage("§aЭффект частиц §eогонь §aвключен!");
    }
    
    private void startRainbowParticles(Player player) {
        new BukkitRunnable() {
            double angle = 0;
            public void run() {
                Location loc = player.getLocation();
                for (int i = 0; i < 6; i++) {
                    double x = Math.cos(angle + i) * 1.5;
                    double z = Math.sin(angle + i) * 1.5;
                    Location particleLoc = loc.clone().add(x, 2, z);
                    player.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, 
                        new Particle.DustOptions(Color.fromRGB(
                            (int)(Math.sin(angle + i) * 127 + 128),
                            (int)(Math.sin(angle + i + 2) * 127 + 128),
                            (int)(Math.sin(angle + i + 4) * 127 + 128)
                        ), 1));
                }
                angle += 0.3;
            }
        }.runTaskTimer(plugin, 0L, 2L);
        player.sendMessage("§aЭффект частиц §eрадуга §aвключен!");
    }
    
    private void startHaloParticles(Player player) {
        new BukkitRunnable() {
            double angle = 0;
            public void run() {
                Location loc = player.getLocation();
                for (int i = 0; i < 12; i++) {
                    double x = Math.cos(angle + i * 0.5) * 0.7;
                    double z = Math.sin(angle + i * 0.5) * 0.7;
                    Location particleLoc = loc.clone().add(x, 2.3, z);
                    player.getWorld().spawnParticle(Particle.END_ROD, particleLoc, 1);
                }
                angle += 0.2;
            }
        }.runTaskTimer(plugin, 0L, 5L);
        player.sendMessage("§aЭффект частиц §eнимб §aвключен!");
    }
    
    private void startMagicParticles(Player player) {
        new BukkitRunnable() {
            double angle = 0;
            public void run() {
                Location loc = player.getLocation();
                for (int i = 0; i < 8; i++) {
                    double x = Math.cos(angle + i) * 1.2;
                    double z = Math.sin(angle + i) * 1.2;
                    double y = Math.sin(angle * 2 + i) * 0.5 + 1.5;
                    Location particleLoc = loc.clone().add(x, y, z);
                    player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, particleLoc, 2);
                }
                angle += 0.2;
            }
        }.runTaskTimer(plugin, 0L, 3L);
        player.sendMessage("§aЭффект частиц §eмагия §aвключен!");
    }
    
    private void startWaterParticles(Player player) {
        new BukkitRunnable() {
            public void run() {
                Location loc = player.getLocation().add(0, 1, 0);
                player.getWorld().spawnParticle(Particle.WATER_SPLASH, loc, 15, 1, 1, 1, 0.1);
                player.getWorld().spawnParticle(Particle.WATER_DROP, loc, 10, 1, 1, 1, 0.05);
            }
        }.runTaskTimer(plugin, 0L, 8L);
        player.sendMessage("§aЭффект частиц §eвода §aвключен!");
    }
    
    private void startLavaParticles(Player player) {
        new BukkitRunnable() {
            public void run() {
                Location loc = player.getLocation().add(0, 1, 0);
                player.getWorld().spawnParticle(Particle.LAVA, loc, 5, 0.5, 0.5, 0.5, 0.1);
                player.getWorld().spawnParticle(Particle.FLAME, loc, 3, 0.3, 0.3, 0.3, 0.02);
            }
        }.runTaskTimer(plugin, 0L, 15L);
        player.sendMessage("§aЭффект частиц §eлава §aвключен!");
    }
}
