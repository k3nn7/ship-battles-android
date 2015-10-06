package net.lalik.shipbattles.sdk2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpApi implements Api {
    private static final String API_HOST = "46.101.215.179:8080";

    @Override
    public Response doRequest(Request request) {
        try {
            HttpURLConnection connection = createConnection(request);
            connection.connect();

            String body = readBody(connection.getInputStream());
            String sessionToken = connection.getHeaderField("X-AuthToken");

            return new Response(body, sessionToken);
        } catch (IOException e) {
            return new Response(e.getClass().toString(), "foo");
        }
    }

    private HttpURLConnection createConnection(Request request) throws IOException {
        URL url = new URL(String.format(
                "http://%s/api/v1/%s",
                API_HOST,
                request.getAction()
        ));
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(10000);
        connection.setRequestMethod(request.getMethod());
        connection.setDoInput(true);
        return connection;
    }

    private String readBody(InputStream input) throws IOException {
        BufferedReader reader;
        StringBuilder string = new StringBuilder();
        String line;
        reader = new BufferedReader(new InputStreamReader(input));
        while ((line = reader.readLine()) != null) {
            string.append(line);
        }
        reader.close();
        return string.toString();
    }
}
