package it.lucapascucci.materialdesing.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import it.lucapascucci.materialdesing.callbacks.BoxOfficeMoviesLoadedListener;
import it.lucapascucci.materialdesing.extras.MovieUtils;
import it.lucapascucci.materialdesing.network.VolleySingleton;
import it.lucapascucci.materialdesing.pojo.Movie;

/**
 * Created by Luca on 24/03/15.
 */
public class TaskLoadMoviesBoxOffice extends AsyncTask<Void,Void,ArrayList<Movie>>{

        private BoxOfficeMoviesLoadedListener myComponent;
        private VolleySingleton volleySingleton;
        private RequestQueue requestQueue;


        public TaskLoadMoviesBoxOffice(BoxOfficeMoviesLoadedListener myComponent){
            this.myComponent = myComponent;
            volleySingleton = VolleySingleton.getInstance();
            requestQueue = volleySingleton.getRequestQueue();

        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            ArrayList<Movie> listMovie = MovieUtils.loadBoxOfficeMovies(requestQueue);
            return listMovie;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> listMovies) {
            if (myComponent != null) {
                myComponent.onBoxOfficeMovieLoaded(listMovies);
            }
        }


}
