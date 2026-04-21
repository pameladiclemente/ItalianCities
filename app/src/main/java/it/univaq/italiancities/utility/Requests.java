package it.univaq.italiancities.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requests {

    public static void asyncRequest(OnRequestListener listener) {
        new Thread(() -> {
            String data = doRequest("GET", "https://raw.githubusercontent.com/matteocontrini/comuni-json/master/comuni.json");
            if(listener != null) listener.onRequestCompleted(data);
        }).start();
    }


    private static String doRequest(String method, String address) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.disconnect();
        }

        return null;
    }


}
