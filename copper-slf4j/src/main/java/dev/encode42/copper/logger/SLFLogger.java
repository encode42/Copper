package dev.encode42.copper.logger;

import org.slf4j.Logger;

import java.util.logging.Level;

public class SLFLogger extends OmniLogger {
    private static Logger slf;

    /**
     * Get the utilized SLF4J logger instance.
     * @return SLF4J instance
     */
    public static org.slf4j.Logger getSlf() {
        return slf;
    }

    /**
     * Set the utilized SLF4J logger instance.
     * @param slf SLF4J instance
     * @param primary Whether to set this as the primary logger
     */
    public static void setSlf(org.slf4j.Logger slf, boolean primary) {
        SLFLogger.slf = slf;

        if (primary) {
            setPrimary("slf");
        }
    }

    /**
     * Set the utilized SLF4J logger instance.
     * @param slf SLF4J instance
     */
    public static void setSlf(Logger slf) {
        setSlf(slf, false);
    }

    /**
     * Set the primary logger to an SLF4J logger instance.
     * @param slf SLF4J instance
     */
    public static void setPrimary(Logger slf) {
        setSlf(slf, true);
    }

    /**
     * Log with a level via the primary logger.
     * @param level Level to log at
     * @param message Message to log
     */
    public static void log(Level level, String message) {
        OmniLogger.log(level, message);

        if (primary == null) {
            return;
        }

        switch (primary) {
            case "slf" -> slfLog(level, message);
        }
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
            case "INFO" -> slf.info(message);
            case "WARNING" -> slf.warn(message);
            case "SEVERE" -> slf.error(message);
        }
    }
}
