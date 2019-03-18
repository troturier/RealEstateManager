package com.openclassrooms.realestatemanager.testDao;

import com.openclassrooms.realestatemanager.LiveDataTestUtil;
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.models.Utilisateur;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class PhotoDaoTest {
    // FOR DATA
    private RealEstateManagerDatabase database;

    // DATA SET FOR TEST
    private static int ID = 1;
    private static Utilisateur USER_DEMO = new Utilisateur(ID, "Roturier", "Thibault");
    private static Type TYPE_DEMO = new Type(ID, "Appartement");
    private static BienImmobilier BIEN_DEMO = new BienImmobilier(
            ID,
            20,
            1,
            1,
            3,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            100000,
            null,
            true,
            1,
            1);
    private static Photo PHOTO_DEMO = new Photo(ID, "test", "", ID);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception{
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception{
        database.close();
    }

    @Test
    public void insertAndGetPhoto() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        this.database.typeDao().insertType(TYPE_DEMO);
        this.database.bienImmobilierDao().insertBienImmobilier(BIEN_DEMO);
        this.database.photoDao().insertPhoto(PHOTO_DEMO);

        // TEST
        Photo photo = LiveDataTestUtil.getValue(this.database.photoDao().getPhoto(ID));
        assertTrue(photo.getId() == ID && photo.getIdBien() == ID);
    }

    @Test
    public void insertAndUpdatePhoto() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        this.database.typeDao().insertType(TYPE_DEMO);
        this.database.bienImmobilierDao().insertBienImmobilier(BIEN_DEMO);
        this.database.photoDao().insertPhoto(PHOTO_DEMO);
        Photo photo = LiveDataTestUtil.getValue(this.database.photoDao().getPhoto(ID));
        photo.setDescription("updated");
        this.database.photoDao().updatePhoto(photo);

        // TEST
        List<Photo> photos = LiveDataTestUtil.getValue(this.database.photoDao().getPhotos(ID));
        assertTrue(photos.get(0).getDescription().equals("updated") && photos.size() == 1);
    }

    @Test
    public void insertAndDeletePhoto() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        this.database.typeDao().insertType(TYPE_DEMO);
        this.database.bienImmobilierDao().insertBienImmobilier(BIEN_DEMO);
        this.database.photoDao().insertPhoto(PHOTO_DEMO);
        Photo photo = LiveDataTestUtil.getValue(this.database.photoDao().getPhoto(ID));
        this.database.photoDao().deletePhoto(photo.getId());

        // TEST
        List<Photo> photos = LiveDataTestUtil.getValue(this.database.photoDao().getPhotos(ID));
        assertTrue(photos.isEmpty());
    }
}
