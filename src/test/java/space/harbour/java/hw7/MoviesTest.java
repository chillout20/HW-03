package space.harbour.java.hw7;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class MoviesTest extends TestCase
{
    Movies comparator = new Movies();
    List<Movies> moviesList = new LinkedList<>();
    List<Movies> moviesListSorted = new LinkedList<>();


    @Test
    public void sortByReleaseYear() throws FileNotFoundException
    {
        for (int i = 0; i < 4; i++) {
            moviesList.add(new Movies());
            moviesListSorted.add(new Movies());
        }
        moviesList.get(0).fillDetails("src/main/java/space/harbour/java/hw7/BladeRunner.json");
        moviesList.get(1).fillDetails("src/main/java/space/harbour/java/hw7/MysticRiver.json");
        moviesList.get(2).fillDetails("src/main/java/space/harbour/java/hw7/StayDown.json");
        moviesList.get(3).fillDetails("src/main/java/space/harbour/java/hw7/TheShawshankRedemption.json");

        moviesListSorted.get(0).fillDetails("src/main/java/space/harbour/java/hw7/BladeRunner.json");
        moviesListSorted.get(1).fillDetails("src/main/java/space/harbour/java/hw7/TheShawshankRedemption.json");
        moviesListSorted.get(2).fillDetails("src/main/java/space/harbour/java/hw7/MysticRiver.json");
        moviesListSorted.get(3).fillDetails("src/main/java/space/harbour/java/hw7/StayDown.json");

        assertEquals(moviesListSorted, comparator.sortByReleaseYear(moviesList));

    }

    @Test
    public void sortByRuntime() throws FileNotFoundException
    {
        moviesListSorted.get(0).fillDetails("src/main/java/space/harbour/java/hw7/StayDown.json");
        moviesListSorted.get(1).fillDetails("src/main/java/space/harbour/java/hw7/BladeRunner.json");
        moviesListSorted.get(2).fillDetails("src/main/java/space/harbour/java/hw7/MysticRiver.json");
        moviesListSorted.get(3).fillDetails("src/main/java/space/harbour/java/hw7/TheShawshankRedemption.json");

        assertEquals(moviesListSorted, comparator.sortByReleaseYear(moviesList));
    }

    @Test
    public void sortByAward() throws FileNotFoundException {
        moviesListSorted.get(0).fillDetails("src/main/java/space/harbour/java/hw7/StayDown.json");
        moviesListSorted.get(1).fillDetails("src/main/java/space/harbour/java/hw7/BladeRunner.json");
        moviesListSorted.get(2).fillDetails("src/main/java/space/harbour/java/hw7/TheShawshankRedemption.json");
        moviesListSorted.get(3).fillDetails("src/main/java/space/harbour/java/hw7/MysticRiver.json");

        assertEquals(moviesListSorted, comparator.sortByReleaseYear(moviesList));
    }

    List<Movies> filterByDirectorResult = new LinkedList<>();
    @Test
    public void filterByDirector() throws FileNotFoundException {
        for (int i = 0; i < 2; i++) {
            filterByDirectorResult.add(new Movies());
        }
        filterByDirectorResult.get(0).fillDetails("src/main/java/space/harbour/java/hw7/StayDown.json");
        filterByDirectorResult.get(1).fillDetails("src/main/java/space/harbour/java/hw7/TheShawshankRedemption.json");

        assertEquals(filterByDirectorResult, comparator.filterByDirector(moviesList, "Frank Darabont"));
    }

    List<Movies> filterByActorResult = new LinkedList<>();
    @Test
    public void filterByActor() throws FileNotFoundException {
        for (int i = 0; i < 2; i++) {
            filterByActorResult.add(new Movies());
        }
        filterByActorResult.get(0).fillDetails("src/main/java/space/harbour/java/hw7/TheShawshankRedemption.json");
        filterByActorResult.get(1).fillDetails("src/main/java/space/harbour/java/hw7/MysticRiver.json");

        assertEquals(filterByActorResult, comparator.filterByActor(moviesList, "Tim Robbins"));
    }

    List<Movies> filterByGenreResult = new LinkedList<>();
    @Test
    public void filterByGenre() throws FileNotFoundException {
        for (int i = 0; i < 3; i++) {
            filterByGenreResult.add(new Movies());
        }

        filterByGenreResult.get(0).fillDetails("src/main/java/space/harbour/java/hw7/StayDown.json");
        filterByGenreResult.get(1).fillDetails("src/main/java/space/harbour/java/hw7/TheShawshankRedemption.json");
        filterByGenreResult.get(2).fillDetails("src/main/java/space/harbour/java/hw7/MysticRiver.json");

        assertEquals(filterByGenreResult, comparator.filterByActor(moviesList, "Drama"));

    }
}