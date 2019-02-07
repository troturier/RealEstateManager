package com.openclassrooms.realestatemanager;


import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.models.Utilisateur;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UtilisateurDaoTest {

    // FOR DATA
    private RealEstateManagerDatabase database;

    // DATA SET FOR TEST
    private static int USER_ID = 2;
    private static Utilisateur USER_DEMO = new Utilisateur(USER_ID, "Roturier", "Thibault");

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
    public void insertAndGetUtilisateur() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        // TEST
        Utilisateur utilisateur = LiveDataTestUtil.getValue(this.database.utilisateurDao().getUtilisateur(USER_ID));
        assertTrue(utilisateur.getNom().equals(USER_DEMO.getNom()) && utilisateur.getPrenom().equals(USER_DEMO.getPrenom()));
    }
}
