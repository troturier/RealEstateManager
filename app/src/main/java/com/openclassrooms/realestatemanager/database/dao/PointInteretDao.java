package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.PointInteret;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PointInteretDao {
    @Query("SELECT * FROM PointInteret WHERE id = :id")
    LiveData<PointInteret> getPointInteret(int id);

    @Query("SELECT * FROM PointInteret")
    LiveData<List<PointInteret>> getPointInterets();

    @Insert
    long insertPointInteret(PointInteret pointInteret);

    @Update
    int updatePointInteret(PointInteret pointInteret);

    @Delete
    int deletePointInteret(PointInteret pointInteret);

    /*@Query("DELETE FROM PointInteret WHERE id = :pointInteretId")
    int deletePointInteret(int pointInteretId);*/
}
