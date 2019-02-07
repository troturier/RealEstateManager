package com.openclassrooms.realestatemanager.viewholders;

import android.view.View;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.RealEstateAdapter;
import com.openclassrooms.realestatemanager.models.BienImmobilier;

import java.lang.ref.WeakReference;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RealEstateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @BindView(R.id.type_TV) TextView typeTV;
    @BindView(R.id.city_TV) TextView cityTV;
    @BindView(R.id.price_TV) TextView priceTV;

    // FOR DATA
    private WeakReference<RealEstateAdapter.Listener> callbackWeakRef;

    public RealEstateViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void updateWithRealEstate(BienImmobilier bienImmobilier, RealEstateAdapter.Listener callback){

        this.callbackWeakRef = new WeakReference<RealEstateAdapter.Listener>(callback);

        this.typeTV.setText(bienImmobilier.getIdType());
        this.cityTV.setText(bienImmobilier.getVille());
        this.priceTV.setText(bienImmobilier.getPrix());
    }


    @Override

    public void onClick(View view) {
        RealEstateAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
