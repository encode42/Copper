package dev.encode42.copper.helper;

import net.dv8tion.jda.api.JDA;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class JDAListenerHelper extends ListenerHelper {
    private final JDA jda;
    public JDAListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass, JDA jda) {
        super(listenerPackage, listenerClass);
        this.jda = jda;
    }

    private void run(Class<?> clazz) throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        jda.addEventListener(clazz.getConstructor().newInstance());
    }
}
