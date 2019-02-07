package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.TypeDao;
import com.openclassrooms.realestatemanager.models.Type;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TypeDataRepository {
    private final TypeDao pointInteretDao;

    public TypeDataRepository(TypeDao typeDao){ this.pointInteretDao = typeDao; }

    // --- GET ---

    public LiveData<Type> getType(int typeId) { return this.pointInteretDao.getType(typeId); }

    public LiveData<List<Type>> getTypes() { return this.pointInteretDao.getTypes(); }

    // --- CREATE ---

    public void createType(Type pointInteret){ this.pointInteretDao.insertType(pointInteret); }

    // --- DELETE ---

    public void deleteType(Type pointInteret){ this.pointInteretDao.deleteType(pointInteret.getId()); }

    // --- UPDATE ---

    public void updateeType(Type pointInteret){ this.pointInteretDao.updateType(pointInteret); }
}
