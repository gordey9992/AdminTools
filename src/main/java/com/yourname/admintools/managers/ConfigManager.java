package com.yourname.admintools.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    
    private final JavaPlugin plugin;
    private FileConfiguration messagesConfig;
    private File messagesFile;
    private Map<String, String> messages;
    
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.messages = new HashMap<>();
    }
    
    public void loadMessages() {
        // Создаем messages.yml если его нет
        saveDefaultMessages();
        
        // Загружаем сообщения
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        
        // Загружаем все сообщения в память
        loadAllMessages();
        
        plugin.getLogger().info("Сообщения успешно загружены!");
    }
    
    private void saveDefaultMessages() {
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
            plugin.getLogger().info("Файл messages.yml создан!");
        }
    }
    
    private void loadAllMessages() {
        messages.clear();
        
        // Загружаем общие сообщения
        addMessage("no-permission");
        addMessage("player-only");
        addMessage("player-not-found");
        addMessage("usage");
        addMessage("unknown-command");
        
        // Загружаем сообщения частиц
        addMessage("particles.menu.title");
        addMessage("particles.started");
        addMessage("particles.stopped");
        addMessage("particles.unknown");
        
        // Загружаем сообщения невидимки
        addMessage("vanish.enabled");
        addMessage("vanish.disabled");
        addMessage("vanish.visible-to");
        
        // Загружаем сообщения никнеймов
        addMessage("nick.usage");
        addMessage("nick.colors");
        addMessage("nick.changed");
        addMessage("nick.reset");
        addMessage("nick.too-long");
        
        // Загружаем сообщения просмотра инвентаря
        addMessage("invsee.opening");
        addMessage("invsee.no-inventory");
        
        // Загружаем сообщения информации об игроке
        addMessage("whois.title");
        addMessage("whois.uuid");
        addMessage("whois.health");
        addMessage("whois.food");
        addMessage("whois.location");
        addMessage("whois.world");
        addMessage("whois.gamemode");
        addMessage("whois.ip");
        addMessage("whois.first-played");
        addMessage("whois.ping");
        
        // Загружаем административные сообщения
        addMessage("admin.help.title");
        addMessage("admin.fly.enabled");
        addMessage("admin.fly.disabled");
        addMessage("admin.heal.healed");
        addMessage("admin.god.enabled");
        addMessage("admin.god.disabled");
        addMessage("admin.feed.fed");
        addMessage("admin.repair.repaired");
        addMessage("admin.repair.no-items");
    }
    
    private void addMessage(String path) {
        String message = messagesConfig.getString(path);
        if (message == null) {
            plugin.getLogger().warning("Сообщение не найдено: " + path);
            messages.put(path, "&cСообщение не найдено: " + path);
        } else {
            messages.put(path, ChatColor.translateAlternateColorCodes('&', message));
        }
    }
    
    public String getMessage(String path, Map<String, String> placeholders) {
        String message = messages.getOrDefault(path, path);
        
        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        
        return message;
    }
    
    public String getMessage(String path, String placeholder, String value) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put(placeholder, value);
        return getMessage(path, placeholders);
    }
    
    public String getMessage(String path) {
        return messages.getOrDefault(path, path);
    }
    
    public void reloadMessages() {
        loadMessages();
    }
}
