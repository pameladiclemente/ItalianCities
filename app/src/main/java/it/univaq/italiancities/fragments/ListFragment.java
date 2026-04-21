package it.univaq.italiancities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.univaq.italiancities.R;
import it.univaq.italiancities.model.ItalianCity;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<ItalianCity> data = new ArrayList<>();
    private Adapter adapter = new Adapter(data);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        loadHardcodedCities();
    }

    private void loadHardcodedCities() {

        data.clear();

        data.add(createCity("Roma", "00100", "06", "RM", 41.9028, 12.4964));
        data.add(createCity("Milano", "20100", "02", "MI", 45.4642, 9.1900));
        data.add(createCity("Napoli", "80100", "081", "NA", 40.8518, 14.2681));
        data.add(createCity("Torino", "10100", "011", "TO", 45.0703, 7.6869));
        data.add(createCity("Firenze", "50100", "055", "FI", 43.7696, 11.2558));
        data.add(createCity("Bologna", "40100", "051", "BO", 44.4949, 11.3426));
        data.add(createCity("Venezia", "30100", "041", "VE", 45.4408, 12.3155));
        data.add(createCity("Genova", "16100", "010", "GE", 44.4056, 8.9463));
        data.add(createCity("Palermo", "90100", "091", "PA", 38.1157, 13.3615));
        data.add(createCity("Cagliari", "09100", "070", "CA", 39.2238, 9.1217));
        data.add(createCity("Bari", "70100", "080", "BA", 41.1171, 16.8719));
        data.add(createCity("Perugia", "06100", "075", "PG", 43.1107, 12.3908));
        data.add(createCity("Ancona", "60100", "071", "AN", 43.6158, 13.5189));
        data.add(createCity("Trieste", "34100", "040", "TS", 45.6495, 13.7768));
        data.add(createCity("Aosta", "11100", "0165", "AO", 45.7370, 7.3201));
        data.add(createCity("Trento", "38100", "0461", "TN", 46.0748, 11.1217));
        data.add(createCity("Potenza", "85100", "0971", "PZ", 40.6401, 15.8050));
        data.add(createCity("Catanzaro", "88100", "0961", "CZ", 38.9098, 16.5877));
        data.add(createCity("Campobasso", "86100", "0874", "CB", 41.5595, 14.6680));
        data.add(createCity("L'Aquila", "67100", "0862", "AQ", 42.3498, 13.3995));

        adapter.notifyDataSetChanged();
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