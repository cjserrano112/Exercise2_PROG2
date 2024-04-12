package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class MovieDeserializer implements JsonDeserializer<Movie> {
    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String id = jsonObject.get("id").getAsString();
        String title = jsonObject.get("title").getAsString();
        String description = jsonObject.get("description").getAsString();

        List<Genre> genres = Arrays.asList(context.deserialize(jsonObject.getAsJsonArray("genres"), Genre[].class));

        int releaseYear = jsonObject.get("releaseYear").getAsInt();

        String imgUrl = jsonObject.get("imgUrl").getAsString();

        int lengthInMinutes = jsonObject.get("lengthInMinutes").getAsInt();

        double rating = jsonObject.get("rating").getAsDouble();

        return new Movie(id, title, description, genres, releaseYear, imgUrl, lengthInMinutes, null, null, null, rating);
    }
}
