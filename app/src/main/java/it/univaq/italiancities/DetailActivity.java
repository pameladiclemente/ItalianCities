package it.univaq.italiancities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import it.univaq.italiancities.model.ItalianCity;

public class DetailActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private ItalianCity city;

    private GoogleMap map;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try {
            city = (ItalianCity) getIntent().getSerializableExtra("city");
            setupTextView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.singleFragmentMap);
        mapFragment.getMapAsync(this);

    }


    private void setupTextView() {
        ((TextView) findViewById(R.id.textTitle)).setText(city.getNome());
        ((TextView) findViewById(R.id.textCode)).setText(city.getCode());
        ((TextView) findViewById(R.id.textCap)).setText(city.getCap());
        ((TextView) findViewById(R.id.textPrefisso)).setText(city.getPrefisso());
        ((TextView) findViewById(R.id.textProvincia)).setText(city.getProvinceCode());


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng cityLocation = new LatLng(city.getLatitude(), city.getLongitude());
        map.addMarker(new MarkerOptions()
                .position(cityLocation)
                .title(city.getNome()));

        enableMyLocation();

        cityLocation = new LatLng(city.getLatitude(), city.getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 10));

    }




    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            Toast.makeText(this, "Impossibile visualizzare: non hai attivato la localizzazione", Toast.LENGTH_LONG);
        }
    }


    public void onZoom(View view){
        if(view.getId() == R.id.zoomIn) {
            map.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId() == R.id.zoomOut){
            map.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }



    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}
