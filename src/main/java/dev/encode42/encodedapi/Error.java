package dev.encode42.encodedapi;

import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.File;
import java.net.URI;

public class Error {
	/**
	 * File read/write error
	 * @param file File that caused the error
	 * @param e Exception stack trace
	 */
	public static void fileReadWriteError(String file, Exception e) {
		Message.error("Error writing config file " + file + ": " + ExceptionUtils.getStackTrace(e));
	}

	/**
	 * File read/write error
	 * @param file File that caused the error
	 * @param e Exception stack trace
	 */
	public static void fileReadWriteError(File file, Exception e) {
		fileReadWriteError(file.getName(), e);
	}

	/**
	 * Reader close error
	 * @param e Exception stack trace
	 */
	public static void readerCloseError(Exception e) {
		Message.error("Error closing reader: " + ExceptionUtils.getStackTrace(e));
	}

	/**
	 * Configuration load error
	 * @param e Exception stack trace
	 */
	public static void configurationLoadError(Exception e) {
		Message.error("Error loading configuration file: " + ExceptionUtils.getStackTrace(e));
	}

	/**
	 * Malformed URL error
	 * @param uri URI that caused the error
	 */
	public static void malformedURLError(URI uri) {
		Message.error("Malformed URL: " + uri + ", parsed scheme: " + uri.getScheme());
	}

	/**
	 * Server contact error
	 * @param uri URI that caused the error
	 * @param e Exception stack trace
	 */
	public static void serverContactError(URI uri, Exception e) {
		Message.error("Error contacting server " + uri + ": " + ExceptionUtils.getStackTrace(e));
	}

	/**
	 * Server contact error
	 * @param url URL that caused the error
	 * @param e Exception stack trace
	 */
	public static void serverContactError(String url, Exception e) {
		serverContactError(URI.create(url), e);
	}
}
