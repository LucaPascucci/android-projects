package it.lucapascucci.laboratorio_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String INTENT_FORM = "it.lucapascucci.FORM_EDIT";
    private static final int FORM_REQUEST_CODE = 100;

    public static final String DATA_NAME = "name";
    public static final String DATA_SURNAME = "surname";
    public static final String DATA_MAIL = "mail";
    public static final String DATA_PASSWORD = "password";
    public static final String DATA_MARRIED = "married";

    Button deleteDataBtn;
    Button formBtn;

    TextView nameTW;
    TextView surnameTW;
    TextView emailTW;
    TextView passwordTW;
    TextView marriedTW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formBtn = (Button) findViewById(R.id.goToForm);
        deleteDataBtn = (Button) findViewById(R.id.deleteData);
        nameTW = (TextView) findViewById(R.id.name);
        surnameTW = (TextView) findViewById(R.id.surname);
        emailTW = (TextView) findViewById(R.id.mail);
        passwordTW = (TextView) findViewById(R.id.password);
        marriedTW = (TextView) findViewById(R.id.married);

        formBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //INTENT implicito
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                Intent intent = new Intent(INTENT_FORM);
                intent.putExtra(DATA_NAME,preferences.getString(DATA_NAME,null));
                intent.putExtra(DATA_SURNAME,preferences.getString(DATA_SURNAME,null));
                intent.putExtra(DATA_MAIL,preferences.getString(DATA_MAIL,null));
                intent.putExtra(DATA_PASSWORD,preferences.getString(DATA_PASSWORD,null));
                intent.putExtra(DATA_MARRIED,preferences.getBoolean(DATA_MARRIED,false));

                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent,FORM_REQUEST_CODE);
                }
            }
        });

        deleteDataBtn.setEnabled(false);
        deleteDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
        showData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FORM_REQUEST_CODE) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            preferences.edit().putString(DATA_NAME,data.getStringExtra(DATA_NAME)).apply();
            preferences.edit().putString(DATA_SURNAME,data.getStringExtra(DATA_SURNAME)).apply();
            preferences.edit().putString(DATA_MAIL,data.getStringExtra(DATA_MAIL)).apply();
            preferences.edit().putString(DATA_PASSWORD,data.getStringExtra(DATA_PASSWORD)).apply();
            preferences.edit().putBoolean(DATA_MARRIED, data.getBooleanExtra(DATA_MARRIED, false)).apply();
            showData();
        }
    }

    private void deleteData() {

        //Se l'utente richiede l'eliminazione dei dati, è consgliabile richiedere conferma prima di procedere.

        new AlertDialog.Builder(this)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO something
                        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                        preferences.edit().remove(DATA_NAME).apply();
                        preferences.edit().remove(DATA_SURNAME).apply();
                        preferences.edit().remove(DATA_MAIL).apply();
                        preferences.edit().remove(DATA_PASSWORD).apply();
                        preferences.edit().remove(DATA_MARRIED).apply();
                        showData();
                    }
                })
                .setNegativeButton("BACK",null)
                .setMessage(getString(R.string.alertMessage))
                .create()
                .show();
    }

    private void showData(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        if (preferences.getString(DATA_NAME,null) != null && !preferences.getString(DATA_NAME,null).trim().isEmpty()){
            deleteDataBtn.setEnabled(true);
            nameTW.setText(preferences.getString(DATA_NAME,null));
        }else{
            nameTW.setText(getResources().getText(R.string.noValue));
        }
        if (preferences.getString(DATA_SURNAME,null) != null && !preferences.getString(DATA_SURNAME,null).trim().isEmpty()){
            deleteDataBtn.setEnabled(true);
            surnameTW.setText(preferences.getString(DATA_NAME, null));
        }else{
            surnameTW.setText(getResources().getText(R.string.noValue));
        }
        if (preferences.getString(DATA_MAIL,null) != null && !preferences.getString(DATA_MAIL,null).trim().isEmpty()){
            deleteDataBtn.setEnabled(true);
            emailTW.setText(preferences.getString(DATA_NAME, null));
        }else{
            emailTW.setText(getResources().getText(R.string.noValue));
        }
        if (preferences.getString(DATA_PASSWORD,null) != null && !preferences.getString(DATA_PASSWORD,null).trim().isEmpty()){
            deleteDataBtn.setEnabled(true);
            passwordTW.setText(preferences.getString(DATA_NAME, null));
        }else{
            passwordTW.setText(getResources().getText(R.string.noValue));
        }
        if (preferences.getBoolean(DATA_MARRIED,false)){
            marriedTW.setText(getResources().getText(R.string.married));
        }else{
            marriedTW.setText(getResources().getText(R.string.single));
        }
    }
}
