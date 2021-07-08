package dev.encode42.encodedapi.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class IO {
	/**
	 * Create a file and its parent directories.
	 * @param location Location to create
	 * @param override Override an existing file
	 * @return Operation success
	 */
	public static boolean createFile(File location, boolean override) {
		// Check if the file exists
		if (!override && location.exists()) return false;

		try {
			// Create the parent directories
			File parentFile = location.getParentFile();
			if (parentFile != null) {
				parentFile.mkdirs();
			}

			// Create the file
			return location.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Create a file and its parent directories.
	 * @param location Location to create
	 * @return Operation success
	 */
	public static boolean createFile(File location) {
		return createFile(location, false);
	}

	/**
	 * Write a string of any size to a new or existing file.
	 * @param location Location to write to
	 * @param contents Contents of the file
	 * @param override Override an existing file
	 * @return Operation success
	 */
	public static boolean writeFile(File location, String contents, boolean override) {
		// Create the file
		createFile(location, override);

		// Write the contents
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(location));
			writer.write(contents);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Write a string of any size to a new or existing file.
	 * @param location Location to write to
	 * @param contents Contents of the file
	 * @return Operation success
	 */
	public static boolean writeFile(File location, String contents) {
		return writeFile(location, contents, false);
	}

	/**
	 * Write a string of any size to a new or existing file.
	 * @param location Location to write to
	 * @param contents Contents of the file
	 * @param override Override an existing file
	 * @return Operation success
	 */
	public static boolean writeFile(String location, String contents, boolean override) {
		File path = IO.toFile(location);
		return writeFile(path, contents, override);
	}

	/**
	 * Write a string of any size to a new or existing file.
	 * @param location Location to write to
	 * @param contents Contents of the file
	 * @return Operation success
	 */
	public static boolean writeFile(String location, String contents) {
		return writeFile(location, contents, false);
	}

	/**
	 * Convert a string location to a file
	 * @param location The location to convert
	 * @param root Use the server directory instead of plugin directory
	 * @return Location converted to a file
	 */
	public static File toFile(String location, Boolean root) {
		return new File(location.replaceAll("/\\\\", File.separator));
	}

	/**
	 * Convert a string location to a file
	 * @param location The location to convert
	 * @return The location converted to a file
	 */
	public static File toFile(String location) {
		return toFile(location, false);
	}

	/**
	 * Convert a string location to a path
	 * @param location The location to convert
	 * @param root Use the server directory instead of plugin directory
	 * @return Location converted to a path
	 */
	public static Path toPath(String location, Boolean root) {
		return toFile(location, root).toPath();
	}

	/**
	 * Convert a string location to a path
	 * @param location The location to convert
	 * @return Location converted to a path
	 */
	public static Path toPath(String location) {
		return toFile(location, false).toPath();
	}

	/**
	 * Get a resource from the program's JAR file
	 * @param resourceName The resource's path
	 * @return The requested resource
	 */
	public static Reader getResource(String resourceName) {
		InputStream resource = IO.class.getClassLoader().getResourceAsStream(resourceName);
		return resource == null ? null : new InputStreamReader(resource, StandardCharsets.UTF_8);
	}
}
