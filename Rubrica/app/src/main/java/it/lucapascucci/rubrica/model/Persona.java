package it.lucapascucci.rubrica.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

import static it.lucapascucci.rubrica.utils.Keys.*;


/**
 * Created by Luca on 26/03/15.
 */
public class Persona implements Serializable {

    private static final long serialVersionUID = -1112127744448386036L;

    private String id;
    private String nome;
    private String cognome;
    private String telefono;
    private String mail;
    private String image;

    public Persona() {
        id = UUID.randomUUID().toString();
    }

    public Persona(JSONObject json) throws NullPointerException, JSONException {
        if (json == null)
            throw new NullPointerException("JSONObject non può essere null");

        setId(json.getString(ID));
        setNome(json.optString(NOME));
        setCognome(json.optString(COGNOME));
        setImage(json.optString(IMAGE));
        setTelefono(json.optString(TELEFONO));
        setMail(json.optString(EMAIL));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public JSONObject toJSONObjcet() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(NOME, nome);
        json.put(COGNOME, cognome);
        json.put(EMAIL, mail);
        json.put(IMAGE, image);
        json.put(TELEFONO, telefono);
        json.put(ID, id);
        return json;
    }
}
