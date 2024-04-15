package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;
    @FXML
    public JFXComboBox releaseYearComboBox;
    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;

    protected List<Movie> allMovies;

    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    protected SortedState sortedState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
    }

    public void initializeState() {
        allMovies = MovieAPI.callMovies(null, null, null, null);
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell()); // apply custom cells to the listview

        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");

        releaseYearComboBox.getItems().add("No filter");
        releaseYearComboBox.setPromptText("Filter by release year");
        Integer[] releaseYears = new Integer[99];
        for (int i = 0; i < 99; i++) {
            releaseYears[i] = 2024 - i;
        }
        releaseYearComboBox.getItems().addAll(releaseYears);

        ratingComboBox.getItems().add("No filter");
        ratingComboBox.setPromptText("Rating from...:");
        Double[] rating = new Double[]{1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00};
        ratingComboBox.getItems().addAll(rating);
    }

    public void applyAllFilters(String searchQuery, Object genre, String releaseYear, String rating) {
        List<Movie> filteredMovies;
        if (searchQuery.isEmpty() && genre == ("Filter by Genre") && releaseYear.equals("Filter by release Year") && rating.equals("Rating from...:")) {
            filteredMovies = MovieAPI.getMovies();
        } else {
            if (searchQuery.isEmpty()) searchQuery = null;
            if (genre != null && genre.equals("No filter")) genre = null;
            if (releaseYear.equals("Filter by release Year") || releaseYear.equals("No filter") || releaseYear.isEmpty()) releaseYear = null;
            if (rating.equals("Rating from...:") || rating.equals("No filter") || rating.isEmpty()) rating = null;
            filteredMovies = MovieAPI.callMovies(searchQuery, (Genre) genre, releaseYear, rating);
        }
        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void searchBtnClicked() {
        String searchQuery = searchField.getText().trim().toLowerCase();
        Object genre = genreComboBox.getSelectionModel().getSelectedItem();

        String releaseYear = "";
        if (releaseYearComboBox.getSelectionModel().getSelectedItem() != null) releaseYear = releaseYearComboBox.getSelectionModel().getSelectedItem().toString();

        String rating = "";
        if (ratingComboBox.getSelectionModel().getSelectedItem() != null) rating = ratingComboBox.getSelectionModel().getSelectedItem().toString();

        applyAllFilters(searchQuery, genre, releaseYear, rating);
        sortedState = SortedState.NONE;
    }

    public void sortBtnClicked() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    public void resetBtnClicked() {
        searchField.clear();
        ratingComboBox.getSelectionModel().clearSelection();
        releaseYearComboBox.getSelectionModel().clearSelection();
        genreComboBox.getSelectionModel().clearSelection();
        searchBtnClicked();
    }

    public String getMostPopularActor(List<Movie> movies) {
        Map<String, Long> actorCounts = movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()));

        return actorCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getTitle)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int min, int max) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= min && movie.getReleaseYear() <= max)
                .collect(Collectors.toList());
    }
}