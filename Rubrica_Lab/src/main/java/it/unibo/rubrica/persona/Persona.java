package it.unibo.rubrica.persona;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Andrea on 24/03/2015.
 */
public class Persona implements Serializable {

    private static final long serialVersionUID = -1112127744448386036L;

    private static final String NOME = "nome";

    private static final String COGNOME = "cognome";

    private static final String IMAGE = "image";

    private static final String TELEFONO = "telefono";

    private static final String EMAIL = "email";

    private static final String ID = "id";

    private static final String INDIRIZZO = "indirizzo";

    private String nome;

    private String cognome;

    private String image;

    private String telefono;

    private String email;

    private String id;

    private String indirizzo;

    public Persona(JSONObject json) throws NullPointerException, JSONException {
        if (json == null)
            throw new NullPointerException("JSONObject non può essere null");

        setId(json.getString(ID));
        setNome(json.optString(NOME));
        setCognome(json.optString(COGNOME));
        setImage(json.optString(IMAGE));
        setTelefono(json.optString(TELEFONO));
        setEmail(json.optString(EMAIL));
        setIndirizzo(json.optString(INDIRIZZO));
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Persona() {
        id = UUID.randomUUID().toString();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONObject toJSONObjcet() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(NOME, nome);
        json.put(COGNOME, cognome);
        json.put(EMAIL, email);
        json.put(IMAGE, image);
        json.put(TELEFONO, telefono);
        json.put(ID, id);
        return json;
    }
}
