package it.lucapascucci.materialdesing.extras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import it.lucapascucci.materialdesing.pojo.Movie;

/**
 * Created by Luca on 20/03/15.
 */
public class MovieSorter {

    public void sortMoviesByName (ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }

    public void sortMoviesByDate (ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return rhs.getReleaseDateTheather().compareTo(lhs.getReleaseDateTheather());
            }
        });
    }

    public void sortMoviesByRating (ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                int ratingLhs = lhs.getAudienceScore();
                int ratingRhs = rhs .getAudienceScore();

                if (ratingLhs < ratingRhs){
                    return 1;
                }else if (ratingLhs > ratingRhs){
                    return -1;
                }else{
                    return 0;
                }
            }

        });
    }
}
