package it.lucapascucci.materialdesing.json;

import org.json.JSONObject;

/**
 * Created by Luca on 24/03/15.
 */
public class Utils {

    public static boolean contains (JSONObject jsonObject, String key){
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key)? true:false;
    }
}
