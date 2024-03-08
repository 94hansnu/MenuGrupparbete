package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DadJokeApiClient {
    private static final String BASE_URL = "http://localhost:5000/api/v1";



    // Author
    // Metod för att uppdatera författare
    public static void updateAuthor(int authorId, String updatedName, String token) {
        try {
            URL url = new URL(BASE_URL + "/author/" +  authorId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);

            String payload = "{\"id\":" + authorId + ", \name\":\"" + updatedName + "\"}";

            connection.getOutputStream().write(payload.getBytes());

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    // Metod för att radera en författare
    public static void deleteAuthor(int authorId, String token) {
        try {
            URL url = new URL(BASE_URL + "/author/" + authorId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

    }

    // USER
    // Metod för att hämta en användare med specifikt ID
    public static void getUserById(int userId, String token) {
        try {
            URL url = new URL(BASE_URL + "/admin/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }


    // Metod för att hämta alla användare
    public static void getAllUsers(String token) {
        try {
            URL url = new URL(BASE_URL + "/admin/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    // Metod för att uppdatera en användare
    public static void updateUser(int userId, String updatedUsername, String updatedPassword, String token) {
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
    public static void deleteUser(int userId, String token) {
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
    public static void upgradeUser(int userId, String token) {
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
    public static void downgradeUser(int userId, String token) {
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
