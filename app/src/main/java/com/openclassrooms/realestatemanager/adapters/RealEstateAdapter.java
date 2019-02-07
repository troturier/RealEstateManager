package com.openclassrooms.realestatemanager.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.viewholders.RealEstateViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RealEstateAdapter extends RecyclerView.Adapter<RealEstateViewHolder> {
    // CALLBACK
    public interface Listener { void onClickDeleteButton(int position); }
    private final Listener callback;

    // FOR DATA
    private List<BienImmobilier> items;

    // CONSTRUCTOR
    public RealEstateAdapter(Listener callback) {
        this.items = new ArrayList<>();
        this.callback = callback;
    }

    @Override
    public RealEstateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_recycler_view_item, parent, false);

        return new RealEstateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RealEstateViewHolder viewHolder, int position) {
        viewHolder.updateWithRealEstate(this.items.get(position), this.callback);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public BienImmobilier getItem(int position){
        return this.items.get(position);
    }

    public void updateData(List<BienImmobilier> items){
        this.items = items;
        this.notifyDataSetChanged();
    }

}
