package it.lucapascucci.check_in;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Luca on 22/04/15.
 */
public class NewCheckInActivity extends ActionBarActivity implements OnMapReadyCallback{

    private GoogleMap googleMap;
    private ProgressDialog dialogCaricamento;

    private CheckIn checkIn = CheckIn.createNewCheckIn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_checkin);

        dialogCaricamento = ProgressDialog.show(this, "","Caricamento mappa", true, false);

        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(this);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container,mapFragment);
        transaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                checkIn.setLatitudine(latLng.latitude);
                checkIn.setLongitudine(latLng.longitude);
                setMapMaker();
            }
        });
        if (dialogCaricamento != null && dialogCaricamento.isShowing()){
            dialogCaricamento.dismiss();
        }
    }

    private void setMapMaker(){
        googleMap.clear();
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(checkIn.getLatitudine(), checkIn.getLongitudine()));
        marker.title("Posizione selezionata");
        googleMap.addMarker(marker);
    }

    private void salva(){
        if (checkIn.isValid()){
            Intent intent = new Intent();
            intent.putExtra("CHECK_IN",checkIn);
            //setResult(RESULT_OK,);
        }
    }
}
