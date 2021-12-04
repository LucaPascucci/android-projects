package it.lucapascucci.rubrica.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import it.lucapascucci.rubrica.R;
import it.lucapascucci.rubrica.model.Persona;
import it.lucapascucci.rubrica.utils.AvatarUtils;
import it.lucapascucci.rubrica.utils.ImageUtils;

import static it.lucapascucci.rubrica.utils.Keys.*;

public class AddOrEditPersona extends Activity {

    private ImageView imagePersona;
    private EditText nameET;
    private EditText surnameET;
    private EditText phoneET;
    private EditText mailET;
    private Button save;
    private Persona personaInModifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_persona);

        nameET = (EditText) findViewById(R.id.nomeET);
        surnameET = (EditText) findViewById(R.id.cognomeET);
        phoneET = (EditText) findViewById(R.id.telefonoET);
        mailET = (EditText) findViewById(R.id.mailET);
        save = (Button) findViewById(R.id.save);
        imagePersona = (ImageView) findViewById(R.id.persona_image);

        imagePersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AvatarUtils.scattaFoto(AddOrEditPersona.this);
            }
        });

        Intent intent = getIntent();
        if (intent != null){
            personaInModifica = (Persona) intent.getSerializableExtra(PERSONA_IN_MODIFICA);
            if (personaInModifica != null){
                nameET.setText(personaInModifica.getNome());
                surnameET.setText(personaInModifica.getCognome());
                phoneET.setText(personaInModifica.getTelefono());
                mailET.setText(personaInModifica.getMail());
                setImagePersona(personaInModifica.getImage());
            }else{
                personaInModifica = new Persona();
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameET.getText().toString().trim().isEmpty() || surnameET.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddOrEditPersona.this);
                    builder.setTitle(R.string.dialog_attenzione).setMessage(R.string.dialog_dati_mancanti);
                    builder.setPositiveButton(R.string.dialog_ok, null);
                    builder.create().show();
                }else{
                    Intent resultIntent = new Intent();
                    personaInModifica.setNome(nameET.getText().toString());
                    personaInModifica.setCognome(surnameET.getText().toString());
                    personaInModifica.setTelefono(phoneET.getText().toString());
                    personaInModifica.setMail(mailET.getText().toString());

                    resultIntent.putExtra(PERSONA_NUOVA,personaInModifica);
                    setResult(RESULT_OK, resultIntent);
                    finish();

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AvatarUtils.CAMERA_ACTIVITY ||requestCode == AvatarUtils.GALLERY_ACTIVITY) {
            String path = AvatarUtils.mangeActivityResultScattaFoto(this, requestCode, data);
            if (path != null && !path.trim().isEmpty()) {
                personaInModifica.setImage(path);
                setImagePersona(path);
            } else {
                Toast.makeText(this, R.string.errore_recupero_immagine, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setImagePersona(String path) {
        if (path != null && !path.isEmpty()) {
            float width = ImageUtils.convertDpToPixel(getResources().getDimension(R.dimen.img_size), this);
            float height = ImageUtils.convertDpToPixel(getResources().getDimension(R.dimen.img_size), this);
            imagePersona.setImageBitmap(ImageUtils.decodeSampledBitmapFromMemory(path, (int) width, (int) height));
        }
    }

}
