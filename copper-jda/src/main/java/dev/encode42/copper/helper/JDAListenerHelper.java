package dev.encode42.copper.helper;

import net.dv8tion.jda.api.JDA;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class JDAListenerHelper extends ListenerHelper {
    private final JDA jda;

    public JDAListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass, JDA jda, boolean register) {
        super(listenerPackage, listenerClass, false);
        this.jda = jda;

        if (register) {
            this.register();
        }
    }

    public JDAListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass, JDA jda) {
        this(listenerPackage, listenerClass, jda, false);
    }

    @Override
    protected void run(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        jda.addEventListener(clazz.getConstructor().newInstance());
    }
}
