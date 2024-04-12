package at.ac.fhcampuswien.fhmdb.models;

import java.net.URL;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;




public class MovieAPI {
    public String title1;
    public String description1;
    public List<Genre> genres1;

    public int releaseYear1;
    public double ratingFrom1;

    private static OkHttpClient client;
    private static URL url;

    private static Gson gson;


    public MovieAPI(String title1, String description1, List<Genre> genres1, int releaseYear1, double ratingFrom1) {
        this.title1 = title1;
        this.description1 = description1;
        this.genres1 = genres1;
        this.releaseYear1 = releaseYear1;
        this.ratingFrom1 = ratingFrom1;
    }

    public static MovieAPI[] callMovies () throws Exception {
        client = new OkHttpClient();
        url = new URL("https://prog2.fh-campuswien.ac.at/movies");
        gson = new Gson();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return gson.fromJson(response.body().charStream(), MovieAPI[].class);
    }

    public String getTitle1() {
        return title1;
    }

    public String getDescription1() {
        return description1;
    }

    public List<Genre> getGenres1() {
        return genres1;
    }

    public int getReleaseYear1() {
        return releaseYear1;
    }

    public double getRatingFrom1() {
        return ratingFrom1;
    }
}
