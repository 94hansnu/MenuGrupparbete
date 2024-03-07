package org.example.API;

import org.example.data.Author;

import java.util.List;

public class AuthorAPI {
    private static String BASE_URL = "http://localhost:5000/api/v1";

    public static Author getAuthor(Long authorId, String jwt) {
        return null;
    }

    public static List<Author> getAllAuthors(String jwt) {
        return null;
    }

    public static void createAuthor(String string, String jwt) {
    }
}
