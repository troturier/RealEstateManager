package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.PointInteret;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PointInteretDao {
    @Query("SELECT * FROM PointInteret WHERE idBien = :idBien")
    LiveData<List<PointInteret>> getPointInterets(int idBien);

    @Query("SELECT * FROM PointInteret WHERE id = :id")
    LiveData<PointInteret> getPointInteret(int id);

    @Insert
    long insertPointInteret(PointInteret pointInteret);

    @Update
    int updatePointInteret(PointInteret pointInteret);

    @Query("DELETE FROM PointInteret WHERE id = :pointInteretId")
    int deleteItem(int pointInteretId);
}
