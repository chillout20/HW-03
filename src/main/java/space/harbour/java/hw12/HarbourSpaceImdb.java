package space.harbour.java.hw12;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFileLocation;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.bson.Document;
import org.eclipse.jetty.http.HttpStatus;
import space.harbour.java.hw10.MongoExecutorMovies;
import space.harbour.java.hw7.Movies;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * A simple CRUD example showing how to create, get, update and delete movie resources.
 */
public class HarbourSpaceImdb {

    /**
     * Map holding the moview.
     */
    private static Map<Integer, Movies> movies = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        // Let's add some movies to the HashMap
        // You shoud read them from the MongoDB
        MongoExecutorMovies executor = new MongoExecutorMovies();

        movies = executor.execRetrieveAllMovies();

        final Gson gson = new Gson();
        final Random random = new Random();

        staticFileLocation("public");

        // Creates a new movie resource, will return the ID to the created resource
        // author and title are sent in the post body
        // as x-www-urlencoded values e.g. author=Foo&title=Bar


        post("/movies", (request, response) -> {
            String director = request.queryParams("director");
            String title = request.queryParams("title");
            String year = request.queryParams("year");
            String runtime = request.queryParams("runtime");
            Movies movie = new Movies();
            movie.setDirector(director);
            movie.setTitle(title);
            movie.setRuntime(Integer.parseInt(runtime));
            movie.setYear(Integer.parseInt(year));

            int id = random.nextInt(Integer.MAX_VALUE);
            movies.put(id, movie);

            response.status(HttpStatus.CREATED_201);
            return id;
        });

        // Gets the movie resource for the provided id
        get("/movies/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Movies movie = movies.get(id);
            if (movie == null) {
                response.status(HttpStatus.NOT_FOUND_404);
                return "Movie not found";
            }
            if (clientAcceptsHtml(request)) {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("movie", movie);
                return render(movieMap, "movie.ftl");
            } else if (clientAcceptsJson(request)) {
                return gson.toJson(movie);
            }

            return null;
        });

        // Updates the movie resource for the provided id with new information

        put("/movies/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Movies movie = movies.get(id);
            if (movie == null) {
                response.status(HttpStatus.NOT_FOUND_404);
                return "Movie not found";
            }
            String newDirector = request.queryParams("director");
            if (newDirector != null) {
                movie.setDirector(newDirector);
            }

            String newTitle = request.queryParams("title");
            if (newTitle != null) {
                movie.setTitle(newTitle);
            }

            String newYear = request.queryParams("year");
            if (newYear != null) {
                movie.setYear(Integer.parseInt(newYear));
            }

            String newRuntime = request.queryParams("runtime");
            if (newRuntime != null) {
                movie.setRuntime(Integer.parseInt(newRuntime));
            }

            return "Movie " + movie.getTitle() + " with id '" + id + "' updated";
        });



        // Deletes the movie resource for the provided id
        delete("/movies/delete/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            String title = movies.get(id).getTitle();
            Movies movie = movies.remove(id);
            Document filter = new Document().append("Title", title);
            if (movie == null) {
                response.status(HttpStatus.NOT_FOUND_404);
                return "Movie not found";
            }
            executor.execDeleteMovie(filter);
            return "Movie " + title + " with id '" + id + "' deleted";
        });

        // Gets all available movie resources
        get("/movies", (request, response) -> {
            if (clientAcceptsHtml(request)) {
                Map<String, Object> moviesMap = new HashMap<>();
                moviesMap.put("movies", movies);
                return render(moviesMap, "movies.ftl");
            } else if (clientAcceptsJson(request)) {
                return gson.toJson(movies);
            }
            return null;
        });
    }

    public static String render(Map values, String template) {
        return new FreeMarkerEngine().render(new ModelAndView(values, template));
    }

    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static boolean clientAcceptsJson(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("application/json");
    }
}