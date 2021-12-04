package it.lucapascucci.laboratorio_2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class FormActivity extends ActionBarActivity {

    private Button submitBtn;
    private EditText nameET;
    private EditText surnameET;
    private EditText mailET;
    private EditText passwordET;
    private CheckBox marriedCHK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        nameET = (EditText) findViewById(R.id.nameET);
        surnameET = (EditText) findViewById(R.id.surnameET);
        mailET = (EditText) findViewById(R.id.mailET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        marriedCHK = (CheckBox) findViewById(R.id.marriedCHK);

        Intent recivedIntent = getIntent();
        if (recivedIntent != null){
            nameET.setText(recivedIntent.getStringExtra(MainActivity.DATA_NAME));
            surnameET.setText(recivedIntent.getStringExtra(MainActivity.DATA_SURNAME));
            mailET.setText(recivedIntent.getStringExtra(MainActivity.DATA_MAIL));
            passwordET.setText(recivedIntent.getStringExtra(MainActivity.DATA_PASSWORD));
            marriedCHK.setChecked(recivedIntent.getBooleanExtra(MainActivity.DATA_MARRIED,false));
        }

        submitBtn = (Button) findViewById(R.id.sumbit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.DATA_NAME,nameET.getText().toString());
                resultIntent.putExtra(MainActivity.DATA_SURNAME,surnameET.getText().toString());
                resultIntent.putExtra(MainActivity.DATA_MAIL,mailET.getText().toString());
                resultIntent.putExtra(MainActivity.DATA_PASSWORD,passwordET.getText().toString());
                resultIntent.putExtra(MainActivity.DATA_MARRIED,marriedCHK.isChecked());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
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
}
