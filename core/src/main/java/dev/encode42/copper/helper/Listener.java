package dev.encode42.copper.helper;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Listener {
    private final String listenerPackage;
    private final Class<? extends Annotation> listenerClass;

    /**
     * Helper class to register multiple annotated classes.
     * @param listenerPackage Package to scan for classes
     * @param listenerClass Annotation class to check
     */
    public Listener(String listenerPackage, Class<? extends Annotation> listenerClass) {
        this.listenerPackage = listenerPackage;
        this.listenerClass = listenerClass;
    }

    /**
     * Registers all listeners annotated with the Listener interface.
     * @author YouHaveTrouble
     */
    public void register() {
        // Find all listener packages
        Reflections reflections = new Reflections((Object) new String[]{listenerPackage});
        Set<Class<?>> listeners = reflections.getTypesAnnotatedWith(listenerClass);

        // Iterate through the package
        for (Class<?> listener : listeners) {
            try {
                run(listener);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Run the registration method
     * @param clazz Class to be registered
     */
    private void run(Class<?> clazz) throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        clazz.getConstructor().newInstance();
    }
}
