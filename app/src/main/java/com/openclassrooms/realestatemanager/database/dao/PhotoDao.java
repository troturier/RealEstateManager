package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Photo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PhotoDao {
    @Query("SELECT * FROM Photo WHERE idBien = :idBien")
    LiveData<List<Photo>> getPhotos(int idBien);

    @Query("SELECT * FROM Photo WHERE id = :id")
    LiveData<Photo> getPhoto(int id);

    @Query("SELECT * FROM Photo ORDER BY id DESC LIMIT 1")
    LiveData<Photo> getLastPhoto();

    @Insert
    long insertPhoto(Photo photo);

    @Update
    int updatePhoto(Photo photo);

    @Query("DELETE FROM Photo WHERE id = :photoId")
    int deletePhoto(int photoId);
}
