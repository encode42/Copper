package dev.encode42.copper.helper;

import dev.encode42.copper.logger.OmniLogger;
import net.dv8tion.jda.api.JDA;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class JDAListenerHelper extends ListenerHelper {
    private JDA jda;

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

    /*
    private static JDA jda;

    public static void register(String listenerPackage, Class<? extends Annotation> listenerClass, JDA jda) {
        register(listenerPackage, listenerClass);
        JDAListenerHelper.jda = jda;
    }

    protected static void run(Class<?> clazz) throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println(clazz.getSimpleName());
        jda.addEventListener(clazz.getConstructor().newInstance());
    }
    */
}
