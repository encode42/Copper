package dev.encode42.copper.logger;

public class StackTrace extends Exception {
    public StackTrace(String message) {
        super(message);
    }

    public void printStackTrace() {
        printStackTrace(System.out);
    }
}
