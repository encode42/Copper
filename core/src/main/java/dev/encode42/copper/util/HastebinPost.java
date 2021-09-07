package dev.encode42.copper.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

// https://gist.github.com/VitorBlog/b142abb5601ca1fe43edce5a134e0c40
public class HastebinPost {
    private String url = "https://hastebin.com/";
    private String contents;
    private boolean raw = true;
    private String response;

    /**
     * Get the URL utilized in the post request.
     * @return URL to post to
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get the contents to send to the Hastebin instance.
     * @return Contents to post
     */
    public String getContents() {
        return contents;
    }

    /**
     * Get whether the post should return a raw link after post rather than the pretty-print link.
     * @return Whether the link should return raw
     */
    public boolean isRaw() {
        return raw;
    }

    /**
     * Get the response of the latest post to the Hastebin instance.
     * @return Response from the last post
     */
    public String getResponse() {
        return response;
    }

    /**
     * Set the URL utilized in the post request.
     * @param url URL to post to
     */
    public HastebinPost setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Set the contents to send to the Hastebin instance.
     * @param contents Contents to post
     */
    public HastebinPost setContents(String contents) {
        this.contents = contents;
        return this;
    }

    /**
     * Return a raw link after post rather than the pretty-print link.
     * @param raw Whether the link should return raw
     */
    public HastebinPost setRaw(boolean raw) {
        this.raw = raw;
        return this;
    }

    /**
     * Post to the Hastebin instance using the build settings.
     * @return Uploaded link
     */
    public String post() {
        try {
            URL host = new URL(this.url);
            URL url = new URL(host + "documents");
            URLConnection connection = url.openConnection();

            connection.setRequestProperty("authority", host.getAuthority());
            connection.setRequestProperty("accept", "application/json, text/javascript, /; q=0.01");
            connection.setRequestProperty("x-requested-with", "XMLHttpRequest");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:91.0) Gecko/20100101 Firefox/91.0");
            connection.setRequestProperty("content-type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            OutputStream stream = connection.getOutputStream();
            stream.write(contents.getBytes());
            stream.flush();
            stream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.lines().collect(Collectors.joining("\n"));

            this.response = new URL(host + (raw ? "raw/" : "") + response.split("\"")[3]).toString();
            return this.response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
