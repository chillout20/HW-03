package space.harbour.java.hw7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
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
        public String getActorName() {
            return this.name;
        }
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

    public void fillDetails(String filename)
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
    public List<Movies> sortByReleaseYear(List<Movies> moviesList) {
        System.out.println("Sorting by release year:");
        moviesList.sort(Comparator.comparingInt((Movies o) -> o.year));
        moviesList.forEach(x -> System.out.println("Title: " + x.title + ", Released: " + x.year));
        return moviesList;
    }

    public List<Movies> sortByRuntime(List<Movies> moviesList) {
        System.out.println("Sorting by runtime:");
        moviesList.sort(Comparator.comparingInt((Movies o) -> o.runtime));
        moviesList.forEach(x -> System.out.println("Title: " + x.title + ", Runtime: " + x.runtime));
        return moviesList;
    }

    public List<Movies> sortByAward(List<Movies> moviesList) {
        System.out.println("Sorting by length of award:");
        moviesList.sort(Comparator.comparingInt((Movies o) -> o.awards.length()));
        moviesList.forEach(x -> System.out.println("Title: " + x.title + ", Award (length): " + x.awards.length()));
        return moviesList;
    }

    public List<Movies> filterByDirector(List<Movies> movieList, String inputDirector) {
        System.out.println("Filtering movies by director " + inputDirector + ":");
        List<Movies> filteredMovieList = movieList.stream()
                .filter(x -> x.director.name.equals(inputDirector))
                .collect(Collectors.toList());

        filteredMovieList
                .forEach(x -> System.out.println("Title: " + x.title + ", Director: " + x.director.name));
        return filteredMovieList;

    }


    public List<Movies> filterByActor(List<Movies> movieList, String inputActor) {
        System.out.println("Filtering movies with actor " + inputActor + ":");
        List<Movies> filteredMovieList = movieList.stream()
                .filter(x -> {for (Actor actor : x.actors) {
                    if (actor.getActorName().equals(inputActor)) {
                        return true ;
                    }
                } return false;})
                .collect(Collectors.toList());

        filteredMovieList
                .forEach(x -> System.out.println("Movie " + x.title + " has actor " + inputActor));

        return filteredMovieList;

    }

    public List<Movies> filterByGenre(List<Movies> movieList, String inputGenre) {
        System.out.println("Filtering movies by genre " + inputGenre + ":");
        List<Movies> filteredMovieList = movieList.stream()
                .filter(x -> Arrays.asList(x.genres).contains(inputGenre))
                .collect(Collectors.toList());

        filteredMovieList
                .forEach(x -> System.out.println("Movies " + x.title + " is in genre " + inputGenre));

        return filteredMovieList;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<Movies> moviesList = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            moviesList.add(new Movies());
        }
        moviesList.get(0).fillDetails("src/main/java/space/harbour/java/hw7/BladeRunner.json");
        moviesList.get(1).fillDetails("src/main/java/space/harbour/java/hw7/MysticRiver.json");
        moviesList.get(2).fillDetails("src/main/java/space/harbour/java/hw7/StayDown.json");
        moviesList.get(3).fillDetails("src/main/java/space/harbour/java/hw7/TheShawshankRedemption.json");

        Movies comparator = new Movies();

        // Before sorting
        System.out.println("Before sorting: ");
        moviesList.forEach(x -> System.out.println(
                x.title + ", Released year: "
                + x.year + ", Runtime: "
                + x.runtime + ", Awards (length): "
                + x.awards.length()));
        System.out.println("\n");

        // Sorting by release year
        comparator.sortByReleaseYear(moviesList);
        System.out.println("\n");

        // Sorting by runtime
        comparator.sortByRuntime(moviesList);
        System.out.println("\n");

        // Sorting by length of "awards"
        comparator.sortByAward(moviesList);
        System.out.println("\n");

        // Filter by director
        comparator.filterByDirector(moviesList, "Frank Darabont");
        System.out.println("\n");

        // Filter by actor
        comparator.filterByActor(moviesList, "Tim Robbins");
        System.out.println("\n");

        // Filter by genre
        comparator.filterByGenre(moviesList, "Drama");
        System.out.println("\n");
    }
}


