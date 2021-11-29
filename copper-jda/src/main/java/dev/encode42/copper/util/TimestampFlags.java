package dev.encode42.copper.util;

public enum TimestampFlags {
    /**
     * Short time format flag.
     * US Format: "h:m a"
     * Example: "11:40 PM"
     */
    SHORT_TIME("t"),

    /**
     * Long time format flag.
     * US Format: "h:m:s a"
     * Example: "11:40:31 PM"
     */
    LONG_TIME("T"),

    /**
     * Short date format flag.
     * US Format: "MM/d/u"
     * Example: "10/06/2021"
     */
    SHORT_DATE("d"),

    /**
     * Long date format flag.
     * US Format: "MMMM d u"
     * Example: "October 6 2021"
     */
    LONG_DATE("D"),

    /**
     * Short date and time format flag.
     * US Format: "MMMM d u h:m:s a"
     * Example: "October 6 2021 11:40:31 PM"
     */
    SHORT_DATE_TIME("f"),

    /**
     * Long date and time format flag.
     * US Format: "EE, MMMM, d, u h:m:s a"
     * Example: "Wednesday, October, 6, 2021 11:40:31 PM"
     */
    LONG_DATE_TIME("F"),

    /**
     * Relative time from current time.
     * Example: "in an hour"
     */
    RELATIVE("R");

    private final String format;

    TimestampFlags(String format) {
        this.format = format;
    }

    /**
     * @return The format the flag utilizes.
     */
    public String getFormat() {
        return this.format;
    }
}
