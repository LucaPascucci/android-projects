package it.lucapascucci.materialdesing.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.lucapascucci.materialdesing.extras.Constants;
import it.lucapascucci.materialdesing.pojo.Movie;

import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_CAST;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_ID;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_LINKS;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_RATINGS;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_REVIEWS;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_SELF;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_SIMILAR;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_THEATER;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static it.lucapascucci.materialdesing.extras.Keys.EndpointBoxOffice.KEY_TITLE;
import static it.lucapascucci.materialdesing.json.Utils.contains;

/**
 * Created by Luca on 24/03/15.
 */
public class Parser {

    public static ArrayList<Movie> parseJSONResponse(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Movie> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++) {

                    long id = -1;
                    String title = Constants.NA;
                    String releaseDate = Constants.NA;
                    int audienceScore = -1;
                    String synopsis = Constants.NA;
                    String urlThumbnail = Constants.NA;
                    String urlSelf = Constants.NA;
                    String urlCast = Constants.NA;
                    String urlReviews = Constants.NA;
                    String urlSimilar = Constants.NA;

                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    if (contains(currentMovie, KEY_ID)) {
                        id = currentMovie.getLong(KEY_ID);
                    }
                    //get the title of the current movie
                    if (contains(currentMovie, KEY_TITLE)) {
                        title = currentMovie.getString(KEY_TITLE);
                    }

                    //get the date in theaters for the current movie
                    if (contains(currentMovie, KEY_RELEASE_DATES)) {
                        JSONObject objectReleaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);

                        if (contains(objectReleaseDates, KEY_THEATER)) {
                            releaseDate = objectReleaseDates.getString(KEY_THEATER);
                        }
                    }

                    //get the audience score for the current movie

                    if (contains(currentMovie, KEY_RATINGS)) {
                        JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
                        if (contains(objectRatings, KEY_AUDIENCE_SCORE)) {
                            audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                        }
                    }

                    // get the synopsis of the current movie
                    if (contains(currentMovie, KEY_SYNOPSIS)) {
                        synopsis = currentMovie.getString(KEY_SYNOPSIS);
                    }

                    //get the url for the thumbnail to be displayed inside the current movie result
                    if (contains(currentMovie, KEY_POSTERS)) {
                        JSONObject objectPosters = currentMovie.getJSONObject(KEY_POSTERS);

                        if (contains(objectPosters, KEY_THUMBNAIL)) {
                            urlThumbnail = objectPosters.getString(KEY_THUMBNAIL);
                        }
                    }

                    //get the url of the related links
                    if (contains(currentMovie, KEY_LINKS)) {
                        JSONObject objectLinks = currentMovie.getJSONObject(KEY_LINKS);
                        if (contains(objectLinks, KEY_SELF)) {
                            urlSelf = objectLinks.getString(KEY_SELF);
                        }
                        if (contains(objectLinks, KEY_CAST)) {
                            urlCast = objectLinks.getString(KEY_CAST);
                        }
                        if (contains(objectLinks, KEY_REVIEWS)) {
                            urlReviews = objectLinks.getString(KEY_REVIEWS);
                        }
                        if (contains(objectLinks, KEY_SIMILAR)) {
                            urlSimilar = objectLinks.getString(KEY_SIMILAR);
                        }
                    }


                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = null;
                    try {
                        date = dateFormat.parse(releaseDate);
                    } catch (ParseException exc) {

                    }
                    movie.setReleaseDateTheather(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);
                    movie.setUrlSelf(urlSelf);
                    movie.setUrlCast(urlCast);
                    movie.setUrlReview(urlReviews);
                    movie.setUrlSimilar(urlSimilar);

                    if (id != -1 && !title.equals(Constants.NA)) {
                        listMovies.add(movie);
                    }
                }

            } catch (JSONException exc) {

            }
        }
        return listMovies;
    }
}
