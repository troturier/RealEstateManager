package com.openclassrooms.realestatemanager.testDao;


import com.openclassrooms.realestatemanager.LiveDataTestUtil;
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
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
public class UtilisateurDaoTest {

    // FOR DATA
    private RealEstateManagerDatabase database;

    // DATA SET FOR TEST
    private static int USER_ID = 1;
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
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);

        // TEST
        Utilisateur utilisateur = LiveDataTestUtil.getValue(this.database.utilisateurDao().getUtilisateur(USER_ID));
        assertTrue(utilisateur.getId() == USER_ID && utilisateur.getNom().equals(USER_DEMO.getNom()) && utilisateur.getPrenom().equals(USER_DEMO.getPrenom()));
    }

    @Test
    public void insertAndUpdateUtilisateur() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        Utilisateur utilisateur = LiveDataTestUtil.getValue(this.database.utilisateurDao().getUtilisateur(USER_ID));
        utilisateur.setPrenom("test");
        this.database.utilisateurDao().updateUtilisateur(utilisateur);

        // TEST
        List<Utilisateur> utilisateurs = LiveDataTestUtil.getValue(this.database.utilisateurDao().getUtilisateurs());
        assertTrue(utilisateurs.size() == 1 && utilisateurs.get(0).getPrenom().equals("test"));
    }

    @Test
    public void insertAndDeleteUtilisateur() throws InterruptedException {
        // BEFORE
        this.database.utilisateurDao().insertUtilisateur(USER_DEMO);
        Utilisateur utilisateur = LiveDataTestUtil.getValue(this.database.utilisateurDao().getUtilisateur(USER_ID));
        this.database.utilisateurDao().deleteUtilisateur(utilisateur.getId());

        // TEST
        List<Utilisateur> utilisateurs = LiveDataTestUtil.getValue(this.database.utilisateurDao().getUtilisateurs());
        assertTrue(utilisateurs.isEmpty());
    }
}
