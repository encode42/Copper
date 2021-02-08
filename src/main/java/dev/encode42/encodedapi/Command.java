package dev.encode42.encodedapi;

import cloud.commandframework.CommandTree;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class Command {
	private static BukkitCommandManager<CommandSender> manager;
	private static AnnotationParser<CommandSender> annotationParser;

	private static BukkitCommandManager<CommandSender> getManager() {
		return manager;
	}
	public static AnnotationParser<CommandSender> getAnnotationParser() {
		return annotationParser;
	}

	/**
	 * Create the command manager
	 * @param plugin The plugin instance
	 */
	public static void create(Plugin plugin) {
		// Coordinator and mapper
		Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> coordinator = AsynchronousCommandExecutionCoordinator.<CommandSender>newBuilder().build();
		Function<CommandSender, CommandSender> mapper = Function.identity();

		try {
			manager = new PaperCommandManager<>(plugin, coordinator, mapper, mapper);
		} catch (Exception e) {
			plugin.getLogger().severe("An error occurred while initializing the command framework. " + e);
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}

		// Version and software specific features
		if (manager.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) ((PaperCommandManager<CommandSender>) manager).registerAsynchronousCompletions();
		if (manager.queryCapability(CloudBukkitCapabilities.BRIGADIER)) manager.registerBrigadier();

		// Annotation parser
		annotationParser = new AnnotationParser<>(manager, CommandSender.class, p -> SimpleCommandMeta.empty());
	}

	/**
	 * Register and parse commands
	 * @param commands Commands to parse
	 */
	public static void register(Object ...commands) {
		// Parse the commands
		for (Object c : commands) annotationParser.parse(c);
	}

	/**
	 * Register and parse commands recursively in a package
	 * @param path Path of commands to parse
	 * @param registrationClass Class interface to check for
	 * - Class must be in the same package as the path
	 */
	public static void recursiveRegister(String path, Class<? extends Annotation> registrationClass) {
		Reflections reflections = new Reflections(new String[]{path});
		for (Class<?> command : reflections.getTypesAnnotatedWith(registrationClass)) {
			try {
				annotationParser.parse(command.getConstructor().newInstance());
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				Error.commandParseError(command.getName(), e);
			}
		};
	}
}
