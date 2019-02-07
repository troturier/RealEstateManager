package com.openclassrooms.realestatemanager.testDao;

import com.openclassrooms.realestatemanager.LiveDataTestUtil;
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
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
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BienImmobilierDaoTest {
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
    public void insertAndGetBienImmobilier() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        this.database.typeDao().insertType(TYPE_DEMO);
        this.database.bienImmobilierDao().insertBienImmobilier(BIEN_DEMO);

        // TEST
        BienImmobilier bienImmobilier = LiveDataTestUtil.getValue(this.database.bienImmobilierDao().getBienImmobilier(ID));
        assertTrue(bienImmobilier.getId() == ID && bienImmobilier.getIdAgent() == ID);
    }

    @Test
    public void insertAndUpdatePointInteret() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        this.database.typeDao().insertType(TYPE_DEMO);
        this.database.bienImmobilierDao().insertBienImmobilier(BIEN_DEMO);
        BienImmobilier bienImmobilier = LiveDataTestUtil.getValue(this.database.bienImmobilierDao().getBienImmobilier(ID));
        bienImmobilier.setDescription("test");
        this.database.bienImmobilierDao().updateBienImmobilier(bienImmobilier);

        // TEST
        List<BienImmobilier> bienImmobiliers = LiveDataTestUtil.getValue(this.database.bienImmobilierDao().getBienImmobiliers());
        assertTrue(bienImmobiliers.size() == 1 && bienImmobiliers.get(0).getDescription().equals("test"));
    }

    @Test
    public void insertAndDeletePointInteret() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        this.database.typeDao().insertType(TYPE_DEMO);
        this.database.bienImmobilierDao().insertBienImmobilier(BIEN_DEMO);
        BienImmobilier bienImmobilier = LiveDataTestUtil.getValue(this.database.bienImmobilierDao().getBienImmobilier(ID));
        this.database.bienImmobilierDao().deleteBienImmobilier(bienImmobilier.getId());

        // TEST
        List<BienImmobilier> bienImmobiliers = LiveDataTestUtil.getValue(this.database.bienImmobilierDao().getBienImmobiliers());
        assertTrue(bienImmobiliers.isEmpty());
    }
}
