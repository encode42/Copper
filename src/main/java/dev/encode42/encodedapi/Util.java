package dev.encode42.encodedapi;

import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;

public class Util {
	static Plugin plugin;

	/**
	 * Set the plugin instance
	 * @param plugin The plugin instance
	 */
	public static void setPlugin(Plugin plugin) {
		Util.plugin = plugin;

		// Create the message audience
		Message.create(plugin);
	}

	/**
	 * Get a resource from the plugin's JAR
	 * @param filename The resource's path
	 * @return The requested resource
	 */
	public static Reader getResource(String filename) {
		InputStream file = plugin.getResource(filename + ".yml");
		return new InputStreamReader(file == null ? emptyStream : file, StandardCharsets.UTF_8);
	}

	/**
	 * An empty InputStream
	 */
	public static final InputStream emptyStream = new InputStream() {
		@Override
		public int read() {
			return -1;
		}
	};

	/**
	 * Request the contents of a link
	 * @param url The URL to send the request to
	 * @return The received contents of the URL
	 */
	public static String makeRequest(String url) {
		URI uri = URI.create(url);

		// Validate the URL
		String scheme = uri.getScheme();
		if (!isEqual(scheme != null ? scheme : "", "https", "http")) {
			Error.malformedURLError(uri);
			return "";
		}

		// Create the client and request
		HttpClient client = HttpClient.newBuilder()
				.followRedirects(HttpClient.Redirect.NORMAL)
				.build();
		HttpRequest request = HttpRequest.newBuilder()
				.timeout(Duration.ofSeconds(30))
				.uri(uri)
				.build();

		// Send the request
		HttpResponse<String> response;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() != 200) throw new IOException("Returned status is not 200 (OK)");
		} catch (IOException|InterruptedException e) {
			Error.serverContactError(uri, e);
			return "";
		}

		return response.body();
	}

	/**
	 * Write a string to a file
	 * @param location Location to write to
	 * @param contents Contents of the file
	 * @param override Override an existing file
	 * @return Operation success
	 */
	public static boolean writeFile(File location, String contents, boolean override) {
		// Check if the file exists
		if (!override && location.exists()) return false;

		// Create the parent directories
		location.getParentFile().mkdirs();

		// Write the contents
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(location, StandardCharsets.UTF_8));
			writer.write(contents);
			writer.close();
		} catch (IOException e) {
			Error.fileReadWriteError(location, e);
			return false;
		}

		return true;
	}

	/**
	 * Write a string to a file
	 * Overload that defaults override to false
	 * @see Util#writeFile(File, String, boolean)
	 * @param location Location to write to
	 * @param contents Contents of the file
	 * @return Operation success
	 */
	public static boolean writeFile(File location, String contents) {
		return writeFile(location, contents, false);
	}

	/**
	 * Write a string to a file
	 * Parses the location argument to a path
	 * @see Util#writeFile(File, String, boolean)
	 * @param location Location to write to
	 * @param contents Contents of the file
	 * @param override Override an existing file
	 * @return Operation success
	 */
	public static boolean writeFile(String location, String contents, boolean override) {
		File path = Util.toFile(location);

		return writeFile(path, contents, override);
	}

	/**
	 * Write a string to a file
	 * Parses the location argument to a path
	 * Overload that defaults override to false
	 * @see Util#writeFile(File, String, boolean)
	 * @param location Location to write to
	 * @param contents Contents of the file
	 * @return Operation success
	 */
	public static boolean writeFile(String location, String contents) {
		return writeFile(location, contents, false);
	}

	/**
	 * Convert a location to a file
	 * @param location The location to convert
	 * @param root Use the server directory instead of plugin directory
	 * @return Location converted to a file
	 */
	public static File toFile(String location, Boolean root) {
		File folder = Util.plugin.getDataFolder();
		File path = root ? new File("") : folder;

		return new File(path + File.separator + location.replaceAll("/\\\\", File.separator));
	}

	/**
	 * Convert a location to a file
	 * Overload that defaults root to false
	 * @see Util#toFile(String, Boolean)
	 * @param location The location to convert
	 * @return The location converted to a file
	 */
	public static File toFile(String location) {
		return toFile(location, false);
	}

	/**
	 * Convert a location to a path
	 * @param location The location to convert
	 * @param root Use the server directory instead of plugin directory
	 * @return Location converted to a path
	 */
	public static Path toPath(String location, Boolean root) {
		return toFile(location, root).toPath();
	}

	/**
	 * Convert a location to a path
	 * Overload that defaults root to false
	 * @see Util#toPath(String, Boolean)
	 * @param location The location to convert
	 * @return Location converted to a path
	 */
	public static Path toPath(String location) {
		return toFile(location, false).toPath();
	}

	/**
	 * Check variable equality with more than one comparison
	 * @param object Object to check equality for
	 * @param comparisons Comparisons to make
	 * @return Object is equal to one or more comparisons
	 */
	public static boolean isEqual(Object object, Object ...comparisons) {
		// Get the instance for specific comparisons
		String instance = object.getClass().getSimpleName();

		// Loop through each comparison
		boolean valid = false;
		for (Object o : comparisons) {
			if ((instance.equals("String") && object.equals(o)) || object == o) {
				// It's valid, break the loop
				valid = true;
				break;
			}
		}

		return valid;
	}
}