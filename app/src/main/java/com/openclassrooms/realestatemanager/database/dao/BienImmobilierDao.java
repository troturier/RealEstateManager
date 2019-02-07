package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.BienImmobilier;

import java.util.List;

@Dao
public interface BienImmobilierDao {

    @Query("SELECT * FROM BienImmobilier")
    LiveData<List<BienImmobilier>> getBienImmobiliers();

    @Query("SELECT * FROM BienImmobilier WHERE id = :id")
    LiveData<BienImmobilier> getBienImmobilier(int id);

    @Insert
    long insertBienImmobilier(BienImmobilier bienImmobilier);

    @Update
    int updateBienImmobilier(BienImmobilier bienImmobilier);

    @Query("DELETE FROM BienImmobilier WHERE id = :bienImmobilierId")
    int deleteBienImmobilier(int bienImmobilierId);
}
