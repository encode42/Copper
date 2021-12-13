package dev.encode42.copper.helper;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class BukkitListenerHelper extends ListenerHelper {
    Plugin plugin;

    public BukkitListenerHelper(Plugin plugin, String listenerPackage, Class<? extends Annotation> listenerClass, boolean register) {
        super(listenerPackage, listenerClass, false);
        this.plugin = plugin;

        if (register) {
            this.register();
        }
    }

    public BukkitListenerHelper(Plugin plugin, String listenerPackage, Class<? extends Annotation> listenerClass) {
        this(plugin, listenerPackage, listenerClass, false);
    }

    @Override
    protected void run(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (Listener.class.isAssignableFrom(clazz)) {
            plugin.getServer().getPluginManager().registerEvents((Listener) clazz.getConstructor().newInstance(), plugin);
        }
    }
}
