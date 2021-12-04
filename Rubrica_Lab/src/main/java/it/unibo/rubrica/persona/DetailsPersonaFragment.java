package it.unibo.rubrica.persona;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import it.unibo.rubrica.R;
import it.unibo.rubrica.utils.ImageUtils;

/**
 * Created by Andrea on 02/04/2015.
 */
public class DetailsPersonaFragment extends Fragment {

    public interface DetailsPersonaListener {

        public void onRequestEditPersona(Persona persona);

        public void onRequestDeletePersona(Persona persona);
    }

    private Persona persona;

    private DetailsPersonaListener listener;

    private TextView nomeText, cognomeText, emailText, telefonoText;

    private View view;

    private ImageView imgPersona;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailsPersonaListener)
            listener = (DetailsPersonaListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details_persona, container, false);

        nomeText = (TextView) view.findViewById(R.id.persona_nome);

        cognomeText = (TextView) view.findViewById(R.id.persona_cognome);

        telefonoText = (TextView) view.findViewById(R.id.persona_telefono);

        emailText = (TextView) view.findViewById(R.id.persona_email);

        imgPersona = (ImageView) view.findViewById(R.id.persona_image);

        view.findViewById(R.id.persona_elimina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogElimina();
            }
        });

        view.findViewById(R.id.persona_modifica).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificaPersona();
            }
        });

        MapFragment mappa = new MapFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_persona_mappa, mappa);
        transaction.commit();

        populatePersona();

        return view;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
        populatePersona();
    }

    private void populatePersona() {
        if (view == null || persona == null)
            return;

        String nome = persona != null ? persona.getNome() : null;
        View view1 = view.findViewById(R.id.layout_persona_nome);
        if (nome != null && !nome.isEmpty()) {
            nomeText.setText(nome);
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        } else {
            if (view.getVisibility() != View.GONE)
                view.setVisibility(View.GONE);
        }

        String cognome = persona != null ? persona.getCognome() : null;
        view1 = view.findViewById(R.id.layout_persona_cognome);
        if (cognome != null && !cognome.isEmpty()) {
            cognomeText.setText(cognome);
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        } else {
            if (view.getVisibility() != View.GONE)
                view.setVisibility(View.GONE);
        }

        String telefono = persona != null ? persona.getTelefono() : null;
        view1 = view.findViewById(R.id.layout_persona_telefono);
        if (telefono != null && !telefono.isEmpty()) {
            telefonoText.setText(telefono);
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        } else {
            if (view.getVisibility() != View.GONE)
                view.setVisibility(View.GONE);
        }

        String email = persona != null ? persona.getEmail() : null;
        view1 = view.findViewById(R.id.layout_persona_email);
        if (email != null && !email.isEmpty()) {
            emailText.setText(email);
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        } else {
            if (view.getVisibility() != View.GONE)
                view.setVisibility(View.GONE);
        }

        String path = persona != null ? persona.getImage() : null;
        if (path != null && !path.isEmpty()) {
            float width = ImageUtils.convertDpToPixel(getActivity().getResources().getDimension(R.dimen.details_img_width), getActivity());
            float height = ImageUtils.convertDpToPixel(getActivity().getResources().getDimension(R.dimen.details_img_height), getActivity());
            imgPersona.setImageBitmap(ImageUtils.decodeSampledBitmapFromMemory(path, (int) width, (int) height));
        }
    }

    private void modificaPersona() {
        if (listener != null)
            listener.onRequestEditPersona(persona);
    }

    private void showDialogElimina() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.dialog_attenzione).setMessage(R.string.dialog_cancella_persona)
                .setPositiveButton(R.string.dialog_si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null)
                            listener.onRequestDeletePersona(persona);
                    }
                }).setNegativeButton(R.string.dialog_no, null).show();
    }
}
