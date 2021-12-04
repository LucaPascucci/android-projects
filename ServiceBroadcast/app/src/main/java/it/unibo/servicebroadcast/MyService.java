package it.unibo.servicebroadcast;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

public class MyService extends Service {
	
	public static final String TIMER = "timer";
	
	private long timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (timer == 0)
            startTimer(10000, 2000);
        return START_STICKY;
    }
	
	public void startTimer(long time, long tick) {
		new CountDownTimer(time, tick) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				timer = millisUntilFinished;
                sendBroadcast();
			}
			
			@Override
			public void onFinish() {
				timer = 0;
                sendBroadcast();
			}
		}.start();
	}

    private void sendBroadcast() {
        Intent intent = new Intent(MainActivity.MAIN_ACTIVITY_ACTION);
        intent.putExtra(TIMER, timer);
        sendBroadcast(intent);
    }

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
