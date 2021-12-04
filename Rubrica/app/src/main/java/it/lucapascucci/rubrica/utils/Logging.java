package it.lucapascucci.rubrica.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Luca on 26/03/15.
 */
public class Logging {

    private static final String debugTag = "PASCU";

    public static void error(String tag,String message){
        Log.e(tag,"" + message);
    }

    public static void debug(String message) {
        Log.d(debugTag, "" + message);
    }

    public static void shortToast(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
    public static void longToast(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}
