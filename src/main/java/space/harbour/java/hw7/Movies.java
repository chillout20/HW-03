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
    Object _id;
    String Title;
    Integer Year;
    String Released;
    Integer Runtime;
    String[] Genres;
    Director Director;

    public static class Director {
        String Name;
    }

    Writer[] Writers;

    public static class Writer {
        String Name;
        String Type;

    }

    Actor[] Actors;

    public static class Actor {
        String Name;
        String As;

        public String getActorName() {
            return this.Name;
        }
    }

    String Plot;
    String[] Languages;
    String[] Countries;
    String Awards;
    String Poster;
    Rating[] Ratings;

    public static class Rating {
        String Source;
        String Value;
        Integer Votes;
    }

    public void fillDetails(String filename)
            throws FileNotFoundException {

        // Read the file
        InputStream fis = new FileInputStream(filename);
        JsonReader jsonReader = Json.createReader(fis);
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        // Title, year, released, runtime
        this.Title = object.getString("Title");
        this.Year = object.getInt("Year");
        this.Released = object.getString("Released");
        this.Runtime = object.getInt("Runtime");

        // Genres
        JsonArray genresArray = object.getJsonArray("Genres");
        this.Genres = new String[genresArray.size()];
        for (int i = 0; i < genresArray.size(); i++) {
            this.Genres[i] = genresArray.getString(i);
        }

        // Director
        JsonObject director = object.getJsonObject("Director");
        this.Director = new Director();
        this.Director.Name = director.getString("Name");

        // Writers
        JsonArray writerArray = object.getJsonArray("Writers");
        this.Writers = new Writer[writerArray.size()];
        for (int i = 0; i < writerArray.size(); i++) {
            this.Writers[i] = new Writer();
            this.Writers[i].Name = writerArray.getJsonObject(i).getString("Name");
            this.Writers[i].Type = writerArray.getJsonObject(i).getString("Type");
        }

        // Actors
        JsonArray actorArray = object.getJsonArray("Actors");
        this.Actors = new Actor[actorArray.size()];
        for (int i = 0; i < actorArray.size(); i++) {
            this.Actors[i] = new Actor();
            this.Actors[i].Name = actorArray.getJsonObject(i).getString("Name");
            this.Actors[i].As = actorArray.getJsonObject(i).getString("As");
        }

        // Plot
        this.Plot = object.getString("Plot");

        // Languages
        JsonArray languagesArray = object.getJsonArray("Languages");
        this.Languages = new String[languagesArray.size()];
        for (int i = 0; i < languagesArray.size(); i++) {
            this.Languages[i] = languagesArray.getString(i);
        }

        // Countries
        JsonArray countriesArray = object.getJsonArray("Countries");
        this.Countries = new String[countriesArray.size()];
        for (int i = 0; i < countriesArray.size(); i++) {
            this.Countries[i] = countriesArray.getString(i);
        }

        // Awards, poster
        this.Awards = object.getString("Awards");
        this.Poster = object.getString("Poster");

        // Ratings
        JsonArray ratingArray = object.getJsonArray("Ratings");
        this.Ratings = new Rating[ratingArray.size()];
        for (int i = 0; i < ratingArray.size(); i++) {
            this.Ratings[i] = new Rating();
            this.Ratings[i].Source = ratingArray.getJsonObject(i).getString("Source");
            this.Ratings[i].Value = ratingArray.getJsonObject(i).getString("Value");

            try {
                this.Ratings[i].Votes = ratingArray.getJsonObject(i).getInt("Votes");
            } catch (NullPointerException e) {
                this.Ratings[i].Votes = null;
            }
        }
    }

    public List<Movies> sortByReleaseYear(List<Movies> moviesList) {
        System.out.println("Sorting by release year:");
        moviesList.sort(Comparator.comparingInt((Movies o) -> o.Year));
        moviesList.forEach(x -> System.out.println("Title: " + x.Title + ", Released: " + x.Year));
        return moviesList;
    }

    public List<Movies> sortByRuntime(List<Movies> moviesList) {
        System.out.println("Sorting by runtime:");
        moviesList.sort(Comparator.comparingInt((Movies o) -> o.Runtime));
        moviesList.forEach(x -> System.out.println("Title: " + x.Title
                + ", Runtime: " + x.Runtime));
        return moviesList;
    }

    public List<Movies> sortByAward(List<Movies> moviesList) {
        System.out.println("Sorting by length of award:");
        moviesList.sort(Comparator.comparingInt((Movies o) -> o.Awards.length()));
        moviesList.forEach(x -> System.out.println("Title: " + x.Title
                + ", Award (length): " + x.Awards.length()));
        return moviesList;
    }

    public List<Movies> filterByDirector(List<Movies> movieList, String inputDirector) {
        System.out.println("Filtering movies by director " + inputDirector + ":");
        List<Movies> filteredMovieList = movieList.stream()
                .filter(x -> x.Director.Name.equals(inputDirector))
                .collect(Collectors.toList());

        filteredMovieList
                .forEach(x -> System.out.println("Title: " + x.Title
                        + ", Director: " + x.Director.Name));
        return filteredMovieList;

    }

    public List<Movies> filterByActor(List<Movies> movieList, String inputActor) {
        System.out.println("Filtering movies with actor " + inputActor + ":");
        List<Movies> filteredMovieList = movieList.stream()
                .filter(x -> {
                    for (Actor actor : x.Actors) {
                        if (actor.getActorName().equals(inputActor)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());

        filteredMovieList
                .forEach(x -> System.out.println("Movie " + x.Title + " has actor " + inputActor));

        return filteredMovieList;

    }

    public List<Movies> filterByGenre(List<Movies> movieList, String inputGenre) {
        System.out.println("Filtering movies by genre " + inputGenre + ":");
        List<Movies> filteredMovieList = movieList.stream()
                .filter(x -> Arrays.asList(x.Genres).contains(inputGenre))
                .collect(Collectors.toList());

        filteredMovieList
                .forEach(x -> System.out.println("Movies " + x.Title
                        + " is in genre " + inputGenre));

        return filteredMovieList;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<Movies> moviesList = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            moviesList.add(new Movies());
        }
        moviesList.get(0).fillDetails(
                "src/main/java/space/harbour/java/hw7/BladeRunner.json");
        moviesList.get(1).fillDetails(
                "src/main/java/space/harbour/java/hw7/MysticRiver.json");
        moviesList.get(2).fillDetails(
                "src/main/java/space/harbour/java/hw7/StayDown.json");
        moviesList.get(3).fillDetails(
                "src/main/java/space/harbour/java/hw7/TheShawshankRedemption.json");

        // Before sorting
        System.out.println("Before sorting: ");
        moviesList.forEach(x -> System.out.println(
                x.Title + ", Released year: "
                + x.Year + ", Runtime: "
                + x.Runtime + ", Awards (length): "
                + x.Awards.length()));
        System.out.println("\n");

        Movies comparator = new Movies();

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

    public String getTitle() {
        return Title;
    }

    public int getYear() {
        return Year;
    }

    public String getDirector() {
        return Director.Name;
    }

    public String getPoster() {
        return Poster;
    }

    public Actor[] getActors() {
        return Actors;
    }

    public String getPlot() {
        return Plot;
    }

    public Integer getRuntime() {
        return Runtime;
    }

    public String getAwards() {
        return Awards;
    }
}


