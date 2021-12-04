package it.lucapascucci.materialdesing.materialtest;

import android.app.Application;
import android.content.Context;

import it.lucapascucci.materialdesing.database.MoviesDatabase;

/**
 * Created by Luca on 18/03/15.
 */
public class MyApplication extends Application {

    public static final String API_KEY_ROTTEN_TOMATOES = "";
    public static final String API_KEY_ROTTEN_TOMATOES_VIVZ = "";
    private static MyApplication sInstance;

    private static MoviesDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = new MoviesDatabase(this);
    }

    public static MyApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

    public synchronized static MoviesDatabase getWritableDatabase(){
        if (mDatabase == null){
            mDatabase = new MoviesDatabase(getAppContext());
        }
        return mDatabase;
    }
}
