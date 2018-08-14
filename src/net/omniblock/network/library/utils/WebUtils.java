package net.omniblock.network.library.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Deprecated
public class WebUtils {

	public static String readUrl(String urlString) throws Exception {

		BufferedReader reader = null;

		try {

			URL url = new URL(urlString);

			URLConnection urlc = url.openConnection();
			urlc.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			urlc.connect();

			reader = new BufferedReader(new InputStreamReader(urlc.getInputStream()));

			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];

			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();

		} finally {

			if (reader != null)
				reader.close();

		}
	}

}
