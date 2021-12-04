package it.lucapascucci.provaservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Luca on 08/04/15.
 */
public class BroadcastRicevitore extends BroadcastReceiver {

    public static final String MY_ACTION = "it.lucapascucci.service.azione";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(MY_ACTION)){
            Intent i = new Intent(context, Servizio.class);
            i.putExtra("VALUE",intent.getIntExtra("VALUE",0));
            context.startService(i);
        }

    }
}
