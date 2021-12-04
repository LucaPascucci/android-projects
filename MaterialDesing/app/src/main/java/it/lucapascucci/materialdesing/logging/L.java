package it.lucapascucci.materialdesing.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Luca on 19/03/15.
 */
public class L {

    public static void m(String message){
        Log.d("PASCU", "" + message);
    }

    public static void t(Context context, String message){
        Toast.makeText(context,"" + message, Toast.LENGTH_SHORT).show();
    }
}
