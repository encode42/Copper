package dev.encode42.copper.helper;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ListenerHelper {
    /**
     * Registers all listeners annotated with the Listener interface.
     * @author YouHaveTrouble
     */
    public static void register(String listenerPackage, Class<? extends Annotation> listenerClass) {
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
    private static void run(Class<?> clazz) throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        clazz.getConstructor().newInstance();
    }
}
