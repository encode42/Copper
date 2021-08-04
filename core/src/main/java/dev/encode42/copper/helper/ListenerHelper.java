package dev.encode42.copper.helper;

import dev.encode42.copper.logger.OmniLogger;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public abstract class ListenerHelper extends AbstractListenerHelper {
    public ListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass, boolean register) {
        super(listenerPackage, listenerClass, register);
    }

    public ListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass) {
        this(listenerPackage, listenerClass, false);
    }

    protected void run(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        clazz.getConstructor().newInstance();
    }
}

abstract class AbstractListenerHelper {
    final String listenerPackage;
    final Class<? extends Annotation> listenerClass;
    final Set<Class<?>> listeners = new HashSet<>();

    /**
     * Registers all listeners annotated with the Listener interface.
     * @author YouHaveTrouble
     */
    public AbstractListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass, boolean register) {
        this.listenerPackage = listenerPackage;
        this.listenerClass = listenerClass;

        if (register) {
            this.register();
        }
    }

    public void register() {
        OmniLogger.info("Registering listeners...");

        // Find all listener packages
        Reflections reflections = new Reflections((Object) new String[]{listenerPackage});
        Set<Class<?>> listeners = reflections.getTypesAnnotatedWith(listenerClass);

        // Iterate through the package
        for (Class<?> listener : listeners) {
            try {
                this.run(listener);
                listeners.add(listener);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        OmniLogger.info(String.format("Listener registration done! Registered a total of %s listeners!", listeners.size()));
    }

    protected abstract void run(Class<?> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    public Set<Class<?>> getListeners() {
        return this.listeners;
    }
}
