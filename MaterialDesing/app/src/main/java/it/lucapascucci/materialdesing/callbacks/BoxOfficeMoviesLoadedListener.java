package it.lucapascucci.materialdesing.callbacks;

import java.util.ArrayList;

import it.lucapascucci.materialdesing.pojo.Movie;

/**
 * Created by Luca on 24/03/15.
 */
public interface BoxOfficeMoviesLoadedListener {

    public void onBoxOfficeMovieLoaded(ArrayList<Movie> listMovies);
}
