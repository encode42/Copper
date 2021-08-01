package dev.encode42.copper.util;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;

public class Constants {
	/**
	 * An empty InputStream.
	 */
	public static final InputStream emptyStream = new InputStream() {
		@Override
		public int read() {
			return -1;
		}
	};

	/**
	 * An empty File.
	 */
	public static final File emptyFile = new File("");
}
