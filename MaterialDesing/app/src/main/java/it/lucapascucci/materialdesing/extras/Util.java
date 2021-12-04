package it.lucapascucci.materialdesing.extras;

import android.os.Build;

/**
 * Created by Luca on 19/03/15.
 */
public class Util {

    public static boolean isLollipopOrGreater(){
        return Build.VERSION.SDK_INT >= 21? true : false;
    }

    public static boolean isJellyBeanOrGrater(){
        return Build.VERSION.SDK_INT >= 16? true : false;
    }
}
