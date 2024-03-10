package org.example.API;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UserAPI {
    private static final String BASE_URL = "http://localhost:5000/api/v1";

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
