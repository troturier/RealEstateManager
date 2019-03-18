package com.openclassrooms.realestatemanager.testDao;


import com.openclassrooms.realestatemanager.LiveDataTestUtil;
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.PointInteret;
import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;
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
public class PointInteretBienImmobilierDaoTest {

    // FOR DATA
    private RealEstateManagerDatabase database;

    // DATA SET FOR TEST
    private static int ID = 1;
    private static Utilisateur USER_DEMO = new Utilisateur(ID, "Roturier", "Thibault");
    private static PointInteret POI_DEMO = new PointInteret(ID, "Test");
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
    private static PointInteretBienImmobilier POIBI_DEMO = new PointInteretBienImmobilier(ID, ID);

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
    public void insertAndGetPointInteretBienImmobiliers() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        this.database.typeDao().insertType(TYPE_DEMO);
        this.database.bienImmobilierDao().insertBienImmobilier(BIEN_DEMO);
        this.database.pointInteretDao().insertPointInteret(POI_DEMO);
        this.database.pointInteretBienImmobilierDao().insertPointInteretBienImmobilier(POIBI_DEMO);

        // TEST
        List<PointInteretBienImmobilier> pointInteretBienImmobilierList = LiveDataTestUtil.getValue(this.database.pointInteretBienImmobilierDao().getPointInteretBienImmobilierByBI(ID));
        List<PointInteretBienImmobilier> pointInteretBienImmobilierList2 = LiveDataTestUtil.getValue(this.database.pointInteretBienImmobilierDao().getPointInteretBienImmobiliersByPOI(ID));
        assertTrue(pointInteretBienImmobilierList.size() == 1 && pointInteretBienImmobilierList.get(0).getIdPoi() == ID);
        assertTrue(pointInteretBienImmobilierList2.size() == 1 && pointInteretBienImmobilierList2.get(0).getIdBien() == ID);
    }

    @Test
    public void insertAndDeletePointInteretBienImmobilier() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        this.database.typeDao().insertType(TYPE_DEMO);
        this.database.bienImmobilierDao().insertBienImmobilier(BIEN_DEMO);
        this.database.pointInteretDao().insertPointInteret(POI_DEMO);
        this.database.pointInteretBienImmobilierDao().insertPointInteretBienImmobilier(POIBI_DEMO);
        List<PointInteretBienImmobilier> pointInteretBienImmobilierList = LiveDataTestUtil.getValue(this.database.pointInteretBienImmobilierDao().getPointInteretBienImmobilierByBI(ID));
        this.database.pointInteretBienImmobilierDao().deletePointInteretBienImmobilier(pointInteretBienImmobilierList.get(0));

        // TEST
        List<PointInteretBienImmobilier> pointInteretBienImmobiliers = LiveDataTestUtil.getValue(this.database.pointInteretBienImmobilierDao().getPointInteretBienImmobilierByBI(ID));
        assertTrue(pointInteretBienImmobiliers.isEmpty());
    }
}
