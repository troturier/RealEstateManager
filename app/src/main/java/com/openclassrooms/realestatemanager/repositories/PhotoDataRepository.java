package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.PhotoDao;
import com.openclassrooms.realestatemanager.models.Photo;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PhotoDataRepository {
    private final PhotoDao photoDao;

    public PhotoDataRepository(PhotoDao photoDao){ this.photoDao = photoDao; }

    // --- GET ---

    public LiveData<Photo> getPhoto(int photoId) { return this.photoDao.getPhoto(photoId); }

    public LiveData<List<Photo>> getPhotos(int bienId) { return this.photoDao.getPhotos(bienId); }

    // --- CREATE ---

    public void createPhoto(Photo photo){ this.photoDao.insertPhoto(photo); }

    // --- DELETE ---

    public void deletePhoto(Photo photo){ this.photoDao.deletePhoto(photo.getId()); }

    // --- UPDATE ---

    public void updateePhoto(Photo photo){ this.photoDao.updatePhoto(photo); }
}
