package dev.encode42.encodedapi;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class Message {
	private static String prefix = "";
	public static void setPrefix(String prefix) {
		Message.prefix = prefix;
	}

	private static final MiniMessage miniMessage = MiniMessage.markdown();
	private static BukkitAudiences audience = null;

	/**
	 * Create the message audience
	 * @param plugin The plugin instance
	 */
	public static void create(Plugin plugin) {
		audience = BukkitAudiences.create(plugin);
	}

	/**
	 * Convert strings to an Adventure component
	 * - Legacy color codes
	 * - Adventure tags
	 * - MiniMessage markdown
	 * @param string Message to convert
	 * @return The converted component
	 */
	private static Component convert(String string) {
		// Convert MiniMessage syntax
		return miniMessage.parse(ChatColor.translateAlternateColorCodes('&', string));
	}

	/**
	 * Send a message to a player
	 * @param sender Player to send the message to
	 * @param string Message to send
	 * @param raw Exclude all prefixes
	 * @param placeholders Map of placeholders
	 */
	public static void send(CommandSender sender, String string, boolean raw, Map<String, String> placeholders) {
		if (audience == null) return;

		// Add prefix and convert to a component
		Component parsed = convert((raw ? "" : prefix) + string);

		// Send the message
		audience.sender(sender).sendMessage(parsed);
	}

	/**
	 * Send a message to a player
	 * Overload that defaults raw to false
	 * @see Message#send(CommandSender, String, boolean, Map)
	 * @param sender Player to send the message to
	 * @param string Message to send
	 * @param placeholders Map of placeholders
	 */
	public static void send(CommandSender sender, String string, HashMap<String, String> placeholders) {
		send(sender, string, false, placeholders);
	}

	/**
	 * Send a message to a player
	 * Overload that defaults placeholders to none
	 * @see Message#send(CommandSender, String, boolean, Map)
	 * @param sender Player to send the message to
	 * @param string Message to send
	 * @param raw Map of placeholders
	 */
	public static void send(CommandSender sender, String string, boolean raw) {
		send(sender, string, raw, null);
	}

	/**
	 * Send a message to a player
	 * Overload that defaults raw to false and placeholders to none
	 * @see Message#send(CommandSender, String, boolean, Map)
	 * @param sender Player to send the message to
	 * @param string Message to send
	 */
	public static void send(CommandSender sender, String string) {
		send(sender, string, false, null);
	}

	/**
	 * Send a message to all players with a permission
	 * @param string Message to send
	 * @param permission Permission to filter by
	 * @param raw Don't add the prefix
	 */
	public static void notify(String string, String permission, boolean raw) {
		if (audience == null) return;

		// Add prefix and convert to a component
		Component parsed = convert((raw ? "" : prefix) + string);

		// Send the notification
		audience.filter(commandSender -> commandSender.hasPermission(permission)).sendMessage(parsed);
	}

	/**
	 * Send a message to all players with a permission
	 * Overload that defaults raw to false
	 * @see Message#notify(String, String, boolean)
	 * @param string Message to send
	 * @param permission Permission to filter by
	 */
	public static void notify(String string, String permission) {
		notify(string, permission, false);
	}

	public static void error(String string) {
		if (audience == null) return;

		// Convert to a component
		Component parsed = convert(string);

		// Send the error
		audience.filter(commandSender -> commandSender instanceof ConsoleCommandSender).sendMessage(parsed);
	}
}
