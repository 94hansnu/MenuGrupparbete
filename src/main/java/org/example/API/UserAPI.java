package org.example.API;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.data.Joke;
import org.example.dto.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UserAPI {
    private static final String BASE_URL = "http://localhost:5000/api/v1";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    // USER
    // Metod för att hämta en användare med specifikt ID
    public static String getUserById(Long userId, String jwt) {
        try {
            URL url = new URL(BASE_URL + "/admin/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + jwt);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            return null;
        }
        return jwt;
    }

    public static User getUserByIdd(Long id, String jwt) throws IOException, ParseException {
        HttpGet get = new HttpGet(BASE_URL + "/admin/" + id);
        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getCode() != 200) {
            return new User();
        }

        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();

        User user = mapper.readValue(EntityUtils.toString(entity), User.class);
        return user;
    }


    // Metod för att hämta alla användare
    public static List<User> getAllUsers(String jwt) throws IOException {
        try {
            URL url = new URL(BASE_URL + "/admin/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + jwt);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode != 200) {
                throw new IOException("Kunde inte hämta användare. Responskod: " + responseCode);
            }

            // Hämta JSON-svaret från servern och konvertera till en lista med användare
            ObjectMapper mapper = new ObjectMapper();
            List<User> users = mapper.readValue(connection.getInputStream(), new TypeReference<List<User>>() {});

            return users;
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            throw e; // Kasta vidare IOException om något går fel
        }
    }

    // Metod för att uppdatera en användare
    public static void updateUser(Long userId, String updatedUsername, String updatedPassword, String token) {
        try {
            URL url = new URL(BASE_URL + "/admin/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);

            String payload = "{\"username\":\"" + updatedUsername + "\", \"password\":\"" + updatedPassword + "\"}";

            connection.getOutputStream().write(payload.getBytes());

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    // Metod för att radera en användare
    public static void deleteUser(Long userId, String token) {
        try {
            URL url = new URL(BASE_URL + "/admin/" + userId);
            HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    // Metod för att uppgradera en användare till admin-rättigheter
    public static void upgradeUser(Long userId, String token) {
        try {
            URL url = new URL(BASE_URL + "/admin/toggleRights/" +  userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " +  responseCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    // Metod för att nedgradera en användare till vanliga användarrättigheter
    public static void downgradeUser(Long userId, String token) {
        try {
            URL url = new URL(BASE_URL + "/admin/toggleRights/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
