package dev.encode42.copper.helper;

import cloud.commandframework.annotations.AnnotationParser;
import org.bukkit.command.CommandSender;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class CommandListenerHelper extends ListenerHelper {
    private final AnnotationParser<CommandSender> annotationParser;

    public CommandListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass, AnnotationParser<CommandSender> annotationParser, boolean register) {
        super(listenerPackage, listenerClass, false);
        this.annotationParser = annotationParser;

        if (register) {
            this.register();
        }
    }

    public CommandListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass, AnnotationParser<CommandSender> annotationParser) {
        this(listenerPackage, listenerClass, annotationParser, false);
    }

    @Override
    protected void run(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        annotationParser.parse(clazz.getConstructor().newInstance());
    }
}
