package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
    private static HomeController homeController;
    @BeforeAll
    static void init() {
        homeController = new HomeController();
    }

    @Test
    void at_initialization_allMovies_and_observableMovies_should_be_filled_and_equal() {
        homeController.initializeState();
        assertEquals(homeController.allMovies, homeController.observableMovies);
    }

    @Test
    void query_filter_with_null_movie_list_throws_exception(){
        // given
        homeController.initializeState();
        String query = "IfE";

        // when and then
        assertThrows(IllegalArgumentException.class, () -> homeController.filterByQuery(null, query));
    }

    @Test
    void query_filter_with_null_value_returns_unfiltered_list() {
        // given
        homeController.initializeState();
        String query = null;

        // when
        List<Movie> actual = homeController.filterByQuery(homeController.observableMovies, query);

        // then
        assertEquals(homeController.observableMovies, actual);
    }

    @Test
    void genre_filter_with_null_value_returns_unfiltered_list() {
        // given
        homeController.initializeState();
        Genre genre = null;

        // when
        List<Movie> actual = homeController.filterByGenre(homeController.observableMovies, genre);

        // then
        assertEquals(homeController.observableMovies, actual);
    }

    @Test
    void genre_filter_returns_all_movies_containing_given_genre() {
        // given
        homeController.initializeState();
        Genre genre = Genre.DRAMA;

        // when
        List<Movie> actual = homeController.filterByGenre(homeController.observableMovies, genre);

        // then
        assertEquals(22, actual.size());
    }

//Leonardo DiCaprio

    @Test
    void check_if_method_returns_most_popular_actor() {
        // given
        homeController.initializeState();

        // when
        String actor_test = homeController.getMostPopularActor(homeController.allMovies);

        // then
        assertEquals("Leonardo DiCaprio", actor_test);
    }


    @Test
    void check_if_method_returns_longest_movie_title_char_count() {
        // given
        homeController.initializeState();

        // when
        int movieLength = homeController.getLongestMovieTitle(homeController.allMovies);

        // then
        assertEquals(46, movieLength);
    }

    @Test
    void check_how_many_movies_a_director_has_created() {
        // given
        homeController.initializeState();

        // when
        long movieCount = homeController.countMoviesFrom(homeController.allMovies, "Francis Ford Coppola");

        // then
        assertEquals(1, movieCount);
    }

    @Test
    void check_how_many_movies_were_published_between_years_2000_and_2005() {
        // given
        homeController.initializeState();

        // when
        int movieCount = homeController.getMoviesBetweenYears(homeController.allMovies, 2000,2005).size();

        // then
        assertEquals(4, movieCount);
    }


}