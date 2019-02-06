package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.Type;

import java.util.List;

public interface TypeDao {
    @Query("SELECT * FROM Type WHERE _id = :_id")
    LiveData<List<Type>> getTypes(int _id);

    @Insert
    int insertType(Type type);

    @Update
    int updateType(Type type);

    @Query("DELETE FROM Type WHERE _id = :typeId")
    int deleteItem(int typeId);
}
