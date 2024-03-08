package org.example.API;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.data.CreateJoke;
import org.example.data.Joke;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.API.payload.Payload.createPayload;

public class JokeAPI {

    private static String BASE_URL = "http://localhost:5000/api/v1/jokes";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static Joke getRandomJoke(String jwt) throws IOException, ParseException {
        HttpGet get = new HttpGet(BASE_URL + "/random");
        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getCode() != 200) {
            return new Joke("Du", null);
        }

        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();

        Joke joke = mapper.readValue(EntityUtils.toString(entity), Joke.class);
        return joke;

    }
    public static List<Joke> getAllJokes(String jwt) throws IOException, ParseException {
        HttpGet get = new HttpGet(BASE_URL);
        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getCode() != 200) {
            List<Joke> jokes = new ArrayList<>();
            jokes.add(new Joke("> Dålig dag för skämt...", null));
            return jokes;
        }

        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Joke> jokes = mapper.readValue(EntityUtils.toString(entity), new TypeReference<ArrayList<Joke>>() {});
        return jokes;
    }
    public static void createJoke(CreateJoke payload, String jwt) throws IOException {
        HttpPost post = new HttpPost(BASE_URL);
        post.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        post.setEntity(createPayload(payload));

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getCode() != 200) {
            System.out.println("> Dåligt skämt, detta publiceras inte.");
        }
    }

    // Joke - ADMIN
    // Metod för att uppdatera ett skämt
    public static void updateJoke(int jokeId, String updatedJoke, String token) {
        try {
            URL url = new URL(BASE_URL + "/jokes/" + jokeId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);

            // JSON-payload för att skicka uppdaterad information om skämtet
            String payload = "{\"id\":" + jokeId + ", \"joke\":\"" + updatedJoke + "\"}";

            connection.getOutputStream().write(payload.getBytes());

            // Tar emot svarskod från servern
            int responsCode = connection.getResponseCode();
            System.out.println("Response Code: " + responsCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    // Metod för att radera ett skämt
    public static void deleteJoke(int jokeId, String token) {
        try {
            URL url = new URL(BASE_URL + "/jokes/" + jokeId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
