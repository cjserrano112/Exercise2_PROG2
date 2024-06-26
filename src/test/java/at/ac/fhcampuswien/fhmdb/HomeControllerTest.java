package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
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
    void apply_filter_with_no_values() {
        homeController.initializeState();
        homeController.applyAllFilters("", "Filter by Genre", "Filter by release Year", "Rating from...:");
        assertEquals(homeController.allMovies, homeController.observableMovies);
    }

    @Test
    void apply_filter_with_values1() {
        homeController.initializeState();
        homeController.applyAllFilters("puss", Genre.ADVENTURE, "", "");
        assertEquals("Puss in Boots", homeController.observableMovies.get(0).getTitle());
    }

    @Test
    void apply_filter_with_values2() {
        homeController.initializeState();
        homeController.applyAllFilters("va", "No filter", "2009", "");
        assertEquals("Avatar", homeController.observableMovies.get(0).getTitle());
    }

    @Test
    void apply_filter_with_values3() {
        homeController.initializeState();
        homeController.applyAllFilters("", null, "", "9.0");
        assertEquals(3, homeController.observableMovies.size());
    }

    @Test
    void sort_list_ascending_check_if_sorted_correctly() {
        homeController.initializeState();
        homeController.sortBtnClicked();
        assertEquals("12 Angry Men", homeController.observableMovies.get(0).getTitle());
    }

    @Test
    void sort_list_descending_check_if_sorted_correctly() {
        homeController.initializeState();
        homeController.sortedState = SortedState.ASCENDING;
        homeController.sortBtnClicked();
        assertEquals("Toy Story", homeController.observableMovies.get(0).getTitle());
    }

    @Test
    void check_if_method_returns_most_popular_actor() {
        List<Movie> test = Arrays.asList((new Movie("1", "Test1", "blabla", List.of(Genre.ROMANCE), 2000, "url", 69, null, null, List.of("Leonardo DiCaprio"), 7.0)),
                (new Movie("2", "Test2", "asdf", List.of(Genre.DRAMA), 2010, "url", 12, null, null, List.of("Ivan Ban"), 7.0)),
                (new Movie("3", "Test3", "blabla", List.of(Genre.ADVENTURE), 1999, "url", 69, null, null, List.of("Ivan Ban"), 7.0)));
        homeController.allMovies = test;
        String actualActor = homeController.getMostPopularActor(homeController.allMovies);
        assertEquals("Ivan Ban", actualActor);
    }


    @Test
    void check_if_method_returns_longest_movie_title_char_count() {
        List<Movie> test = Arrays.asList((new Movie("1", "Test1 longest Name", "blabla", List.of(Genre.ROMANCE), 2000, "url", 69, null, null, List.of("Leonardo DiCaprio"), 7.0)),
                (new Movie("2", "Test2", "asdf", List.of(Genre.DRAMA), 2010, "url", 12, null, null, List.of("Ivan Ban"), 7.0)),
                (new Movie("3", "Test3", "blabla", List.of(Genre.ADVENTURE), 1999, "url", 69, null, null, List.of("Leonardo DiCaprio"), 7.0)));
        homeController.allMovies = test;
        int movieLength = homeController.getLongestMovieTitle(homeController.allMovies);
        assertEquals(18, movieLength);
    }

    @Test
    void check_how_many_movies_a_director_has_created() {
        List<Movie> test = Arrays.asList((new Movie("1", "Test1", "blabla", List.of(Genre.ROMANCE), 2000, "url", 69, List.of("Ivan Ban"), null, List.of("Leonardo DiCaprio"), 7.0)),
                (new Movie("2", "Test2", "asdf", List.of(Genre.DRAMA), 2010, "url", 12, List.of("Christopher Serrano"), null, List.of("Ivan Ban"), 7.0)),
                (new Movie("3", "Test3", "blabla", List.of(Genre.ADVENTURE), 1999, "url", 69, List.of("Christopher Serrano"), null, List.of("Leonardo DiCaprio"), 7.0)));
        homeController.allMovies = test;
        long movieCount = homeController.countMoviesFrom(homeController.allMovies, "Christopher Serrano");
        assertEquals(2, movieCount);
    }

    @Test
    void check_how_many_movies_were_published_between_years_2000_and_2010() {
        List<Movie> test = Arrays.asList((new Movie("1", "Test1", "blabla", List.of(Genre.ROMANCE), 2000, "url", 69, null, null, List.of("Leonardo DiCaprio"), 7.0)),
                (new Movie("2", "Test2", "asdf", List.of(Genre.DRAMA), 2010, "url", 12, List.of("Christopher Serrano"), null, List.of("Ivan Ban"), 7.0)),
                (new Movie("3", "Test3", "blabla", List.of(Genre.ADVENTURE), 1999, "url", 69, null, null, List.of("Leonardo DiCaprio"), 7.0)));
        homeController.allMovies = test;
        int movieCount = homeController.getMoviesBetweenYears(homeController.allMovies, 2000,2010).size();
        assertEquals(2, movieCount);
    }

    @Test
    void equals_should_return_false_when_parameter_is_null() {
        Movie movie = new Movie("1", "Test", "Leer", List.of(Genre.DRAMA), 2000, "url", 100, null, null, null, 5.5);
        Movie movieNull = null;
        assertFalse(movie.equals(movieNull));
    }

    @Test
    void equals_should_return_false_when_parameter_is_not_a_movie() {
        Movie movie = new Movie("1", "Test", "Leer", List.of(Genre.DRAMA), 2000, "url", 100, null, null, null, 5.5);
        String comparison = "test";
        assertFalse(movie.equals(comparison));
    }

    @Test
    void get_id_should_return_id() {
        Movie movie = new Movie("1", "Test", "Leer", List.of(Genre.DRAMA), 2000, "url", 100, null, null, null, 5.5);
        assertEquals("1", movie.getId());
    }

    @Test
    void get_description_should_return_description() {
        Movie movie = new Movie("1", "Test", "Leer", List.of(Genre.DRAMA), 2000, "url", 100, null, null, null, 5.5);
        assertEquals("Leer", movie.getDescription());
    }

    @Test
    void get_genres_should_return_genres() {
        Movie movie = new Movie("1", "Test", "Leer", List.of(Genre.DRAMA), 2000, "url", 100, null, null, null, 5.5);
        assertEquals(List.of(Genre.DRAMA), movie.getGenres());
    }

    @Test
    void get_imgurl_should_return_imgurl() {
        Movie movie = new Movie("1", "Test", "Leer", List.of(Genre.DRAMA), 2000, "url", 100, null, null, null, 5.5);
        assertEquals("url", movie.getImgUrl());
    }

    @Test
    void get_lengthmin_should_return_lengthmin() {
        Movie movie = new Movie("1", "Test", "Leer", List.of(Genre.DRAMA), 2000, "url", 100, null, null, null, 5.5);
        assertEquals(100, movie.getLengthInMinutes());
    }

    @Test
    void get_writers_should_return_writers() {
        Movie movie = new Movie("1", "Test", "Leer", List.of(Genre.DRAMA), 2000, "url", 100, null, List.of("Writer 1", "Writer 2"), null, 5.5);
        assertEquals(List.of("Writer 1", "Writer 2"), movie.getWriters());
    }

    @Test
    void get_rating_should_return_rating() {
        Movie movie = new Movie("1", "Test", "Leer", List.of(Genre.DRAMA), 2000, "url", 100, null, null, null, 5.5);
        assertEquals(5.5, movie.getRating());
    }

    @Test
    void call_movies_should_catch_exception_and_print_message_and_return_empty_list() {
        List<Movie> result = MovieAPI.callMovies("", Genre.ACTION, "23a", "5jk");
        assertEquals(List.of(), result);
    }
}