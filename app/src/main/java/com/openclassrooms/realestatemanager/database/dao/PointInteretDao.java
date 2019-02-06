package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.PointInteret;

import java.util.List;

public interface PointInteretDao {
    @Query("SELECT * FROM PointInteret WHERE _id = :_id")
    LiveData<List<PointInteret>> getPointInterets(int _id);

    @Insert
    int insertPointInteret(PointInteret pointInteret);

    @Update
    int updatePointInteret(PointInteret pointInteret);

    @Query("DELETE FROM PointInteret WHERE _id = :pointInteretId")
    int deleteItem(int pointInteretId);
}
