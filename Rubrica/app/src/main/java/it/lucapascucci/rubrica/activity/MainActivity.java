package it.lucapascucci.rubrica.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import it.lucapascucci.rubrica.R;
import it.lucapascucci.rubrica.adapter.AdapterPersona;
import it.lucapascucci.rubrica.fragment.DetailsPersonaFragment;
import it.lucapascucci.rubrica.fragment.PersoneFragment;
import it.lucapascucci.rubrica.model.DataManager;
import it.lucapascucci.rubrica.model.Persona;
import it.lucapascucci.rubrica.utils.FileUtils;
import it.lucapascucci.rubrica.utils.Logging;

import static it.lucapascucci.rubrica.utils.Keys.*;


public class MainActivity extends ActionBarActivity  implements PersoneFragment.PersoneListener, DetailsPersonaFragment.DetailsPersonaListenter{


    private static final int REQUEST_CODE_NEW = 1;
    private static final int REQUEST_CODE_DISPLAY = 0;

    private ListView listView;
    private AdapterPersona adapterPersona;

    private boolean isShowDetails = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        loadPersone();

        showListPersone();

        //new DownloadPersone().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_EDIT && resultCode == RESULT_OK) {
            Persona persona = data != null ? (Persona) data.getSerializableExtra(PERSONA_NUOVA) : null;
            addPersonaToManager(persona);
        }

        showListPersone();

    }

    /**
     * Lancia l'activity per visualizzare i dettagli di una persona
     *
     * @param persona
     *         persona da visualizzare
     */
    private void showPersona(Persona persona) {
        if (persona == null) {
            Toast.makeText(this, R.string.toast_niente_da_mostrare, Toast.LENGTH_LONG).show();
            return;
        }

        DetailsPersonaFragment fragment = (DetailsPersonaFragment) getFragmentManager().findFragmentByTag(TAG_DETAILS_PERSONA);
        if (fragment == null) {
            fragment = new DetailsPersonaFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment, TAG_DETAILS_PERSONA);
            transaction.commit();

            isShowDetails = true;
        }
        fragment.setPersonaVisualizzata(persona);
    }


    /**
     * Lancia l'activity per aggiungere o editare una persona
     *
     * @param persona
     *         può essere null
     */
    private void addOrEditPersona(Persona persona) {
        Intent intent = new Intent(this, AddOrEditPersona.class);
        if (persona != null)
            intent.putExtra(PERSONA_IN_MODIFICA, persona);
        startActivityForResult(intent, RESULT_EDIT);
    }

    private void showListPersone() {
        PersoneFragment fragment = (PersoneFragment) getFragmentManager().findFragmentByTag(TAG_LISTA_PERSONE);
        if (fragment == null) {
            fragment = new PersoneFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment, TAG_LISTA_PERSONE);
            transaction.commit();

            isShowDetails = false;
        }
        fragment.setPersone(DataManager.getInstance().getPersone());
    }

    private void savePersone() {
        JSONArray array = new JSONArray();
        List<Persona> persone = DataManager.getInstance().getPersone();
        if (persone != null) {
            for (Persona persona : persone) {
                try {
                    array.put(persona.toJSONObjcet());
                } catch (Exception e) {
                    Logging.error("savePersone", "Errore durante l'aggiunta di una persona all'array :" + e.getMessage());
                }
            }
        }

        FileUtils.writeToFile(array.toString(), FILENAME, this);
    }

    private void loadPersone() {
        String data = FileUtils.readFromFile(FILENAME, this);
        if (data != null && !data.isEmpty()) {
            try {
                JSONArray array = new JSONArray(data);
                DataManager manager = DataManager.getInstance();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.optJSONObject(i);
                    if (json != null) {
                        try {
                            Persona persona = new Persona(json);
                            manager.addPersona(persona);
                        } catch (Exception e) {
                            Logging.error("loadPersone", "Errore durante il parse del json persona :" + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                Logging.error("loadPersone", "Errore durante il parse della lista delle persone :" + e.getMessage());
            }
        }
    }

    /**
     * Aggiunge una persona al manager
     *
     * @param persona
     */
    protected void addPersonaToManager(Persona persona) {
        if (persona != null) {
            DataManager.getInstance().addPersona(persona);
            Logging.longToast(this, getString(R.string.toast_persona_salvata));
            savePersone();
        }
    }

    /**
     * Inizializza o aggiorna la lista delle persone sulla listView
     */
    protected void populateListView() {
        if (adapterPersona == null) {
            adapterPersona = new AdapterPersona(this, DataManager.getInstance().getPersone());
            listView.setAdapter(adapterPersona);
        } else {
            adapterPersona.clear();
            adapterPersona.addAll(DataManager.getInstance().getPersone());
            adapterPersona.notifyDataSetChanged();
        }
    }

    @Override
    public void onPersonaClicked(Persona persona) {
        showPersona(persona);
    }

    @Override
    public void onRequestNuovaPersona() {
        addOrEditPersona(null);
    }

    @Override
    public void onBackPressed() {
        if (isShowDetails) {
            showListPersone();
        } else
            super.onBackPressed();
    }

    @Override
    public void onRequestEditPersona(Persona persona) {
        addOrEditPersona(persona);
    }

    @Override
    public void onRequestDeletePersona(Persona persona) {
        String id = persona != null ? persona.getId() : null;
        if (id != null) {
            if (DataManager.getInstance().deletePersona(id)) {
                Toast.makeText(this, R.string.toast_persona_eliminata, Toast.LENGTH_LONG).show();
                savePersone();
                showListPersone();
            }
        }
    }

    protected void showError(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Errore durante la chiamata al server");
        dialog.show();
    }

    private void parsePersone(String result){
        try{
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++){
                try {
                    JSONObject json = array.getJSONObject(i);
                    Persona persona = new Persona();
                    persona.setNome(json.getString("nome"));
                    persona.setCognome(json.getString("cognome"));
                    persona.setMail(json.getString("email"));
                    persona.setTelefono(json.getString("telefono"));
                    DataManager.getInstance().addPersona(persona);

                }catch (Exception e){
                    Logging.error("PASCU",e.getMessage());
                }
            }
            showListPersone();
        }catch (JSONException e){
            showError();
        }
    }

    @SuppressWarnings("deprecation")
    protected class DownloadPersone extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            //String URL = params[0];

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.mklab.cc/prove");
            try {
                HttpResponse response = client.execute(post);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuilder sb = new StringBuilder();
                String content = "";
                while ((content = reader.readLine()) != null){
                    sb.append(content);
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null){
                parsePersone(s);
            }else{
                showError();

            }
        }
    }
}
