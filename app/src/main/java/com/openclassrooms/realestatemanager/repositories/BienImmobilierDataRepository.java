package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.BienImmobilierDao;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BienImmobilierDataRepository {
    private final BienImmobilierDao bienImmobilierDao;

    public BienImmobilierDataRepository(BienImmobilierDao bienImmobilierDao){ this.bienImmobilierDao = bienImmobilierDao; }

    // --- GET ---

    public LiveData<BienImmobilier> getBienImmobilier(int bienId) { return this.bienImmobilierDao.getBienImmobilier(bienId); }

    public LiveData<BienImmobilier> getLastBienImmobilier() { return this.bienImmobilierDao.getLastBienImmobilier(); }

    public LiveData<List<BienImmobilier>> getBienImmobiliers() { return this.bienImmobilierDao.getBienImmobiliers(); }

    public LiveData<List<BienImmobilierComplete>> getBienImmobiliersComplete() { return this.bienImmobilierDao.getBienImmobiliersComplete(); }

    // --- CREATE ---

    public long createBienImmobilier(BienImmobilier bienImmobilier){ return this.bienImmobilierDao.insertBienImmobilier(bienImmobilier); }

    // --- DELETE ---

    public void deleteBienImmobilier(BienImmobilier bienImmobilier){ this.bienImmobilierDao.deleteBienImmobilier(bienImmobilier.getId()); }

    // --- UPDATE ---

    public void updateeBienImmobilier(BienImmobilier bienImmobilier){ this.bienImmobilierDao.updateBienImmobilier(bienImmobilier); }

}
