package it.lucapascucci.provaservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    public static final String MAIN_ACTIVITY_ACTION = "it.lucapascucci.provaservice.main_activity_action";
    private TextView textView;

    public BroadcastReceiver brodcast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(MAIN_ACTIVITY_ACTION)) {
                Toast.makeText(getApplicationContext(),"Finito!!",Toast.LENGTH_LONG).show();
                leggiTimerLog();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BroadcastRicevitore.MY_ACTION);
                String value = ((EditText) findViewById(R.id.editText)).getText().toString();
                intent.putExtra("VALUE", Integer.parseInt(value));
                sendBroadcast(intent);
            }
        });
        textView = (TextView) findViewById(R.id.text_log);

    }

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

    private void leggiTimerLog() {
        String log = readFromFile();
        textView.setText(log != null ? log : "");
    }

    private String readFromFile() {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        String ret = "";
        try {
            inputStream = new FileInputStream(Servizio.getFileData());
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString + "\n");
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
