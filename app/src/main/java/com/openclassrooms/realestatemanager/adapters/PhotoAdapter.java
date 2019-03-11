package com.openclassrooms.realestatemanager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private final boolean editactivity;
    private final Context context;

    // FOR DATA
    private List<Photo> items;

    // CONSTRUCTOR
    public PhotoAdapter(Listener callback, boolean editactivity, Context context){
        this.items = new ArrayList<>();
        this.bienImmobilierComplete = null;
        this.editactivity = editactivity;
        this.callback = callback;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.detail_recycler_view_item, parent, false);

        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        if(position == items.size()){
                holder.detailIV.setImageDrawable(this.context.getDrawable(R.drawable.ic_add_media));
                holder.detailTV.setVisibility(View.GONE);
                holder.detailIV.setScaleType(ImageView.ScaleType.CENTER);
                holder.photoCv.setCardBackgroundColor(this.context.getColor(R.color.colorAccent));
        }
        else {
            holder.updateWithPhoto(this.items.get(position), this.callback);
            holder.detailTV.setVisibility(View.VISIBLE);
            holder.detailIV.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.photoCv.setCardBackgroundColor(0x00000000);
            if (bienImmobilierComplete != null) {
                if (items.get(position).getId() == bienImmobilierComplete.getBienImmobilier().getIdPhotoCouverture())
                    holder.detailTV.setBackgroundColor(Color.parseColor("#A8FF0C40"));
                else
                    holder.detailTV.setBackgroundColor(Color.parseColor("#A80C0C0C"));
            }
        }
    }

    @Override
    public int getItemCount() {
        if(editactivity)
            return this.items.size()+1;
        else
            return this.items.size();
    }

    public Photo getItem(int position){ return this.items.get(position);}

    public void updateData(List<Photo> items, BienImmobilierComplete bienImmobilierComplete){
        this.items = items;
        this.bienImmobilierComplete = bienImmobilierComplete;
        this.notifyDataSetChanged();
    }
}
