package it.unibo.androidsensor;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private TextView acc, ambient, light, magnetic, proxi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acc = (TextView) findViewById(R.id.accelerometer);
        ambient = (TextView) findViewById(R.id.ambient);
        light = (TextView) findViewById(R.id.light);
        magnetic = (TextView) findViewById(R.id.magnetic);
        proxi = (TextView) findViewById(R.id.proxi);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : sensorList) {
            switch (sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    ((TextView) findViewById(R.id.accelerometer_label)).setText("TYPE_ACCELEROMETER -> disponibile");
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    ((TextView) findViewById(R.id.ambient_label)).setText("TYPE_AMBIENT_TEMPERATURE -> disponibile");
                    break;
                case Sensor.TYPE_LIGHT:
                    ((TextView) findViewById(R.id.light_label)).setText("TYPE_LIGHT -> disponibile");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                    ((TextView) findViewById(R.id.magnetic_label)).setText("TYPE_MAGNETIC_FIELD -> disponibile");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    ((TextView) findViewById(R.id.proxi_label)).setText("TYPE_PROXIMITY -> disponibile");
                    break;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event == null)
            return;

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                getAccelerometer(event);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                getAmbientTemperature(event);
                break;
            case Sensor.TYPE_LIGHT:
                getLight(event);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                getMagneticField(event);
                break;
            case Sensor.TYPE_PROXIMITY:
                getProximity(event);
                break;
        }
    }

    private void getMagneticField(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        magnetic.setText("X: " + x + "\nY: " + y + " \nZ: " + z);
    }

    private void getLight(SensorEvent event) {
        float[] values = event.values;

        float light = values[0];

        this.light.setText("Luce: " + light + " lux");
    }

    private void getProximity(SensorEvent event) {
        float[] values = event.values;

        float proximity = values[0];

        proxi.setText("Distanza: " + proximity + " cm");

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        acc.setText("X: " + x + "\nY: " + y + " \nZ: " + z);
    }

    private void getAmbientTemperature(SensorEvent event) {
        float[] values = event.values;

        float temp = values[0];

        ambient.setText("Gradi: " + temp + " °");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();

        //android 5.0 non registra tutti i sensori
        /*sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ALL),
                SensorManager.SENSOR_DELAY_UI);*/

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
