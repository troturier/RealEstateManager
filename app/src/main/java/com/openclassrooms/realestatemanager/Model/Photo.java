package com.openclassrooms.realestatemanager.Model;

import android.graphics.Bitmap;

public class Photo {
    private int _id;
    private String description;
    private Bitmap photoMedia;
    private BienImmobilier bienImmobilier;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPhotoMedia() {
        return photoMedia;
    }

    public void setPhotoMedia(Bitmap photoMedia) {
        this.photoMedia = photoMedia;
    }

    public BienImmobilier getBienImmobilier() {
        return bienImmobilier;
    }

    public void setBienImmobilier(BienImmobilier bienImmobilier) {
        this.bienImmobilier = bienImmobilier;
    }
}
