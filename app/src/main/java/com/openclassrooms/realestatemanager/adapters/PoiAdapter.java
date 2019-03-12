package com.openclassrooms.realestatemanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.PointInteret;
import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;
import com.openclassrooms.realestatemanager.viewmodels.BienImmobilierViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PoiAdapter extends RecyclerView.Adapter<PoiAdapter.ViewHolder>{

    public interface Listener { void onClickDeleteButton(int position); }
    private final PoiAdapter.Listener callback;
    private List<PointInteret> items;
    private BienImmobilierComplete bienImmobilierComplete;
    private BienImmobilierViewModel bienImmobilierViewModel;

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox poi;

        public ViewHolder(View itemView) {
            super(itemView);
            poi = itemView.findViewById(R.id.poi);

            poi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    PointInteretBienImmobilier pointInteretBienImmobilier = new PointInteretBienImmobilier();
                    pointInteretBienImmobilier.setIdBien(bienImmobilierComplete.getBienImmobilier().getId());
                    pointInteretBienImmobilier.setIdPoi(items.get(getAdapterPosition()).getId());
                    if(isChecked){
                        bienImmobilierViewModel.createPointInteretBienImmobilier(pointInteretBienImmobilier);
                        List<PointInteretBienImmobilier> pointInteretBienImmobiliers = bienImmobilierComplete.getPointInteretBienImmobiliers();
                        pointInteretBienImmobiliers.add(pointInteretBienImmobilier);
                        bienImmobilierComplete.setPointInteretBienImmobiliers(pointInteretBienImmobiliers);
                    }
                    else {
                        bienImmobilierViewModel.deletePointInteretBienImmobilier(pointInteretBienImmobilier);
                        List<PointInteretBienImmobilier> pointInteretBienImmobiliers = bienImmobilierComplete.getPointInteretBienImmobiliers();
                        pointInteretBienImmobiliers.remove(pointInteretBienImmobilier);
                        bienImmobilierComplete.setPointInteretBienImmobiliers(pointInteretBienImmobiliers);
                    }
                }
            });
        }
    }

    public PoiAdapter(Listener callback, BienImmobilierViewModel bienImmobilierViewModel){
        this.items = new ArrayList<>();
        this.bienImmobilierComplete = null;
        this.bienImmobilierViewModel = bienImmobilierViewModel;
        this.callback = callback;
    }

    public void updateData(List<PointInteret> pointInteretList, BienImmobilierComplete bienImmobilierComplete){
        this.items = pointInteretList;
        this.bienImmobilierComplete = bienImmobilierComplete;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.poi_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PoiAdapter.ViewHolder holder, final int i) {
        int x = holder.getLayoutPosition();

        boolean found = false;
        for (PointInteretBienImmobilier poi1 : bienImmobilierComplete.getPointInteretBienImmobiliers()) {
            if (items.get(x).getId() == poi1.getIdPoi()) {
                found = true;
            }
        }
        if (found) {
            holder.poi.setChecked(true);
        }
        holder.poi.setText(items.get(x).getLibelle());
    }

    public List<PointInteret> getItemsList(){
        return items;
    }
}
