package dev.encode42.copper.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Constants {
	/**
	 * An empty InputStream.
	 */
	public static final InputStream EMPTY_STREAM = new InputStream() {
		@Override
		public int read() {
			return -1;
		}
	};

	/**
	 * An empty File.
	 */
	public static final File EMPTY_FILE = new File("");

	/**
	 * An empty string list.
	 */
	public static final List<String> EMPTY_STRING_LIST = new ArrayList<>();
}
