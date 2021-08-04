package dev.encode42.copper.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OmniLogger {
    private static Logger logger;
    private static org.slf4j.Logger slf;
    private static String primary;

    /**
     * Get the utilized Java logger instance.
     * @return Logger instance
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Get the utilized SLF4J logger instance.
     * @return SLF4J instance
     */
    public static org.slf4j.Logger getSlf() {
        return slf;
    }

    /**
     * Get the current primary logger string.
     * @return Primary logger
     */
    public static String getPrimary() {
        return primary;
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
     * Set the utilized SLF4J logger instance.
     * @param slf SLF4J instance
     * @param primary Whether to set this as the primary logger
     */
    public static void setSlf(org.slf4j.Logger slf, boolean primary) {
        OmniLogger.slf = slf;

        if (primary) {
            setPrimary("slf");
        }
    }

    /**
     * Set the utilized SLF4J logger instance.
     * @param slf SLF4J instance
     */
    public static void setSlf(org.slf4j.Logger slf) {
        setSlf(slf, false);
    }

    /**
     * Set the primary logger to a Java logger instance.
     * @param logger Java logger instance
     */
    public static void setPrimary(Logger logger) {
        setLogger(logger, true);
    }

    /**
     * Set the primary logger to an SLF4J logger instance.
     * @param slf SLF4J instance
     */
    public static void setPrimary(org.slf4j.Logger slf) {
        setSlf(slf, true);
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
    public static void log(Level level, String message) {
        switch (primary) {
            case "java":
                javaLog(level, message);
                break;

            case "slf":
                slfLog(level, message);
                break;
        }
    }

    /**
     * Informational log via the primary logger.
     * @param message Message to log
     */
    public static void info(String message) {
        log(Level.INFO, message);
    }

    /**
     * Warning log via the primary logger.
     * @param message Message to log
     */
    public static void warn(String message) {
        log(Level.WARNING, message);
    }

    /**
     * Severe log via the primary logger.
     * @param message Message to log
     */
    public static void error(String message) {
        log(Level.SEVERE, message);
    }

    /**
     * Method used to log with levels and Java.
     * @param level Level to log at
     * @param message Message to log
     */
    private static void javaLog(Level level, String message) {
        if (logger == null) {
            return;
        }

        logger.log(level, message);
    }

    /**
     * Method used to log with levels and SLF4J.
     * @param level Level to log at
     * @param message Message to log
     */
    private static void slfLog(Level level, String message) {
        if (slf == null) {
            return;
        }

        switch (level.getName()) {
            case "INFO":
                slf.info(message);
                break;

            case "WARNING":
                slf.warn(message);
                break;

            case "SEVERE":
                slf.error(message);
                break;
        }
    }
}
