package com.openclassrooms.realestatemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
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

    // FOR DATA
    private List<Photo> items;

    // CONSTRUCTOR
    public PhotoAdapter(Listener callback){
        this.items = new ArrayList<>();
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
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public Photo getItem(int position){ return this.items.get(position);}

    public void updateData(List<Photo> items){
        this.items = items;
        this.notifyDataSetChanged();
    }
}
