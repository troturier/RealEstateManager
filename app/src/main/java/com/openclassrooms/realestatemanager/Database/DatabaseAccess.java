package com.openclassrooms.realestatemanager.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.openclassrooms.realestatemanager.Model.BienImmobilier;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the databases connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the databases connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all real estate from the databases.
     *
     * @return a List of real estate
     */
    public List<BienImmobilier> getRealEstate() {
        List<BienImmobilier> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM BienImmobilier", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BienImmobilier bienImmobilier = new BienImmobilier();
            bienImmobilier.set_id(cursor.getInt(0));
            bienImmobilier.setSurface(cursor.getInt(1));
            bienImmobilier.setNombreChambres(cursor.getInt(2));
            bienImmobilier.setNombreSdB(cursor.getInt(3));
            if (cursor.getString(4) != null) bienImmobilier.setDescription(cursor.getString(4));
            bienImmobilier.setDateEntree(cursor.getString(5));
            if (cursor.getString(6) != null) bienImmobilier.setDateVente(cursor.getString(6));
            bienImmobilier.setPrix(cursor.getFloat(7));
            bienImmobilier.setStatut(cursor.getInt(8) != 0);
            list.add(bienImmobilier);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}