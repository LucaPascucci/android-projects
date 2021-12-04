package it.lucapascucci.materialdesing.extras;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import it.lucapascucci.materialdesing.json.Endpoints;
import it.lucapascucci.materialdesing.json.Parser;
import it.lucapascucci.materialdesing.json.Requestor;
import it.lucapascucci.materialdesing.materialtest.MyApplication;
import it.lucapascucci.materialdesing.pojo.Movie;

/**
 * Created by Luca on 24/03/15.
 */
public class MovieUtils {

    public static ArrayList<Movie> loadBoxOfficeMovies (RequestQueue requestQueue){
        JSONObject response = Requestor.sendRequestBoxOfficeMovies(requestQueue, Endpoints.getRequestUrl(30));
        ArrayList<Movie> listMovies = Parser.parseJSONResponse(response);
        MyApplication.getWritableDatabase().insertMoviesBoxOffice(listMovies,true);
        return listMovies;
    }
}
