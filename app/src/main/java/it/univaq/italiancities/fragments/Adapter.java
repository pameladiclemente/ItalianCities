package it.univaq.italiancities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.univaq.italiancities.R;
import it.univaq.italiancities.model.ItalianCity;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ItalianCity> data;

    public Adapter(List<ItalianCity> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_city, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title;
        private final TextView subtitle;

        public ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.textTitle);
            subtitle = view.findViewById(R.id.textSubtitle);

            view.findViewById(R.id.layoutRoot).setOnClickListener(this); //liner layout dell'adapter
        }

        public void onBind(ItalianCity italianCities) {
            title.setText(italianCities.getNome());
            subtitle.setText(italianCities.getCap());

        }

        @Override
        public void onClick(View view) {
            ItalianCity city = data.get(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putSerializable("city", city);
            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_detailActivity, bundle);

        }

    }
}
