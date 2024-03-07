package org.example.API;

import org.example.data.Joke;

import java.util.List;

public class JokeAPI {

    private static String BASE_URL = "http://localhost:5000/api/v1";

    public static Joke getRandomJoke(String jwt) {
        return null;
    }
    public static List<Joke> getAllJokes(String jwt) {
        return null;
    }
    public static void createJoke(Object[] payload, String jwt) {

    }
}
