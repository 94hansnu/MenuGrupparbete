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

}
