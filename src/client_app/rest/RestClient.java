package client_app.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient {

    private String baseUrl;

    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // Example: login
    public String login(String username, String password) {
        try {
            URL url = new URL(baseUrl + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Example: GET doctors
    public String getDoctors() {
        try {
            URL url = new URL(baseUrl + "/doctors");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
