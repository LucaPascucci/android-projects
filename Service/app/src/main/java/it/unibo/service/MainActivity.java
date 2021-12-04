package it.unibo.service;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text_log);

        findViewById(R.id.avvia_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avviaTimerSerive();
            }
        });

        findViewById(R.id.leggi_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leggiTimerLog();
            }
        });
    }


    private void avviaTimerSerive() {
        startService(new Intent(this, MyService.class));
    }

    private void leggiTimerLog() {
        String log = readFromFile();
        textView.setText(log != null ? log : "");
    }

    public String readFromFile() {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        String ret = "";
        try {
            inputStream = new FileInputStream(MyService.getFileData());
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }

            ret = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            Log.e("", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("", "Can not read file: " + e.toString());
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            if (inputStreamReader != null)
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                }
        }

        return ret;
    }
}
