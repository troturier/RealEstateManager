package com.openclassrooms.realestatemanager.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.RealEstateAdapter;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RealEstateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @BindView(R.id.type_TV) TextView typeTV;
    @BindView(R.id.city_TV) public TextView cityTV;
    @BindView(R.id.price_TV) public TextView priceTV;
    @BindView(R.id.imageView) ImageView reIV;

    // FOR DATA
    private WeakReference<RealEstateAdapter.Listener> callbackWeakRef;
    public LinearLayout row_linearlayout;

    public RealEstateViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        row_linearlayout=(LinearLayout)itemView.findViewById(R.id.row_linrLayout);
    }

    public void updateWithRealEstate(BienImmobilierComplete bienImmobilierComplete, RealEstateAdapter.Listener callback){
        if(bienImmobilierComplete.getPhotoCouverture().size() > 0) {
            Picasso.get()
                    .load(new File(bienImmobilierComplete.getPhotoCouverture().get(0).getCheminAcces()))
                    .centerCrop()
                    .fit()
                    .error(R.mipmap.ic_iv_placeholder_no_image)
                    .into(reIV);
        }
        this.typeTV.setText(bienImmobilierComplete.getType().get(0).getLibelle());
        this.cityTV.setText(bienImmobilierComplete.getBienImmobilier().getVille());
        int price = Utils.convertEuroToDollar(bienImmobilierComplete.getBienImmobilier().getPrix());
        this.priceTV.setText("$"+String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(price)));
    }

    @Override
    public void onClick(View view) {
        RealEstateAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
