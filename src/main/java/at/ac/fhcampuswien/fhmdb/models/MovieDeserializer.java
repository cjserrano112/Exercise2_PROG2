package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class MovieDeserializer implements JsonDeserializer<Movie> {
    @Override
    // Wird benötigt um die Movies zu initialisieren
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String id = jsonObject.get("id").getAsString();
        String title = jsonObject.get("title").getAsString();
        String description = jsonObject.get("description").getAsString();

        List<Genre> genres = Arrays.asList(context.deserialize(jsonObject.getAsJsonArray("genres"), Genre[].class));

        int releaseYear = jsonObject.get("releaseYear").getAsInt();

        String imgUrl = jsonObject.get("imgUrl").getAsString();

        int lengthInMinutes = jsonObject.get("lengthInMinutes").getAsInt();

        List<String> directors = Arrays.asList(context.deserialize(jsonObject.getAsJsonArray("directors"), String[].class));
        List<String> writers = Arrays.asList(context.deserialize(jsonObject.getAsJsonArray("writers"), String[].class));
        List<String> mainCast = Arrays.asList(context.deserialize(jsonObject.getAsJsonArray("mainCast"), String[].class));

        double rating = jsonObject.get("rating").getAsDouble();

        return new Movie(id, title, description, genres, releaseYear, imgUrl, lengthInMinutes, directors, writers, mainCast, rating);
    }
}
