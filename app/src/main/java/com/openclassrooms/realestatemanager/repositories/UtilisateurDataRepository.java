package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.UtilisateurDao;
import com.openclassrooms.realestatemanager.models.Utilisateur;

import java.util.List;

import androidx.lifecycle.LiveData;

public class UtilisateurDataRepository {
    private final UtilisateurDao utilisateurDao;

    public UtilisateurDataRepository(UtilisateurDao utilisateurDao){ this.utilisateurDao = utilisateurDao; }

    // --- GET ---

    public LiveData<Utilisateur> getUtilisateur(int utilisateurId) { return this.utilisateurDao.getUtilisateur(utilisateurId); }

    public LiveData<List<Utilisateur>> getUtilisateurs() { return this.utilisateurDao.getUtilisateurs(); }

    // --- CREATE ---

    public void createUtilisateur(Utilisateur utilisateur){ this.utilisateurDao.insertUtilisateur(utilisateur); }

    // --- DELETE ---

    public void deleteUtilisateur(Utilisateur utilisateur){ this.utilisateurDao.deleteUtilisateur(utilisateur.getId()); }

    // --- UPDATE ---

    public void updateeUtilisateur(Utilisateur utilisateur){ this.utilisateurDao.updateUtilisateur(utilisateur); }
}
