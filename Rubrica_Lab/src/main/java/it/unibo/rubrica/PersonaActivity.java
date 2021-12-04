package it.unibo.rubrica;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import it.unibo.rubrica.persona.Persona;
import it.unibo.rubrica.utils.ImageUtils;

/**
 * Created by Andrea on 15/04/2015.
 */
public class PersonaActivity extends Activity {

    public static final String PERSONA = "persona";
    Persona persona;
    private TextView nomeText, cognomeText, emailText, telefonoText;


    private ImageView imgPersona;
    private GoogleMap marker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);

        persona = (Persona) getIntent().getSerializableExtra(PERSONA);

        nomeText = (TextView) findViewById(R.id.persona_nome);

        cognomeText = (TextView) findViewById(R.id.persona_cognome);

        telefonoText = (TextView) findViewById(R.id.persona_telefono);

        emailText = (TextView) findViewById(R.id.persona_email);

        imgPersona = (ImageView) findViewById(R.id.persona_image);

        findViewById(R.id.persona_elimina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialogElimina();
            }
        });

        findViewById(R.id.persona_modifica).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //modificaPersona();
            }
        });

        MapFragment mappa = new MapFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_persona_mappa, mappa);
        transaction.commit();

        mappa.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setMarker(googleMap);
            }
        });

        pouplatePersona();
    }

    private void pouplatePersona() {
        if (persona == null)
            return;

        String nome = persona != null ? persona.getNome() : null;
        if (nome != null && !nome.isEmpty()) {
            nomeText.setText(nome);

        } else {
        }

        String cognome = persona != null ? persona.getCognome() : null;
        if (cognome != null && !cognome.isEmpty())
            cognomeText.setText(cognome);

        String telefono = persona != null ? persona.getTelefono() : null;
        if (telefono != null && !telefono.isEmpty())
            telefonoText.setText(telefono);

        String email = persona != null ? persona.getEmail() : null;
        if (email != null && !email.isEmpty())
            emailText.setText(email);

        String path = persona != null ? persona.getImage() : null;
        if (path != null && !path.isEmpty()) {
            float width = ImageUtils.convertDpToPixel(getResources().getDimension(R.dimen.details_img_width), this);
            float height = ImageUtils.convertDpToPixel(getResources().getDimension(R.dimen.details_img_height), this);
            imgPersona.setImageBitmap(ImageUtils.decodeSampledBitmapFromMemory(path, (int) width, (int) height));
        }
    }

    public void setMarker(GoogleMap marker) {
        this.marker = marker;

        new GetPersonaLocation().execute();

        marker.getUiSettings().setAllGesturesEnabled(false);
        marker.getUiSettings().setMyLocationButtonEnabled(false);
        marker.getUiSettings().setZoomControlsEnabled(false);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(44.1390945, 12.2429281));
        marker.addMarker(markerOptions);

        marker.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(44.1390945, 12.2429281),
                15, 0, 0)));
    }

    protected class GetPersonaLocation extends AsyncTask<Void, Void, Address> {

        @Override
        protected Address doInBackground(Void... params) {
            List<Address> addresses = null;
            try {
                Geocoder geocoder = new Geocoder(PersonaActivity.this, Locale.getDefault());
                addresses = geocoder.getFromLocationName(persona.getIndirizzo(), 1);
            } catch (Exception e1) {}

            return addresses != null && addresses.size() > 0 ? addresses.get(0) : null;
        }

        @Override
        protected void onPostExecute(Address address) {
            super.onPostExecute(address);
            if (address != null && marker != null) {
                LatLng pos = new LatLng(address.getLatitude(), address.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(pos);
                marker.addMarker(markerOptions);

                marker.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(pos,
                        15, 0, 0)));
            }
        }
    }
}
