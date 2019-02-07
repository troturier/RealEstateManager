package com.openclassrooms.realestatemanager.testDao;


import com.openclassrooms.realestatemanager.LiveDataTestUtil;
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.models.Type;

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
public class TypeDaoTest {

    // FOR DATA
    private RealEstateManagerDatabase database;

    // DATA SET FOR TEST
    private static int TYPE_ID = 1;
    private static Type TYPE_DEMO = new Type(TYPE_ID, "Appartement");

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
    public void insertAndGetType() throws InterruptedException {
        // BEFORE
        this.database.typeDao().insertType(TYPE_DEMO);

        // TEST
        Type type = LiveDataTestUtil.getValue(this.database.typeDao().getType(TYPE_ID));
        assertTrue(type.getId() == TYPE_ID && type.getLibelle().equals(TYPE_DEMO.getLibelle()));
    }

    @Test
    public void insertAndUpdateType() throws InterruptedException {
        // BEFORE
        this.database.typeDao().insertType(TYPE_DEMO);
        Type type = LiveDataTestUtil.getValue(this.database.typeDao().getType(TYPE_ID));
        type.setLibelle("test");
        this.database.typeDao().updateType(type);

        // TEST
        List<Type> types = LiveDataTestUtil.getValue(this.database.typeDao().getTypes());
        assertTrue(types.size() == 1 && types.get(0).getLibelle().equals("test"));
    }

    @Test
    public void insertAndDeleteType() throws InterruptedException {
        // BEFORE
        this.database.typeDao().insertType(TYPE_DEMO);
        Type type = LiveDataTestUtil.getValue(this.database.typeDao().getType(TYPE_ID));
        this.database.typeDao().deleteItem(type.getId());

        // TEST
        List<Type> types = LiveDataTestUtil.getValue(this.database.typeDao().getTypes());
        assertTrue(types.isEmpty());
    }
}
