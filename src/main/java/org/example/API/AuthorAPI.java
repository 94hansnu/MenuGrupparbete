package org.example.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.data.Author;
import org.example.data.Joke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.API.payload.Payload.createPayload;

public class AuthorAPI {
    private static String BASE_URL = "http://localhost:5000/api/v1/author";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static Author getAuthor(Long authorId, String jwt) throws IOException, ParseException {
        HttpGet get = new HttpGet(BASE_URL+"/" + authorId);
        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getCode() != 200) {
            return new Author("> Askkungen");
        }

        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();

        Author author = mapper.readValue(EntityUtils.toString(entity), Author.class);
        return author;
    }

    public static List<Author> getAllAuthors(String jwt) throws IOException, ParseException {
        HttpGet get = new HttpGet(BASE_URL);

        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getCode() != 200) {
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("> Tingeling"));
            return authors;
        }

        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Author> authors = mapper.readValue(EntityUtils.toString(entity), new TypeReference<ArrayList<Author>>() {});
        return authors;

    }

    public static void createAuthor(String name, String jwt) throws IOException {
        HttpPost post = new HttpPost(BASE_URL);
        post.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        post.setEntity(createPayload(name));

        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getCode() != 200) {
            System.out.println("> Gick ej att lägga till author.");

        }
    }
}
