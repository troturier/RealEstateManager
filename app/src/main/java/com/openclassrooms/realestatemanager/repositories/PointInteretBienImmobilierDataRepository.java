package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.PointInteretBienImmobilierDao;
import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PointInteretBienImmobilierDataRepository {
    private final PointInteretBienImmobilierDao pointInteretBienImmobilierDao;

    public PointInteretBienImmobilierDataRepository(PointInteretBienImmobilierDao pointInteretDao){ this.pointInteretBienImmobilierDao = pointInteretDao; }

    // --- GET ---

    public LiveData<PointInteretBienImmobilier> getPointInteretBienImmobilier(int pointInteretId) { return this.pointInteretBienImmobilierDao.getPointInteretBienImmobilier(pointInteretId); }

    public LiveData<List<PointInteretBienImmobilier>> getPointInteretsBienImmobilier(int bienId) { return this.pointInteretBienImmobilierDao.getPointInteretsBienImmobilier(bienId); }

    public LiveData<List<PointInteretBienImmobilier>> getAllPointInteretsBienImmobilier() { return this.pointInteretBienImmobilierDao.getAllPointInteretsBienImmobilier(); }

    // --- CREATE ---

    public void createPointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier){ this.pointInteretBienImmobilierDao.insertPointInteretBienImmobilier(pointInteretBienImmobilier); }

    // --- DELETE ---

    public void deletePointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier){ this.pointInteretBienImmobilierDao.deletePointInteretBienImmobilier(pointInteretBienImmobilier.getIdPoi()); }

    // --- UPDATE ---

    public void updateePointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier){ this.pointInteretBienImmobilierDao.updatePointInteretBienImmobilier(pointInteretBienImmobilier); }
}
