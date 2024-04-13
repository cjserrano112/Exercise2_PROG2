package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

        List<String> mainCast = Arrays.asList(context.deserialize(jsonObject.getAsJsonArray("mainCast"), String[].class));
        List<String> directors = Arrays.asList(context.deserialize(jsonObject.getAsJsonArray("directors"), String[].class));

        return new Movie(id, title, description, genres, releaseYear, imgUrl, lengthInMinutes, directors, null, mainCast, rating);
    }
}
