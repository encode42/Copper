package dev.encode42.copper.io;

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
	 * Create a folder and its parent directories.
	 * @param location Location to create
	 * @param override Override an existing folder
	 * @return Operation success
	 */
	public static boolean createFolder(File location, boolean override) {
		// Check if the folder exists
		if (!override && location.exists()) return false;

		// Create the directories
		boolean created = location.mkdirs();

		// Delete folder contents
		if (override) {
			File[] files = location.listFiles();
			if (files != null) {
				for (File file : files) {
					file.delete();
				}
			}
		}

		return created;
	}

	/**
	 * Create a folder and its parent directories.
	 * @param location Location to create
	 * @return Operation success
	 */
	public static boolean createFolder(File location) {
		return createFolder(location, false);
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

	/**
	 * Get a file's extension type.
	 * @param file File to get extension from
	 * @return File extension type
	 */
	public static String getExtension(File file) {
		String fileName = file.getAbsoluteFile().getName();
		return fileName.substring(Math.max(fileName.indexOf(".") + 1, 0));
	}

	/**
	 * Check if a file has a certain extension.
	 * @param file File to check extension
	 * @param type Type to check for
	 * @return Whether the file type is the specified type
	 */
	public static boolean isType(File file, String type) {
		return getExtension(file).equals(type);
	}

	/**
	 * Get a file's relative directory from a root
	 * @param root File root
	 * @param file Target file
	 * @return String of the file's relative directory
	 */
	public static String getRelativeDirectory(File root, File file) {
		StringBuilder targetPath = new StringBuilder();
		File currentPath = file.getParentFile();

		try {
			while (!currentPath.getCanonicalPath().equals(root.getCanonicalPath())) {
				targetPath.insert(0, File.separator + currentPath.getName());
				currentPath = currentPath.getParentFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return targetPath.toString();
	}
}
