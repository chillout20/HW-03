package hw4;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.GregorianCalendar;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Movies {
    String title;
    Integer year;
    String released;
    Integer runtime;
    String[] genres;
    Director director;

    public static class Director {
        String name;
    }

    Writer[] writers;

    public static class Writer {
        String name;
        String type;
    }

    Actor[] actors;

    public static class Actor {
        String name;
        String as;
    }

    String plot;
    String[] languages;
    String[] countries;
    String awards;
    String poster;
    Rating[] ratings;

    public static class Rating {
        String source;
        String value;
        Integer votes;
    }

    public void fillDetailsFromFile(String filename)
            throws FileNotFoundException {
        // Read the file
        InputStream fis = new FileInputStream(filename);
        JsonReader jsonReader = Json.createReader(fis);
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        // Title, year, released, runtime
        this.title = object.getString("Title");
        this.year = object.getInt("Year");
        this.released = object.getString("Released");
        this.runtime = object.getInt("Runtime");

        // Genres
        JsonArray genresArray = object.getJsonArray("Genres");
        this.genres = new String[genresArray.size()];
        for (int i = 0; i < genresArray.size(); i++) {
            this.genres[i] = genresArray.getString(i);
        }

        // Director
        JsonObject director = object.getJsonObject("Director");
        this.director = new Director();
        this.director.name = director.getString("Name");

        // Writers
        JsonArray writerArray = object.getJsonArray("Writers");
        this.writers = new Writer[writerArray.size()];
        for (int i = 0; i < writerArray.size(); i++) {
            this.writers[i] = new Writer();
            this.writers[i].name = writerArray.getJsonObject(i).getString("Name");
            this.writers[i].type = writerArray.getJsonObject(i).getString("Type");
        }

        // Actors
        JsonArray actorArray = object.getJsonArray("Actors");
        this.actors = new Actor[actorArray.size()];
        for (int i = 0; i < actorArray.size(); i++) {
            this.actors[i] = new Actor();
            this.actors[i].name = actorArray.getJsonObject(i).getString("Name");
            this.actors[i].as = actorArray.getJsonObject(i).getString("As");
        }

        // Plot
        this.plot = object.getString("Plot");

        // Languages
        JsonArray languagesArray = object.getJsonArray("Languages");
        this.languages = new String[languagesArray.size()];
        for (int i = 0; i < languagesArray.size(); i++) {
            this.languages[i] = languagesArray.getString(i);
        }

        // Countries
        JsonArray countriesArray = object.getJsonArray("Countries");
        this.countries = new String[countriesArray.size()];
        for (int i = 0; i < countriesArray.size(); i++) {
            this.countries[i] = countriesArray.getString(i);
        }

        // Awards, poster
        this.awards = object.getString("Awards");
        this.poster = object.getString("Poster");

        // Ratings
        JsonArray ratingArray = object.getJsonArray("Ratings");
        this.ratings = new Rating[ratingArray.size()];
        for (int i = 0; i < ratingArray.size(); i++) {
            this.ratings[i] = new Rating();
            this.ratings[i].source = ratingArray.getJsonObject(i).getString("Source");
            this.ratings[i].value = ratingArray.getJsonObject(i).getString("Value");

            try {
                this.ratings[i].votes = ratingArray.getJsonObject(i).getInt("Votes");
            } catch (NullPointerException e) {
                this.ratings[i].votes = null;
            }
        }
    }

    public void fillDetails(String jsonString)
            throws FileNotFoundException {
        // Read the file
        Gson gson = new Gson();
        JsonObject object = gson.fromJson(jsonString, JsonObject.class);

        // Title, year, released, runtime
        this.title = object.getString("Title");
        this.year = object.getInt("Year");
        this.released = object.getString("Released");
        this.runtime = object.getInt("Runtime");

        // Genres
        JsonArray genresArray = object.getJsonArray("Genres");
        this.genres = new String[genresArray.size()];
        for (int i = 0; i < genresArray.size(); i++) {
            this.genres[i] = genresArray.getString(i);
        }

        // Director
        JsonObject director = object.getJsonObject("Director");
        this.director = new Director();
        this.director.name = director.getString("Name");

        // Writers
        JsonArray writerArray = object.getJsonArray("Writers");
        this.writers = new Writer[writerArray.size()];
        for (int i = 0; i < writerArray.size(); i++) {
            this.writers[i] = new Writer();
            this.writers[i].name = writerArray.getJsonObject(i).getString("Name");
            this.writers[i].type = writerArray.getJsonObject(i).getString("Type");
        }

        // Actors
        JsonArray actorArray = object.getJsonArray("Actors");
        this.actors = new Actor[actorArray.size()];
        for (int i = 0; i < actorArray.size(); i++) {
            this.actors[i] = new Actor();
            this.actors[i].name = actorArray.getJsonObject(i).getString("Name");
            this.actors[i].as = actorArray.getJsonObject(i).getString("As");
        }

        // Plot
        this.plot = object.getString("Plot");

        // Languages
        JsonArray languagesArray = object.getJsonArray("Languages");
        this.languages = new String[languagesArray.size()];
        for (int i = 0; i < languagesArray.size(); i++) {
            this.languages[i] = languagesArray.getString(i);
        }

        // Countries
        JsonArray countriesArray = object.getJsonArray("Countries");
        this.countries = new String[countriesArray.size()];
        for (int i = 0; i < countriesArray.size(); i++) {
            this.countries[i] = countriesArray.getString(i);
        }

        // Awards, poster
        this.awards = object.getString("Awards");
        this.poster = object.getString("Poster");

        // Ratings
        JsonArray ratingArray = object.getJsonArray("Ratings");
        this.ratings = new Rating[ratingArray.size()];
        for (int i = 0; i < ratingArray.size(); i++) {
            this.ratings[i] = new Rating();
            this.ratings[i].source = ratingArray.getJsonObject(i).getString("Source");
            this.ratings[i].value = ratingArray.getJsonObject(i).getString("Value");

            try {
                this.ratings[i].votes = ratingArray.getJsonObject(i).getInt("Votes");
            } catch (NullPointerException e) {
                this.ratings[i].votes = null;
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Movies bladeRunner = new Movies();
        bladeRunner.fillDetails("src/main/java/hw4/BladeRunner.json");
    }
}


