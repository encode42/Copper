package dev.encode42.encodedapi;

import org.bukkit.Bukkit;

public class Listener {
	/**
	 * Register event listeners
	 * @param listeners Listeners to register
	 */
	public static void register(org.bukkit.event.Listener ...listeners) {
		// Register the listeners
		for (org.bukkit.event.Listener listener : listeners) Bukkit.getServer().getPluginManager().registerEvents(listener, Util.plugin);
	}
}
