package com.yourname.admintools;

import com.yourname.admintools.commands.*;
import com.yourname.admintools.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminTools extends JavaPlugin {
    
    private static AdminTools instance;
    private ConfigManager configManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Создаем папку плагина если её нет
        saveDefaultConfig();
        
        // Инициализация менеджеров
        this.configManager = new ConfigManager(this);
        configManager.loadMessages();
        
        // Регистрация команд
        getCommand("particles").setExecutor(new ParticleCommand(this));
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("nick").setExecutor(new NickCommand(this));
        getCommand("invsee").setExecutor(new InvSeeCommand(this));
        getCommand("whois").setExecutor(new WhoIsCommand(this));
        getCommand("admin").setExecutor(new AdminCommand(this));
        
        // Красивое сообщение при включении
        sendWelcomeMessage();
        
        getLogger().info("AdminTools успешно включен!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("AdminTools выключен!");
    }
    
    private void sendWelcomeMessage() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&6╔══════════════════════════════════╗"));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&6║          &e&lAdminTools &6v1.0.0         ║"));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&6║    &aПлагин успешно загружен!     ║"));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&6║   &7Разработано для вашего сервера  ║"));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&6╚══════════════════════════════════╝"));
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&7Доступные команды:"));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&e/particles &7- Система эффектов частиц"));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&e/vanish &7- Режим невидимки"));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&e/nick &7- Изменение никнейма"));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
            "&e/admin &7- Административные команды"));
        getServer().getConsoleSender().sendMessage("");
    }
    
    public static AdminTools getInstance() {
        return instance;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
}
