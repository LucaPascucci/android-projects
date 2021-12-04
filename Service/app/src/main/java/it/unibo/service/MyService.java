package it.unibo.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by on 07/04/2015.
 */
public class MyService extends Service {

    private static final String FILE = "data.txt";

    public static File getFileData() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE);
    }



    private long timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (timer == 0)
            startTimer();
        return START_STICKY;
    }

    private void startTimer() {
        Log.d("MyService", "startTimer()");
        writeStartTimer();
        new CountDownTimer(10000, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("MyService", "onTick("+millisUntilFinished+")");
                timer = millisUntilFinished;
                writeTimer();
            }

            @Override
            public void onFinish() {
                Log.d("MyService", "onFinish()");
                timer = 0;
                writeEndTimer();
            }
        }.start();
    }

    public static String getDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
        return df.format(Calendar.getInstance().getTime());
    }

    private void writeStartTimer() {
        writeText("/*** Inizio timer il " + getDate() + " ***/");
    }

    private void writeTimer() {
        writeText("["+ getDate() +"] Il tempo del timer è " + timer);
    }

    private void writeEndTimer() {
        writeText("/*** Timer terminato ***/");
    }

    private void writeText(String text) {
        PrintWriter pw = null;
        try {

            File file = getFileData();

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            pw.println(text);
        } catch (IOException e) {
            Log.e("MyService", "Errore durante il writeText(" + text + ")", e);
        } finally {
            if (pw != null)
                try {
                    pw.close();
                } catch (Exception e) {
                }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
