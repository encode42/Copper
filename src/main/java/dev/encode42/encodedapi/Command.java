package dev.encode42.encodedapi;

import cloud.commandframework.CommandTree;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.BukkitCommandMetaBuilder;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.function.Function;
import java.util.logging.Logger;

public class Command {
	private static BukkitCommandManager<CommandSender> manager;
	private static AnnotationParser<CommandSender> annotationParser;

	/**
	 * Create the command manager and parse commands
	 * @param plugin The plugin instance
	 * @param commands Commands to parse
	 */
	public static void register(Plugin plugin, Object ...commands) {
		Logger log = plugin.getLogger();

		// Coordinator and mapper
		Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> coordinator = AsynchronousCommandExecutionCoordinator.<CommandSender>newBuilder().build();
		Function<CommandSender, CommandSender> mapper = Function.identity();

		try {
			manager = new PaperCommandManager<>(plugin, coordinator, mapper, mapper);
		} catch (Exception e) {
			log.severe("An error occurred while initializing the command framework. " + e);
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}

		// Version and software specific features
		if (manager.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) ((PaperCommandManager<CommandSender>) manager).registerAsynchronousCompletions();
		if (manager.queryCapability(CloudBukkitCapabilities.BRIGADIER)) manager.registerBrigadier();

		// Annotation parser
		annotationParser = new AnnotationParser<>(manager,
				CommandSender.class,
				p -> BukkitCommandMetaBuilder.builder()
						.withDescription(p.get(StandardParameters.DESCRIPTION, "No description provided."))
						.build());

		// Parse the commands
		for (Object c : commands) annotationParser.parse(c);
	}
}
