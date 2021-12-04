package it.lucapascucci.materialdesing.json;

import it.lucapascucci.materialdesing.materialtest.MyApplication;

import static it.lucapascucci.materialdesing.extras.UrlEndpoints.URL_BOX_OFFICE;
import static it.lucapascucci.materialdesing.extras.UrlEndpoints.URL_CHAR_AMEPERSAND;
import static it.lucapascucci.materialdesing.extras.UrlEndpoints.URL_CHAR_QUESTION;
import static it.lucapascucci.materialdesing.extras.UrlEndpoints.URL_PARAM_API_KEY;
import static it.lucapascucci.materialdesing.extras.UrlEndpoints.URL_PARAM_LIMIT;

/**
 * Created by Luca on 24/03/15.
 */
public class Endpoints {

    public static String getRequestUrl(int limit) {
        return URL_BOX_OFFICE +
                URL_CHAR_QUESTION +
                URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES_VIVZ +
                URL_CHAR_AMEPERSAND +
                URL_PARAM_LIMIT + limit;
    }
}
