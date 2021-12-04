package it.lucapascucci.rubrica.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Luca on 27/03/15.
 */
public class DataManager {

    private HashMap<String, Persona> persone;

    private static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    private DataManager() {
    }

    public void addPersona(Persona persona) {
        if (persona == null)
            throw new NullPointerException("Persona non può essere null");

        if (persone == null)
            persone = new HashMap<String, Persona>(1);

        persone.put(persona.getId(), persona);
    }

    public boolean deletePersona(Persona persona) {
        if (persona == null)
            throw new NullPointerException("Persona non può essere null");

        return deletePersona(persona.getId());
    }

    public boolean deletePersona(String id) {
        return persone != null && persone.containsKey(id) ? persone.remove(id) != null : false;
    }

    public List<Persona> getPersone() {
        ArrayList<Persona> personeList = new ArrayList<Persona>(0);

        if (persone != null)
            personeList.addAll(persone.values());

        return personeList;
    }
}
