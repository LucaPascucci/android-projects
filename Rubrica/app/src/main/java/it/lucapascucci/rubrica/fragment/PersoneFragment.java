package it.lucapascucci.rubrica.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import it.lucapascucci.rubrica.R;
import it.lucapascucci.rubrica.adapter.AdapterPersona;
import it.lucapascucci.rubrica.model.Persona;

/**
 * Created by Luca on 01/04/15.
 */
public class PersoneFragment extends Fragment{

    public interface PersoneListener{
        /**
         *
         * @param persona
         */
        public void onPersonaClicked(Persona persona);

        /**
         * Quando l'utente clicca sul pussante nuova persona
         */
        public void onRequestNuovaPersona();
    }

    private PersoneListener listener;

    private ListView listView;
    private Button button;
    private List<Persona> persone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persone, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Persona persona = (Persona) parent.getItemAtPosition(position);
                onPersonaClicked(persona);
            }
        });

        button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNuovaPersona();
            }


        });

        if (this.persone != null){
            AdapterPersona adapter = new AdapterPersona(getActivity(), persone);
            listView.setAdapter(adapter);
        }

        return view;
    }

    private void addNuovaPersona() {
        if (listener != null){
            listener.onRequestNuovaPersona();
        }
    }

    private void onPersonaClicked(Persona persona){
        if (listener != null){
            listener.onPersonaClicked(persona);
        }
    }

    public void setPersone(List<Persona> persone){
            this.persone = persone;
            if (listView != null) {
                AdapterPersona adapter = new AdapterPersona(getActivity(), persone);
                listView.setAdapter(adapter);
            }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PersoneListener){
            listener = (PersoneListener) activity;
        }
    }
}
