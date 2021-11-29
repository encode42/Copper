package dev.encode42.copper.helper;

import cloud.commandframework.annotations.AnnotationParser;
import org.checkerframework.checker.units.qual.C;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class CommandListenerHelper extends ListenerHelper {
    private final AnnotationParser<C> annotationParser;

    public CommandListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass, AnnotationParser<C> annotationParser, boolean register) {
        super(listenerPackage, listenerClass, register);
        this.annotationParser = annotationParser;
    }

    public CommandListenerHelper(String listenerPackage, Class<? extends Annotation> listenerClass, AnnotationParser<C> annotationParser) {
        this(listenerPackage, listenerClass, annotationParser, false);
    }

    @Override
    protected void run(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        annotationParser.parse(clazz.getConstructor().newInstance());
    }
}
