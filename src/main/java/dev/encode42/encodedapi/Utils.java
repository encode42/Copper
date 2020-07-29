package dev.encode42.encodedapi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.logging.Logger;

public class Utils {
    private static Plugin plugin;
    private static Logger log;

    public Utils(Plugin plugin) {
        Utils.plugin = plugin;
        Utils.log = plugin.getLogger();
    }

    // ## Translate color codes to chat-compatible colors
    public static String toChat(String message) {
        if (message == null) message = "null";
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    // ## Permission checking
    public static boolean checkPerms(CommandSender sender, Command command, String args) {
        // Console running command
        if (!(sender instanceof Player)) return true;

        // Player running command
        if (args != null) return sender.hasPermission(command.getName() + "." + args);
        else return sender.hasPermission(command.getName()) || sender.hasPermission(command.getName() + ".*");
    }

    // Overloading
    public static boolean checkPerms(CommandSender sender, Command command) {
        return checkPerms(sender, command, null);
    }

    // ## Send message to sender
    public static void sendMessage(CommandSender sender, String message, HashMap options) {
        // Defaults (null checking)
        options.putIfAbsent("message", "Message not defined.");
        options.putIfAbsent("severity", "info");
        options.putIfAbsent("placeholders", false);
        options.putIfAbsent("toChat", true);

        // Colors, placeholders, prefix
        if (options.get("prefix") != null && sender instanceof Player) message = options.get("prefix") + message;
        if ((Boolean) options.get("placeholders"))                  message = replacePlaceholders(message);
        if ((Boolean) options.get("toChat"))                        message = toChat(message);

        // Send the player/log the message
        if (sender instanceof Player) sender.sendMessage(message);
        else {
            switch (options.get("severity").toString()) {
                case "severe": log.severe(message);
                case "warn":   log.warning(message);
                case "info":   log.info(message);
            }
        }
    }

    // ## Placeholders (This is W.I.P.)
    public static String replacePlaceholders(String message, HashMap<String, String> customPlaceholders) {
        if (customPlaceholders != null) {
            for (HashMap.Entry<String, String> entry : customPlaceholders.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (key == null) key = "null";
                if (value == null) value = "null";

                message = message.replace(key, value);
            };
        }
        return message;
    }
    public static String replacePlaceholders(String message) {
        return replacePlaceholders(message, null);
    }

    // ## Players
    // Check if a player is online
    public static boolean isOnline(String player) {
        return plugin.getServer().getPlayer(player) != null;
    }

    // Convert object to player
    public static Player getPlayer(Object objectPlayer) {
        String player = String.valueOf(objectPlayer);

        if (isOnline(player)) return plugin.getServer().getPlayer(player);
        return null;
    }
}
