package com.openclassrooms.realestatemanager.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PhotoAdapter;
import com.openclassrooms.realestatemanager.models.Photo;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    // FOR UI
    @BindView(R.id.detail_rv_tv) public TextView detailTV;
    @BindView(R.id.detail_rv_iv) public ImageView detailIV;
    @BindView(R.id.photo_cv) public CardView photoCv;

    // FOR DATA
    private WeakReference<PhotoAdapter.Listener> callbackWeakRef;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithPhoto(Photo photo, PhotoAdapter.Listener callback){

        Glide
                .with(itemView)
                .load(photo.getCheminAcces())
                .error(R.mipmap.error)
                .placeholder(R.mipmap.loading)
                .centerCrop()
                .into(detailIV);

        detailTV.setText(photo.getDescription());
    }

    @SuppressWarnings("unused")
    @Override
    public void onClick(View v) {
        PhotoAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
