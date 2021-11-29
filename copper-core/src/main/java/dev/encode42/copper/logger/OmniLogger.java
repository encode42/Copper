package dev.encode42.copper.logger;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OmniLogger {
    protected static Logger logger;
    protected static String printStreamFormat = "[%s] %s";
    protected static PrintStream printStream;
    protected static PrintStream errorStream;
    protected static String primary;

    /**
     * Get the utilized Java logger instance.
     * @return Logger instance
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Get the current primary logger string.
     * @return Primary logger
     */
    public static String getPrimary() {
        return primary;
    }

    /**
     * Set the utilized output stream
     * @param logger PrintStream instance
     * @param primary Whether to set this as the primary logger
     */
    public static void setLogger(PrintStream logger, boolean primary) {
        OmniLogger.printStream = logger;

        if (primary) {
            setPrimary("printStream");
        }
    }

    /**
     * Set the utilized Java logger instance.
     * @param logger PrintStream instance
     */
    public static void setLogger(PrintStream logger) {
        setLogger(logger, false);
    }

    /**
     * Set the error stream utilized by the PrintStream logger.
     * Utilizes the default PrintStream logger if not set.
     * @param errorStream PrintStream instance
     */
    public static void setErrorStream(PrintStream errorStream) {
        OmniLogger.errorStream = errorStream;
    }

    /**
     * Set the utilized Java logger instance.
     * @param logger Java logger instance
     * @param primary Whether to set this as the primary logger
     */
    public static void setLogger(Logger logger, boolean primary) {
        OmniLogger.logger = logger;

        if (primary) {
            setPrimary("java");
        }
    }

    /**
     * Set the utilized Java logger instance.
     * @param logger Java logger instance
     */
    public static void setLogger(Logger logger) {
        setLogger(logger, false);
    }

    /**
     * Set the primary logger to a Java logger instance.
     * @param logger Java logger instance
     */
    public static void setPrimary(Logger logger) {
        setLogger(logger, true);
    }

    /**
     * Set the primary logger string.
     * @param primary Primary logger string.
     */
    public static void setPrimary(String primary) {
        OmniLogger.primary = primary;
    }

    /**
     * Log with a level via the primary logger.
     * @param level Level to log at
     * @param message Message to log
     */
    public static void log(Level level, Object message) {
        if (primary == null) {
            return;
        }

        switch (primary) {
            case "java" -> javaLog(level, message);
            case "printStream" -> printStreamLog(level, message);
        }
    }

    /**
     * Informational log via the primary logger.
     * @param message Message to log
     */
    public static void info(Object message) {
        log(Level.INFO, message);
    }

    /**
     * Warning log via the primary logger.
     * @param message Message to log
     */
    public static void warn(Object message) {
        log(Level.WARNING, message);
    }

    /**
     * Severe log via the primary logger.
     * @param message Message to log
     */
    public static void error(Object message) {
        log(Level.SEVERE, message);
    }

    /**
     * Create a stack trace via the primary logger.
     * @param level Level to log at
     * @param message Message to log
     */
    public static void trace(Level level, Object message) {
        PrintStream stream;
        switch (level.getName()) {
            case "SEVERE" -> stream = System.err;
            default -> stream = System.out;
        }

        new StackTrace(String.valueOf(message)).printStackTrace(stream);
    }

    /**
     * Create a stack trace via the primary logger.
     * @param message Message to log
     */
    public static void trace(Object message) {
        trace(Level.INFO, message);
    }

    /**
     * Create a stack trace via the primary logger.
     */
    public static void trace() {
        trace(Level.INFO, "");
    }

    /**
     * Method used to log with levels and Java.
     * @param level Level to log at
     * @param message Message to log
     */
    private static void javaLog(Level level, Object message) {
        if (logger == null) {
            return;
        }

        logger.log(level, String.valueOf(message));
    }

    /**
     * Method used to log with levels and PrintStreams.
     * @param level Level to log at
     * @param message Message to log
     */
    private static void printStreamLog(Level level, Object message) {
        if (printStream == null) {
            return;
        }

        String levelName = level.getName();
        switch (levelName) {
            case "INFO", "WARNING" -> printStream.printf(printStreamFormat + "%n", levelName, message);
            case "SEVERE" -> {
                if (errorStream != null) {
                    errorStream.printf(printStreamFormat + "%n", levelName, message);
                    return;
                }

                printStream.printf(printStreamFormat + "%n", levelName, message);
            }
        }
    }
}
