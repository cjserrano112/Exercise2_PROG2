package at.ac.fhcampuswien.fhmdb.api;

import java.util.Arrays;
import java.util.List;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieDeserializer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.*;

public class MovieAPI {
    private static final String URL = "https://prog2.fh-campuswien.ac.at/movies";

    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Movie.class, new MovieDeserializer()).create();

    private static String modifyURL(String query, Genre genre, String releaseYear, String rating) {
        StringBuilder url = new StringBuilder(URL);
        appendQueryParam(url, "query", query);
        appendQueryParam(url, "genre", genre != null ? genre.toString() : null);
        appendQueryParam(url, "releaseYear", releaseYear);
        appendQueryParam(url, "ratingFrom", rating);
        System.out.println("Request: " + url.toString());
        return url.toString();
    }

    private static void appendQueryParam(StringBuilder url, String paramName, String paramValue) {
        if (paramValue != null && !paramValue.isEmpty()) {
            if (url.indexOf("?") == -1) {
                url.append("?");
            } else {
                url.append("&");
            }
            url.append(paramName).append("=").append(paramValue);
        }
    }

    public static List<Movie> callMovies(String query, Genre genre, String releaseYear, String rating) {
        Request request = new Request.Builder()
                .url(modifyURL(query, genre, releaseYear, rating))
                .addHeader("User-Agent", "http.agent")
                .build();
        try (Response response = CLIENT.newCall(request).execute()) {
            Movie[] movies = GSON.fromJson(response.body().string(), Movie[].class);
            return Arrays.asList(movies);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }
    public static List<Movie> getMovies() {
        return callMovies(null, null, null, null);
    }
}