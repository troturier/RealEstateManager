package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

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

    /**
     * This query will tell Room to query both the [BienImmobilier] and [Type] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM BienImmobilier")
    LiveData<List<BienImmobilierComplete>> getBienImmobiliersComplete();



}
