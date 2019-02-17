package com.openclassrooms.realestatemanager.testContentProvider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.providers.UtilisateurContentProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UtilisateurContentProviderTest {

    // FOR DATA
    private RealEstateManagerDatabase database;
    private ContentResolver mContentResolver;

    // DATA SET FOR TEST
    private static int USER_ID = 3;

    @Before
    public void setUp() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = InstrumentationRegistry.getContext().getContentResolver();

    }

    @After
    public void closeDb() throws Exception{
        database.close();
    }

    @Test
    public void getUtilisateursWhenNoUtilisateurInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(UtilisateurContentProvider.URI_UTILISATEUR, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(2));
        cursor.close();
    }

    @Test
    public void insertAndGetUtilisateur() {
        // BEFORE : Adding demo utilisateur
        final Uri userUri = mContentResolver.insert(UtilisateurContentProvider.URI_UTILISATEUR, generateUtilisateur());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(UtilisateurContentProvider.URI_UTILISATEUR, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(3));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("id")), is(1));
    }

    // ---
    private ContentValues generateUtilisateur(){
        final ContentValues values = new ContentValues();
        values.put("id", USER_ID);
        values.put("nom", "Content");
        values.put("prenom", "Provider");
        return values;
    }
}
