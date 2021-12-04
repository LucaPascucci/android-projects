package it.lucapascucci.rubrica.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import it.lucapascucci.rubrica.R;
import it.lucapascucci.rubrica.model.Persona;
import it.lucapascucci.rubrica.utils.ImageUtils;

/**
 * Created by Luca on 03/04/15.
 */
public class DetailsPersonaFragment extends Fragment{

    public interface DetailsPersonaListenter{

        public void onRequestEditPersona(Persona persona);

        public void onRequestDeletePersona(Persona persona);

    }
    private Persona personaVisualizzata;

    private View view;

    private ImageView imgPersona;
    private TextView name;
    private TextView surname;
    private TextView phone;
    private TextView mail;
    private Button modify;
    private Button delete;

    private DetailsPersonaListenter listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailsPersonaListenter){
            listener = (DetailsPersonaListenter) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_details_persona,container,false);
        imgPersona = (ImageView) view.findViewById(R.id.detailImage);
        phone = (TextView) view.findViewById(R.id.detailPhone);
        mail = (TextView) view.findViewById(R.id.detailMail);
        name = (TextView) view.findViewById(R.id.detailName);
        surname = (TextView) view.findViewById(R.id.detailSurname);
        modify = (Button) view.findViewById(R.id.modify);
        delete = (Button) view.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogElimina();
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificaPersona();
            }
        });

        populatePersona();

        return view;
    }

    public void setPersonaVisualizzata(Persona personaVisualizzata){
        this.personaVisualizzata = personaVisualizzata;
        populatePersona();
    }

    public void populatePersona(){

        if (personaVisualizzata != null) {
            if (name != null){
                name.setText(personaVisualizzata.getNome());
            }
            if (surname != null){
                surname.setText(personaVisualizzata.getCognome());
            }
            if (phone != null){
                phone.setText(personaVisualizzata.getTelefono());
            }
            if (mail != null){
                mail.setText(personaVisualizzata.getMail());
            }
            String path = personaVisualizzata.getImage();
            if (path != null && !path.isEmpty()) {
                float width = ImageUtils.convertDpToPixel(getActivity().getResources().getDimension(R.dimen.details_img_width), getActivity());
                float height = ImageUtils.convertDpToPixel(getActivity().getResources().getDimension(R.dimen.details_img_height), getActivity());
                imgPersona.setImageBitmap(ImageUtils.decodeSampledBitmapFromMemory(path, (int) width, (int) height));
            }
        }
    }

    private void modificaPersona() {
        if (listener != null)
            listener.onRequestEditPersona(personaVisualizzata);
    }

    private void showDialogElimina() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.dialog_attenzione).setMessage(R.string.dialog_cancella_persona)
                .setPositiveButton(R.string.dialog_si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null)
                            listener.onRequestDeletePersona(personaVisualizzata);
                    }
                }).setNegativeButton(R.string.dialog_no, null).show();
    }
}
