package dev.encode42.encodedapi;

import org.bukkit.Bukkit;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;

public class Listener {
	/**
	 * Register event listeners
	 * @param listeners Listeners to register
	 */
	public static void register(Object ...listeners) {
		// Register the listeners
		for (Object listener : listeners) Bukkit.getServer().getPluginManager().registerEvents(org.bukkit.event.Listener.class.cast(listener), Util.plugin);
	}

	/**
	 * Register event listeners recursively in a package
	 * @param path Path of listeners to register
	 * @param registrationClass Class interface to check for
	 * - Class must be in the same package as the path
	 */
	public static void recursiveRegister(String path, Class<? extends Annotation> registrationClass) {
		Reflections reflections = new Reflections(new String[]{path});
		for (Class<?> listener : reflections.getTypesAnnotatedWith(registrationClass)) {
			Bukkit.getServer().getPluginManager().registerEvents(org.bukkit.event.Listener.class.cast(listener), Util.plugin);
		};
	}
}
