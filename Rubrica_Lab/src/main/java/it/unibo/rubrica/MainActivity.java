package it.unibo.rubrica;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import it.unibo.rubrica.persona.AddOrEditPersonaActivity;
import it.unibo.rubrica.persona.DetailsPersonaFragment;
import it.unibo.rubrica.persona.Persona;
import it.unibo.rubrica.persona.PersonaAdapter;
import it.unibo.rubrica.persona.PersoneFragment;
import it.unibo.rubrica.utils.FileUtils;


public class MainActivity extends ActionBarActivity implements PersoneFragment.PersoneListener,
        DetailsPersonaFragment.DetailsPersonaListener {

    private static final int ACTIVITY_ADD_EDIT_PERSONA = 101;
    private static final int ACTIVITY_DETAILS_PERSONA = 102;

    private static final String PERSONE = "persone";

    private static final String FRAG_PERSONE = "fragPersone";
    private static final String FRAG_PERSONA = "fragPersona";

    private ListView listView;

    private PersonaAdapter adapter;

    private boolean isShowDetails = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPersone();

        showListPersone();
    }

    private void loadPersone() {
        String data = FileUtils.readFromFile(PERSONE, this);
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
                            Log.e("loadPersone", "Errore durante il parse del json persona", e);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("loadPersone", "Errore durante il parse della lista delle persone", e);
            }
        }
    }

    private void savePersone() {
        JSONArray array = new JSONArray();
        List<Persona> persone = DataManager.getInstance().getPersone();
        if (persone != null) {
            for (Persona persona : persone) {
                try {
                    array.put(persona.toJSONObjcet());
                } catch (Exception e) {
                    Log.e("savePersone", "Errore durante l'aggiunta di una persona all'array", e);
                }
            }
        }

        FileUtils.writeToFile(array.toString(), PERSONE, this);
    }

    /**
     * Lancia l'activity per aggiungere o editare una persona
     *
     * @param persona
     *         può essere null
     */
    private void addOrEditPersona(Persona persona) {
        Intent intent = new Intent(this, AddOrEditPersonaActivity.class);
        if (persona != null)
            intent.putExtra(AddOrEditPersonaActivity.PERSONA, persona);
        startActivityForResult(intent, ACTIVITY_ADD_EDIT_PERSONA);
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

        /*DetailsPersonaFragment fragment  = new DetailsPersonaFragment();
        fragment.setPersona(persona);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment, FRAG_PERSONA);
            transaction.commit();

            isShowDetails = true;*/

        Intent intent = new Intent(this, PersonaActivity.class);
        intent.putExtra(PersonaActivity.PERSONA, persona);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        if (isShowDetails) {
            showListPersone();
        } else
            super.onBackPressed();
    }

    private void showListPersone() {
        PersoneFragment fragment = (PersoneFragment) getFragmentManager().findFragmentByTag(FRAG_PERSONE);
        if (fragment == null) {
            fragment = new PersoneFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment, FRAG_PERSONE);
            transaction.commit();

            isShowDetails = false;
        }
        fragment.setPersone(DataManager.getInstance().getPersone());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_ADD_EDIT_PERSONA && resultCode == RESULT_OK) {
            Persona persona = data != null ? (Persona) data.getSerializableExtra(AddOrEditPersonaActivity.PERSONA) : null;
            addPersonaToManager(persona);
        }

        showListPersone();
    }

    /**
     * Aggiunge una persona al manager
     *
     * @param persona
     */
    protected void addPersonaToManager(Persona persona) {
        if (persona != null) {
            DataManager.getInstance().addPersona(persona);
            Toast.makeText(this, R.string.toast_persona_salvata, Toast.LENGTH_LONG).show();
            savePersone();
        }
    }

    /**
     * Inizializza o aggiorna la lista delle persone sulla listView
     */
    protected void populateListView() {
        if (adapter == null) {
            adapter = new PersonaAdapter(this, DataManager.getInstance().getPersone());
            listView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(DataManager.getInstance().getPersone());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestNuovaPersona() {
        addOrEditPersona(null);
    }

    @Override
    public void onPersonaClicked(Persona persona) {
        showPersona(persona);
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
}