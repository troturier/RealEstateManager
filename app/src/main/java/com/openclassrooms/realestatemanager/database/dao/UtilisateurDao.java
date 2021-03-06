package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import com.openclassrooms.realestatemanager.models.Utilisateur;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UtilisateurDao {
    @Query("SELECT * FROM Utilisateur WHERE id = :id")
    LiveData<Utilisateur> getUtilisateur(int id);

    @Query("SELECT * FROM Utilisateur")
    LiveData<List<Utilisateur>> getUtilisateurs();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertUtilisateur(Utilisateur utilisateur);

    @Update
    int updateUtilisateur(Utilisateur utilisateur);

    @Query("DELETE FROM Utilisateur WHERE id = :utilisateurId")
    int deleteUtilisateur(int utilisateurId);

    @Query("SELECT * FROM Utilisateur")
    Cursor getUtilisateursWithCursor();
}
