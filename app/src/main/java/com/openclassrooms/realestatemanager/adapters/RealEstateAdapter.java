package com.openclassrooms.realestatemanager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.viewholders.RealEstateViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Real Estate Adapter used in RealEstateListFragment
 */
public class RealEstateAdapter extends RecyclerView.Adapter<RealEstateViewHolder> {
    // CALLBACK
    public interface Listener { void onClickDeleteButton(int position); }
    private final Listener callback;

    // FOR DATA
    private List<BienImmobilierComplete> items;
    public int row_index;

    // CONSTRUCTOR
    public RealEstateAdapter(Listener callback) {
        this.items = new ArrayList<>();
        this.callback = callback;
        this.row_index = -1;
    }

    @NonNull
    @Override
    public RealEstateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_recycler_view_item, parent, false);

        return new RealEstateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RealEstateViewHolder viewHolder, int position) {
        viewHolder.updateWithRealEstate(this.items.get(position), this.callback);
        if(row_index==position){
            viewHolder.row_linearlayout.setBackgroundColor(Color.parseColor("#FFFF4081"));
            viewHolder.priceTV.setTextColor(Color.parseColor("#ffffff"));
            viewHolder.cityTV.setTextColor(Color.parseColor("#CFCFCF"));
        }
        else
        {
            viewHolder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
            viewHolder.priceTV.setTextColor(Color.parseColor("#FFFF4081"));
            viewHolder.cityTV.setTextColor(Color.parseColor("#5D5D5E"));
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public BienImmobilierComplete getItem(int position){
        return this.items.get(position);
    }

    public void updateData(List<BienImmobilierComplete> items){
        this.items = items;
        this.notifyDataSetChanged();
    }

}
