package it.unibo.rubrica.persona;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import it.unibo.rubrica.R;

/**
 * Created by Andrea on 01/04/2015.
 */
public class PersoneFragment extends Fragment {

    public interface PersoneListener {

        /**
         * Quando l'utente clicca sul pulsante aggiungi persona
         */
        public void onRequestNuovaPersona();

        /**
         * Quando l'utente clicca su una persona della lista
         * @param persona persona premuta
         */
        public void onPersonaClicked(Persona persona);

    }

    private PersoneListener listener;

    private ListView listView;

    private List<Persona> persone;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PersoneListener)
            listener = (PersoneListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persone, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null)
                    listener.onPersonaClicked((Persona) parent.getItemAtPosition(position));
            }
        });

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onRequestNuovaPersona();
            }
        });

        if (persone != null) {
            PersonaAdapter adapter = new PersonaAdapter(getActivity(), persone);
            listView.setAdapter(adapter);
        }

        return view;
    }

    public void setPersone(List<Persona> persone) {
        this.persone = persone;
        if (listView != null) {
            PersonaAdapter adapter = new PersonaAdapter(getActivity(), persone);
            listView.setAdapter(adapter);
        }
    }
}
