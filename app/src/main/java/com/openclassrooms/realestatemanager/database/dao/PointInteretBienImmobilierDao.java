package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PointInteretBienImmobilierDao {
    @Query("SELECT * FROM PointInteretBienImmobilier WHERE idBien = :idBien")
    LiveData<List<PointInteretBienImmobilier>> getPointInteretsBienImmobilier(int idBien);

    @Query("SELECT * FROM PointInteretBienImmobilier")
    LiveData<List<PointInteretBienImmobilier>> getAllPointInteretsBienImmobilier();

    @Query("SELECT * FROM PointInteretBienImmobilier WHERE idPoi = :id")
    LiveData<PointInteretBienImmobilier> getPointInteretBienImmobilier(int id);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long insertPointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier);

    @Update
    int updatePointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier);

    @Query("DELETE FROM PointInteretBienImmobilier WHERE idPoi = :pointInteretId")
    int deletePointInteretBienImmobilier(int pointInteretId);
}
