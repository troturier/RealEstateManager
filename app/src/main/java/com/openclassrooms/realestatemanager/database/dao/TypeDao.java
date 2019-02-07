package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Type;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TypeDao {
    @Query("SELECT * FROM Type")
    LiveData<List<Type>> getTypes();

    @Query("SELECT * FROM Type WHERE id = :id")
    LiveData<Type> getType(int id);

    @Insert
    long insertType(Type type);

    @Update
    int updateType(Type type);

    @Query("DELETE FROM Type WHERE id = :typeId")
    int deleteItem(int typeId);
}
