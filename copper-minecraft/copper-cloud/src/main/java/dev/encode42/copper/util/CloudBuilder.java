package dev.encode42.copper.util;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.extra.confirmation.CommandConfirmationManager;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import cloud.commandframework.paper.PaperCommandManager;
import dev.encode42.copper.logger.OmniLogger;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CloudBuilder {
    private final TextColor destructiveColor = TextColor.color(0xF03E3E);
    private final TextColor secondaryColor = TextColor.color(0xDEE2E6);
    private final Component defaultConfirmationPrompt = Component.text("This command requires confirmation. Run '/<command> confirm' to confirm.").color(destructiveColor);
    private Component confirmationPrompt;
    private Component noConfirmationsPrompt = Component.text("There are no pending commands.").color(destructiveColor);
    private String pluginName;
    private BukkitCommandManager<CommandSender> manager;
    private AnnotationParser<CommandSender> annotationParser;
    private BukkitAudiences bukkitAudiences;
    private MinecraftHelp<CommandSender> minecraftHelp;
    private CommandConfirmationManager<CommandSender> confirmationManager;

    /**
     * Builder and helper for the cloud command framework.
     * @param plugin Plugin connected to the builder
     */
    public CloudBuilder(Plugin plugin) {
        this.pluginName = plugin.getName();

        // Create the plugin manager
        try {
            this.manager = new PaperCommandManager<CommandSender>(
                    plugin,
                    AsynchronousCommandExecutionCoordinator
                            .<CommandSender>newBuilder()
                            .build(),
                    Function.identity(),
                    Function.identity());
        } catch (Exception e) {
            OmniLogger.error("Failed to initialize command manager.");
            OmniLogger.error(e);

            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        // Enable features for capable versions
        if (this.manager.queryCapability(CloudBukkitCapabilities.BRIGADIER)) {
            this.manager.registerBrigadier();
        }

        if (this.manager.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            ((PaperCommandManager<CommandSender>) this.manager).registerAsynchronousCompletions();
        }

        // Create the annotation parser
        this.annotationParser = new AnnotationParser<>(
                this.manager,
                CommandSender.class,
                p -> CommandMeta.simple()
                        .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
                        .build()
        );

        // Create the audience
        this.bukkitAudiences = BukkitAudiences.create(plugin);
    }

    /**
     * Set the name utilized in messages.
     * @param pluginName Name of the plugin
     */
    public CloudBuilder setPluginName(String pluginName) {
        this.pluginName = pluginName;

        // Create the default confirmation prompt
        if (this.confirmationPrompt == null) {
            this.confirmationPrompt = Component.text("This command requires confirmation. Run '/")
                    .append(Component.text(pluginName).color(secondaryColor))
                    .append(Component.text("' to confirm."))
                    .color(destructiveColor);
        }

        return this;
    }

    /**
     * Set the command confirmation prompt utilized in command confirmations.
     */
    public CloudBuilder setConfirmationPrompt(Component confirmationPrompt) {
        this.confirmationPrompt = confirmationPrompt;

        return this;
    }

    /**
     * Set the prompt utilized when there are no commands to confirm.
     */
    public CloudBuilder setNoConfirmationsPrompt(Component noConfirmationsPrompt) {
        this.noConfirmationsPrompt = noConfirmationsPrompt;

        return this;
    }

    /**
     * Create an automatically generated help command.
     */
    public CloudBuilder createHelp() {
        this.minecraftHelp = new MinecraftHelp<>(
                this.pluginName + " help",
                bukkitAudiences::sender,
                manager
        );

        return this;
    }

    /**
     * Create the command confirmation manager.
     * @param timeout Time to wait until expiry
     * @param timeUnit Unit of time for timeout
     */
    public CloudBuilder createConfirmationManager(long timeout, TimeUnit timeUnit) {
        this.confirmationManager = new CommandConfirmationManager<>(
                timeout,
                timeUnit,
                context -> context.getCommandContext()
                        .getSender()
                        .sendMessage(this.getConfirmationPrompt()),
                sender -> sender.sendMessage(this.noConfirmationsPrompt)
        );

        this.confirmationManager.registerConfirmationProcessor(this.manager);

        return this;
    }

    /**
     * Create the command confirmation manager.
     * @param timeout Time to wait until expiry
     */
    public CloudBuilder createConfirmationManager(long timeout) {
        return createConfirmationManager(timeout, TimeUnit.SECONDS);
    }

    /**
     * Create the command confirmation manager.
     */
    public CloudBuilder createConfirmationManager() {
        return createConfirmationManager(30);
    }

    /**
     * Get the built command manager.
     */
    public BukkitCommandManager<CommandSender> getManager() {
        return manager;
    }

    /**
     * Get the built annotation parser.
     */
    public AnnotationParser<CommandSender> getAnnotationParser() {
        return annotationParser;
    }

    /**
     * Get the built Adventure Bukkit audience.
     */
    public BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }

    /**
     * Get the confirmation prompt.
     */
    public Component getConfirmationPrompt() {
        return this.confirmationPrompt != null ? this.confirmationPrompt : this.defaultConfirmationPrompt;
    }

    /**
     * Get the no confirmations prompt.
     */
    public Component getNoConfirmationsPrompt() {
        return this.noConfirmationsPrompt;
    }
}
