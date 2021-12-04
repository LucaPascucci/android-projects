package it.lucapascucci.laboratorio_1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    EditText txt;
    Button btn;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (EditText) findViewById(R.id.editText);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btn = (Button) findViewById(R.id.button);
        btn.setEnabled(false);

        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn.setEnabled(!String.valueOf(txt.getText()).trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                String value = String.valueOf(((RadioButton) findViewById(selectedId)).getText());
                if(R.id.longRadio == selectedId) {
                    Toast.makeText(getApplicationContext(), String.valueOf(txt.getText()) + " " + value, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), String.valueOf(txt.getText()) + " " + value, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toast.makeText(this,getString(R.string.app_name) + " " + getString(R.string.startApp),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume(); //obbligatorio!!
        Toast.makeText(this,getString(R.string.app_name) + " " + getString(R.string.resumed),Toast.LENGTH_LONG).show();
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
}
