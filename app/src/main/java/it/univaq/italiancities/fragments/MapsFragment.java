package it.univaq.italiancities.fragments;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import it.univaq.italiancities.R;
import it.univaq.italiancities.model.ItalianCity;
import it.univaq.italiancities.utility.LocationHelper;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap map;
    private boolean firstLoad = true;
    private Marker myMarker;
    private List<Marker> cityMarker = new ArrayList<>();
    private LocationHelper locationHelper;

    private ActivityResultLauncher<String> launcher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if(result) {
                    locationHelper.start(MapsFragment.this);
                } else {
                    Toast.makeText(requireContext(), "Attiva la localizzazione", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        locationHelper = new LocationHelper(requireContext(), launcher);
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragmentMap);

        if (fragment != null) {
            fragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        // UI controlli
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(true);

        // Click marker → vai a dettaglio
        map.setOnMarkerClickListener(marker -> {
            ItalianCity city = (ItalianCity) marker.getTag();

            if (city != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("city", city);

                Navigation.findNavController(requireView())
                        .navigate(R.id.action_mapsFragment_to_detailActivity, bundle);

                return true;
            }
            return false;
        });

        // Popup info personalizzato
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                ItalianCity city = (ItalianCity) marker.getTag();
                if (city == null) return null;

                View view = getLayoutInflater().inflate(R.layout.map_info_window, null);

                TextView title = view.findViewById(R.id.title);
                TextView details = view.findViewById(R.id.details);

                title.setText(city.getNome());
                details.setText(
                        "CAP: " + city.getCap() + "\n" +
                                "Provincia: " + city.getProvinceCode()
                );

                return view;
            }
        });

        locationHelper.start(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        load(location);
    }

    private void load(Location location){

        addMyMarker(location);

        for(Marker m : cityMarker) {
            m.remove();
        }
        cityMarker.clear();

        // 🔥 DATI HARDCODED (coerenti con ListFragment)
        List<ItalianCity> citiesList = getCities();

        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        boolean hasPoints = false;

        for(ItalianCity city: citiesList) {

            LatLng latLng = new LatLng(city.getLatitude(), city.getLongitude());

            MarkerOptions options = new MarkerOptions()
                    .title(city.getNome())
                    .position(latLng);

            Marker marker = map.addMarker(options);
            marker.setTag(city);
            cityMarker.add(marker);

            bounds.include(latLng);
            hasPoints = true;
        }

        if (firstLoad) {
            if (hasPoints) {
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 200));
            } else {
                LatLng italy = new LatLng(41.8719, 12.5674);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(italy, 5));
            }
            firstLoad = false;
        }
    }

    private void addMyMarker(Location location) {

        LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());

        if(myMarker == null) {
            MarkerOptions options = new MarkerOptions()
                    .title("My Location")
                    .position(myPosition)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            myMarker = map.addMarker(options);
        } else {
            myMarker.setPosition(myPosition);
        }
    }

    private List<ItalianCity> getCities() {
        List<ItalianCity> list = new ArrayList<>();

        list.add(createCity("Roma", "00100", "06", "RM", 41.9028, 12.4964));
        list.add(createCity("Milano", "20100", "02", "MI", 45.4642, 9.1900));
        list.add(createCity("Napoli", "80100", "081", "NA", 40.8518, 14.2681));
        list.add(createCity("Torino", "10100", "011", "TO", 45.0703, 7.6869));
        list.add(createCity("Firenze", "50100", "055", "FI", 43.7696, 11.2558));
        list.add(createCity("Bologna", "40100", "051", "BO", 44.4949, 11.3426));
        list.add(createCity("Venezia", "30100", "041", "VE", 45.4408, 12.3155));
        list.add(createCity("Genova", "16100", "010", "GE", 44.4056, 8.9463));
        list.add(createCity("Palermo", "90100", "091", "PA", 38.1157, 13.3615));
        list.add(createCity("Cagliari", "09100", "070", "CA", 39.2238, 9.1217));
        list.add(createCity("Bari", "70100", "080", "BA", 41.1171, 16.8719));
        list.add(createCity("Perugia", "06100", "075", "PG", 43.1107, 12.3908));
        list.add(createCity("Ancona", "60100", "071", "AN", 43.6158, 13.5189));
        list.add(createCity("Trieste", "34100", "040", "TS", 45.6495, 13.7768));
        list.add(createCity("Aosta", "11100", "0165", "AO", 45.7370, 7.3201));
        list.add(createCity("Trento", "38100", "0461", "TN", 46.0748, 11.1217));
        list.add(createCity("Potenza", "85100", "0971", "PZ", 40.6401, 15.8050));
        list.add(createCity("Catanzaro", "88100", "0961", "CZ", 38.9098, 16.5877));
        list.add(createCity("Campobasso", "86100", "0874", "CB", 41.5595, 14.6680));
        list.add(createCity("L'Aquila", "67100", "0862", "AQ", 42.3498, 13.3995));

        return list;
    }

    private ItalianCity createCity(String nome, String cap, String prefisso, String provincia, double lat, double lng) {
        ItalianCity city = new ItalianCity();
        city.setNome(nome);
        city.setCap(cap);
        city.setPrefisso(prefisso);
        city.setProvinceCode(provincia);
        city.setLatitude(lat);
        city.setLongitude(lng);
        city.setCode(provincia);
        return city;
    }
}