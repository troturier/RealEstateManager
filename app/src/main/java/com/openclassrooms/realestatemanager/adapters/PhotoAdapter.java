package com.openclassrooms.realestatemanager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.viewholders.PhotoViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    // CALLBACK
    public interface Listener { void onClickDeleteButton(int position); }
    private final PhotoAdapter.Listener callback;
    private BienImmobilierComplete bienImmobilierComplete;

    // FOR DATA
    private List<Photo> items;

    // CONSTRUCTOR
    public PhotoAdapter(Listener callback){
        this.items = new ArrayList<>();
        this.bienImmobilierComplete = null;
        this.callback = callback;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detail_recycler_view_item, parent, false);

        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.updateWithPhoto(this.items.get(position), this.callback);
        if(bienImmobilierComplete != null) {
            if (items.get(position).getId() == bienImmobilierComplete.getBienImmobilier().getIdPhotoCouverture())
                holder.detailTV.setBackgroundColor(Color.parseColor("#A8FF0C40"));
            else
                holder.detailTV.setBackgroundColor(Color.parseColor("#A80C0C0C"));
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public Photo getItem(int position){ return this.items.get(position);}

    public void updateData(List<Photo> items, BienImmobilierComplete bienImmobilierComplete){
        this.items = items;
        this.bienImmobilierComplete = bienImmobilierComplete;
        this.notifyDataSetChanged();
    }
}
