package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.Utilisateur;

import java.util.List;

public interface UtilisateurDao {
    @Query("SELECT * FROM Utilisateur WHERE _id = :_id")
    LiveData<List<Utilisateur>> getUtilisateurs(int _id);

    @Insert
    int insertUtilisateur(Utilisateur utilisateur);

    @Update
    int updateUtilisateur(Utilisateur utilisateur);

    @Query("DELETE FROM Utilisateur WHERE _id = :utilisateurId")
    int deleteItem(int utilisateurId);
}
