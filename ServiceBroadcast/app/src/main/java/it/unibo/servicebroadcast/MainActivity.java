package it.unibo.servicebroadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final String MAIN_ACTIVITY_ACTION = "it.unibo.servicebroadcast.main_activity_action";
	
	public BroadcastReceiver brodcast = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && intent.getAction().equals(MAIN_ACTIVITY_ACTION)) {
				long timer = intent.getLongExtra(MyService.TIMER, -1);
				if (timer == -1) {
					Toast.makeText(getApplicationContext(), "Dati mancanti", Toast.LENGTH_SHORT).show();
				} else if (timer == 0) {
					Toast.makeText(getApplicationContext(), "CountDown terminato", Toast.LENGTH_SHORT).show();
				} else if (timer != 0) {
					Toast.makeText(getApplicationContext(), "Timer rimanente " + Long.toString(timer),
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(brodcast, new IntentFilter(MAIN_ACTIVITY_ACTION));
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(brodcast);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyBroadcastReceiver.MY_ACTION);
				sendBroadcast(intent);
			}
		});
		
	}


}