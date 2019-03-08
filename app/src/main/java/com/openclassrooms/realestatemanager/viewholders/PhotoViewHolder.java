package com.openclassrooms.realestatemanager.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PhotoAdapter;
import com.openclassrooms.realestatemanager.models.Photo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    // FOR UI
    @BindView(R.id.detail_rv_tv) public TextView detailTV;
    @BindView(R.id.detail_rv_iv) ImageView detailIV;

    // FOR DATA
    private WeakReference<PhotoAdapter.Listener> callbackWeakRef;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithPhoto(Photo photo, PhotoAdapter.Listener callback){
        Picasso.get()
                .load(new File(photo.getCheminAcces()))
                .resize(250, 250)
                .centerCrop()
                .error(R.mipmap.ic_iv_placeholder_no_image)
                .into(detailIV);

        detailTV.setText(photo.getDescription());
    }

    @Override
    public void onClick(View v) {
        PhotoAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
