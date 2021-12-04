package it.unibo.rubrica.persona;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import it.unibo.rubrica.R;
import it.unibo.rubrica.utils.AvatarUtils;
import it.unibo.rubrica.utils.ImageUtils;

/**
 * Created by Andrea on 24/03/2015.
 */
public class AddOrEditPersonaActivity extends Activity {

    public static final String PERSONA = "persona";

    private Persona persona;

    private EditText nomeEditText, cognomeEditText, emailEditText, telefonoEditText, indirizzoEditText;

    private ImageView imgPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_persona);

        nomeEditText = (EditText) findViewById(R.id.persona_nome);
        cognomeEditText = (EditText) findViewById(R.id.persona_cognome);
        emailEditText = (EditText) findViewById(R.id.persona_email);
        telefonoEditText = (EditText) findViewById(R.id.persona_telefono);
        imgPersona = (ImageView) findViewById(R.id.persona_image);
        indirizzoEditText = (EditText) findViewById(R.id.persona_indirizzo);

        persona = (Persona) getIntent().getSerializableExtra(PERSONA);
        if (persona == null)
            persona = new Persona();
        else
            populateData();

        imgPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AvatarUtils.scattaFoto(AddOrEditPersonaActivity.this);
            }
        });

        findViewById(R.id.persona_salva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataAndSalva();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AvatarUtils.CAMERA_ACTIVITY ||requestCode == AvatarUtils.GALLERY_ACTIVITY) {
            String path = AvatarUtils.mangeActivityResultScattaFoto(this, requestCode, data);
            if (path != null && !path.trim().isEmpty()) {
                persona.setImage(path);
                setImagePersona(path);
            } else {
                Toast.makeText(this, R.string.errore_recupero_immagine, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void populateData() {
        String nome = persona != null ? persona.getNome() : null;
        if (nome != null && !nome.isEmpty())
            nomeEditText.setText(nome);

        String cognome = persona != null ? persona.getCognome() : null;
        if (cognome != null && !cognome.isEmpty())
            cognomeEditText.setText(cognome);

        String telefono = persona != null ? persona.getTelefono() : null;
        if (telefono != null && !telefono.isEmpty())
            telefonoEditText.setText(telefono);

        String email = persona != null ? persona.getEmail() : null;
        if (email != null && !email.isEmpty())
            emailEditText.setText(email);

        setImagePersona(persona != null ? persona.getImage() : null);
    }

    private void setImagePersona(String path) {
        if (path != null && !path.isEmpty()) {
            float width = ImageUtils.convertDpToPixel(getResources().getDimension(R.dimen.img_size), this);
            float height = ImageUtils.convertDpToPixel(getResources().getDimension(R.dimen.img_size), this);
            imgPersona.setImageBitmap(ImageUtils.decodeSampledBitmapFromMemory(path, (int) width, (int) height));
            //imgPersona.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    protected void checkDataAndSalva() {
        String nome = nomeEditText.getText().toString();
        String cognome = cognomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String telefono = telefonoEditText.getText().toString();
        String indirizzo = indirizzoEditText.getText().toString();

        if (!nome.trim().isEmpty() && !cognome.trim().isEmpty()) {
            persona.setEmail(email.trim());
            persona.setTelefono(telefono.trim());
            persona.setNome(nome.trim());
            persona.setCognome(cognome.trim());
            persona.setIndirizzo(indirizzo.trim());
            goBackSuccess();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.dialog_attenzione).setMessage(R.string.dialog_dati_mancanti)
                    .setPositiveButton(R.string.dialog_ok, null).show();
        }
    }

    protected void goBackSuccess() {
        Intent intent = new Intent();
        intent.putExtra(PERSONA, persona);
        setResult(RESULT_OK, intent);
        finish();
    }
}