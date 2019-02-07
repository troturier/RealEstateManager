package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.PointInteretDao;
import com.openclassrooms.realestatemanager.models.PointInteret;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PointInteretDataRepository {
    private final PointInteretDao pointInteretDao;

    public PointInteretDataRepository(PointInteretDao pointInteretDao){ this.pointInteretDao = pointInteretDao; }

    // --- GET ---

    public LiveData<PointInteret> getPointInteret(int pointInteretId) { return this.pointInteretDao.getPointInteret(pointInteretId); }

    public LiveData<List<PointInteret>> getPointInterets(int bienId) { return this.pointInteretDao.getPointInterets(bienId); }

    // --- CREATE ---

    public void createPointInteret(PointInteret pointInteret){ this.pointInteretDao.insertPointInteret(pointInteret); }

    // --- DELETE ---

    public void deletePointInteret(PointInteret pointInteret){ this.pointInteretDao.deletePointInteret(pointInteret.getId()); }

    // --- UPDATE ---

    public void updateePointInteret(PointInteret pointInteret){ this.pointInteretDao.updatePointInteret(pointInteret); }
}
