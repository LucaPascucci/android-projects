package it.lucapascucci.materialdesing.services;

import java.util.ArrayList;

import it.lucapascucci.materialdesing.callbacks.BoxOfficeMoviesLoadedListener;
import it.lucapascucci.materialdesing.logging.L;
import it.lucapascucci.materialdesing.pojo.Movie;
import it.lucapascucci.materialdesing.task.TaskLoadMoviesBoxOffice;
import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by Luca on 21/03/15.
 */
public class MyService extends JobService implements BoxOfficeMoviesLoadedListener{

    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this, "onStartJob");
        this.jobParameters = jobParameters;
        new TaskLoadMoviesBoxOffice(this).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        L.t(this,"onCloseJob");
        return false;
    }


    @Override
    public void onBoxOfficeMovieLoaded(ArrayList<Movie> listMovies) {
        L.t(this,"onBoxOfficeMoviesLoaded");
        jobFinished(jobParameters,false);
    }
}
