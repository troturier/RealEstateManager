package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.BienImmobilier;

import java.util.List;

@Dao
public interface BienImmobilierDao {

    @Query("SELECT * FROM BienImmobilier WHERE _id = :_id")
    LiveData<List<BienImmobilier>> getBienImmobiliers(int _id);

    @Insert
    int insertBienImmobilier(BienImmobilier bienImmobilier);

    @Update
    int updateBienImmobilier(BienImmobilier bienImmobilier);

    @Query("DELETE FROM BienImmobilier WHERE _id = :bienImmobilierId")
    int deleteItem(int bienImmobilierId);
}
