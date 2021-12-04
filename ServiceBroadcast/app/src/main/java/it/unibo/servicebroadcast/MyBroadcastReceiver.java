package it.unibo.servicebroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
	
	public static final String MY_ACTION = "it.unibo.service.my_action";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action != null && action.equals(MY_ACTION)) {
			Intent i = new Intent(context, MyService.class);
			context.startService(i);
		} 
	}

}