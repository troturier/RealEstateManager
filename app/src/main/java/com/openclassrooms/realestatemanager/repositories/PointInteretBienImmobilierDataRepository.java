package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.PointInteretBienImmobilierDao;
import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PointInteretBienImmobilierDataRepository {
    private final PointInteretBienImmobilierDao pointInteretBienImmobilierDao;

    public PointInteretBienImmobilierDataRepository(PointInteretBienImmobilierDao pointInteretDao){ this.pointInteretBienImmobilierDao = pointInteretDao; }

    // --- GET ---

    public LiveData<List<PointInteretBienImmobilier>> getPointInteretBienImmobilierByBI(int bienId) { return this.pointInteretBienImmobilierDao.getPointInteretBienImmobilierByBI(bienId); }

    public LiveData<List<PointInteretBienImmobilier>> getPointInteretBienImmobiliersByPOI(int poiId) { return this.pointInteretBienImmobilierDao.getPointInteretBienImmobiliersByPOI(poiId); }

    public LiveData<List<PointInteretBienImmobilier>> getAllPointInteretsBienImmobilier() { return this.pointInteretBienImmobilierDao.getAllPointInteretsBienImmobilier(); }

    // --- CREATE ---

    public void createPointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier){ this.pointInteretBienImmobilierDao.insertPointInteretBienImmobilier(pointInteretBienImmobilier); }

    // --- DELETE ---

    public void deletePointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier){ this.pointInteretBienImmobilierDao.deletePointInteretBienImmobilier(pointInteretBienImmobilier); }

    }
