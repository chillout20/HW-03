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
import org.eclipse.jetty.http.HttpStatus;
import space.harbour.java.hw10.MongoExecutorMovies;
import space.harbour.java.hw7.Movies;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * A simple CRUD example showing how to create, get, update and delete book resources.
 */
public class HarbourSpaceIMDb {

    /**
     * Map holding the books
     */
    private static Map<Integer, Movies> movies = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        // Let's add some books to the HashMap
        // You shoud read them from the MongoDB
        MongoExecutorMovies executor = new MongoExecutorMovies();

        movies = executor.execRetrieveAllMovies();

        final Gson gson = new Gson();
        final Random random = new Random();

        staticFileLocation("public");

        // Creates a new book resource, will return the ID to the created resource
        // author and title are sent in the post body as x-www-urlencoded values e.g. author=Foo&title=Bar
        // you get them by using request.queryParams("valuename")


        //post("/movies", (request, response) -> {
        //    String author = request.queryParams("author");
        //    String title = request.queryParams("title");
        //    Integer pages = Integer.valueOf(request.queryParams("pages"));
        //    Book book = new Book(author, title, pages);
        //
        //    int id = random.nextInt(Integer.MAX_VALUE);
        //    movies.put(id, book);
        //
        //    response.status(HttpStatus.CREATED_201);
        //    return id;
        //});

        // Gets the book resource for the provided id
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
            } else if (clientAcceptsJson(request))
                return gson.toJson(movie);

            return null;
        });

        // Updates the book resource for the provided id with new information
        // author and title are sent in the request body as x-www-urlencoded values e.g. author=Foo&title=Bar
        // you get them by using request.queryParams("valuename")


        //put("/movies/:id", (request, response) -> {
        //    Integer id = Integer.valueOf(request.params(":id"));
        //    Movies movie = movies.get(id);
        //    if (movie == null) {
        //        response.status(HttpStatus.NOT_FOUND_404);
        //        return "Movie not found";
        //    }
        //    String newAuthor = request.queryParams("author");
        //    String newTitle = request.queryParams("title");
        //    String newPages = request.queryParams("pages");
        //    if (newAuthor != null) {
        //        book.setAuthor(newAuthor);
        //    }
        //    if (newTitle != null) {
        //        book.setTitle(newTitle);
        //    }
        //    if (newPages != null) {
        //        book.setPages(Integer.valueOf(newPages));
        //    }
        //    return "Book with id '" + id + "' updated";
        //});



        // Deletes the movie resource for the provided id
        delete("/books/delete/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Movies movie = movies.remove(id);
            if (movie == null) {
                response.status(HttpStatus.NOT_FOUND_404);
                return "Movie not found";
            }
            return "Movie with id '" + id + "' deleted";
        });

        // Gets all available book resources
        get("/movies", (request, response) -> {
            if (clientAcceptsHtml(request)) {
                Map<String, Object> moviesMap = new HashMap<>();
                moviesMap.put("movies", movies);
                return render(moviesMap, "movies.ftl");
            } else if (clientAcceptsJson(request))
                return gson.toJson(movies);

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