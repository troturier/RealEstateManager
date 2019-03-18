package com.openclassrooms.realestatemanager.testDao;

import com.openclassrooms.realestatemanager.LiveDataTestUtil;
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.models.PointInteret;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class PointInteretDaoTest {

    // FOR DATA
    private RealEstateManagerDatabase database;

    // DATA SET FOR TEST
    private static int ID = 1;
    private static PointInteret POI_DEMO = new PointInteret(ID, "Test");

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
    public void insertAndGetPointInteret() throws InterruptedException {
        // BEFORE
        this.database.pointInteretDao().insertPointInteret(POI_DEMO);
        // TEST
        PointInteret pointInteret = LiveDataTestUtil.getValue(this.database.pointInteretDao().getPointInteret(ID));
        assertTrue(pointInteret.getId() == ID && pointInteret.getLibelle().equals(POI_DEMO.getLibelle()));
    }

    @Test
    public void insertAndUpdatePointInteret() throws InterruptedException {
        // BEFORE
        this.database.pointInteretDao().insertPointInteret(POI_DEMO);
        PointInteret pointInteret = LiveDataTestUtil.getValue(this.database.pointInteretDao().getPointInteret(ID));
        pointInteret.setLibelle("Test2");
        this.database.pointInteretDao().updatePointInteret(pointInteret);
        // TEST
        pointInteret = LiveDataTestUtil.getValue(this.database.pointInteretDao().getPointInteret(ID));
        assertEquals("Test2", pointInteret.getLibelle());
    }

    @Test
    public void insertAndDeletePointInteret() throws InterruptedException {
        // BEFORE
        this.database.pointInteretDao().insertPointInteret(POI_DEMO);
        PointInteret pointInteret = LiveDataTestUtil.getValue(this.database.pointInteretDao().getPointInteret(ID));
        this.database.pointInteretDao().deletePointInteret(pointInteret);
        // TEST
        List<PointInteret> pointInteretList = LiveDataTestUtil.getValue(this.database.pointInteretDao().getPointInterets());
        assertTrue(pointInteretList.isEmpty());
    }
}
