package it.lucapascucci.provaservice;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Luca on 08/04/15.
 */
public class Servizio extends Service {

    private static final String FILE = "data.txt";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            writeText(intent.getIntExtra("VALUE", 0));
        }
        sendBroadcast();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static File getFileData() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE);
    }

    private void writeText(int value) {
        PrintWriter pw = null;
        try {

            File file = getFileData();

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            for (int i = 0; i < value; i++){
                pw.println(i);
            }
        } catch (IOException e) {
            Log.e("MyService", "Errore durante il writeText", e);
        } finally {
            if (pw != null)
                try {
                    pw.close();
                } catch (Exception e) {
                }
        }
    }

    private void sendBroadcast() {
        Intent intent = new Intent(MainActivity.MAIN_ACTIVITY_ACTION);
        sendBroadcast(intent);
    }
}
