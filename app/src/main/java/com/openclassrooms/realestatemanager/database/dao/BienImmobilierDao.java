package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.BienImmobilier;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.*;

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
    int deleteItem(int bienImmobilierId);
}
