package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.Photo;

import java.util.List;

public interface PhotoDao {
    @Query("SELECT * FROM Photo WHERE _id = :_id")
    LiveData<List<Photo>> getPhotos(int _id);

    @Insert
    int insertPhoto(Photo photo);

    @Update
    int updatePhoto(Photo photo);

    @Query("DELETE FROM Photo WHERE _id = :photoId")
    int deleteItem(int photoId);
}
