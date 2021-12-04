package it.lucapascucci.rubrica.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import it.lucapascucci.rubrica.R;
import it.lucapascucci.rubrica.model.Persona;
import it.lucapascucci.rubrica.utils.ImageUtils;

import static it.lucapascucci.rubrica.utils.Keys.*;

public class DetailsPersona extends Activity {

    private static final int REQUEST_CODE_MODIFY = 2;
    private TextView name;
    private TextView surname;
    private TextView phone;
    private TextView mail;
    private Persona personaVisualizzata;
    private boolean isEdit = false;
    private Button modify;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Slide slide = new Slide();
            slide.setDuration(3500);
            getWindow().setEnterTransition(slide);
            getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition_main));
        }
        setContentView(R.layout.activity_details_persona);

        phone = (TextView) findViewById(R.id.detailPhone);
        mail = (TextView) findViewById(R.id.detailMail);
        name = (TextView) findViewById(R.id.detailName);
        surname = (TextView) findViewById(R.id.detailSurname);
        modify = (Button) findViewById(R.id.modify);
        delete = (Button) findViewById(R.id.delete);

        if (!phone.getText().toString().trim().isEmpty()){
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.getText().toString())));
                }
            });
        }

        if (!mail.getText().toString().trim().isEmpty()){
            mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+mail.getText().toString())));
                }
            });
        }

        Intent intent = getIntent();
        if (intent != null){
            personaVisualizzata = (Persona) intent.getSerializableExtra(PERSONA_VISUALIZZATA);
            if (personaVisualizzata != null) {
                name.setText(personaVisualizzata.getNome());
                surname.setText(personaVisualizzata.getCognome());
                phone.setText(personaVisualizzata.getTelefono());
                mail.setText(personaVisualizzata.getMail());

            }else{
                personaVisualizzata = new Persona();
            }
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogElimina();
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ADD_OR_EDIT_INTENT);
                intent.putExtra(PERSONA_IN_MODIFICA, personaVisualizzata);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent,REQUEST_CODE_MODIFY);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_MODIFY){
            personaVisualizzata = (Persona) data.getSerializableExtra(PERSONA_NUOVA);
            if (personaVisualizzata instanceof Persona) {
                isEdit = true;
                name.setText(personaVisualizzata.getNome());
                surname.setText(personaVisualizzata.getCognome());
                phone.setText(personaVisualizzata.getTelefono());
                mail.setText(personaVisualizzata.getMail());
            }
        }
    }

    private void showDialogElimina() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.dialog_attenzione).setMessage(R.string.dialog_cancella_persona)
                .setPositiveButton(R.string.dialog_si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(DELETE_ID, personaVisualizzata.getId());
                        setResult(RESULT_DELETE, intent);
                        finish();
                    }
                }).setNegativeButton(R.string.dialog_no, null).show();
    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            Intent intent = new Intent();
            intent.putExtra(PERSONA_MODIFICATA, personaVisualizzata);
            setResult(RESULT_EDIT, intent);
            finish();
        } else
            super.onBackPressed();
    }
}
