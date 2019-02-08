package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;

import com.openclassrooms.realestatemanager.database.dao.BienImmobilierDao;
import com.openclassrooms.realestatemanager.database.dao.PhotoDao;
import com.openclassrooms.realestatemanager.database.dao.PointInteretDao;
import com.openclassrooms.realestatemanager.database.dao.TypeDao;
import com.openclassrooms.realestatemanager.database.dao.UtilisateurDao;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.PointInteret;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.models.Utilisateur;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {BienImmobilier.class, Photo.class, PointInteret.class, Type.class, Utilisateur.class}, version = 1, exportSchema = false)
public abstract class RealEstateManagerDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile RealEstateManagerDatabase INSTANCE;

    // --- DAO ---
    public abstract BienImmobilierDao bienImmobilierDao();
    public abstract PhotoDao photoDao();
    public abstract PointInteretDao pointInteretDao();
    public abstract TypeDao typeDao();
    public abstract UtilisateurDao utilisateurDao();

    // --- INSTANCE ---
    public static RealEstateManagerDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (RealEstateManagerDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDatabase.class, "REM.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                // --- UTILISATEURS ---

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("nom", "Roturier");
                contentValues.put("prenom", "Thibault");

                db.insert("Utilisateur", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 2);
                contentValues.put("nom", "Doe");
                contentValues.put("prenom", "John");

                db.insert("Utilisateur", OnConflictStrategy.IGNORE, contentValues);

                // --- TYPES ---

                contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("libelle", "Appartement");

                db.insert("Type", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 2);
                contentValues.put("libelle", "Maison");

                db.insert("Type", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 3);
                contentValues.put("libelle", "Duplex");

                db.insert("Type", OnConflictStrategy.IGNORE, contentValues);

                // --- BIENS IMMOBILIER ---

                contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("surface", 25);
                contentValues.put("chambres", 1);
                contentValues.put("sdb", 1);
                contentValues.put("pieces", 3);
                contentValues.put("dateEntree", "14/03/2018");
                contentValues.put("rue", "41, rue Sarrette");
                contentValues.put("complementRue", "Appartement n°90");
                contentValues.put("ville", "Bordeaux");
                contentValues.put("pays", "France");
                contentValues.put("cp", "33800");
                contentValues.put("description", "Petit appartement étudiant situé à proximité de la gare St Jean de Bordeaux.");
                contentValues.put("prix", 160000);
                contentValues.put("statut", true);
                contentValues.put("idAgent", 1);
                contentValues.put("idType", 1);

                db.insert("BienImmobilier", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 2);
                contentValues.put("surface", 90);
                contentValues.put("chambres", 2);
                contentValues.put("sdb", 1);
                contentValues.put("pieces", 5);
                contentValues.put("dateEntree", "25/09/2018");
                contentValues.put("rue", "16, cours Franklin Roosevelt");
                contentValues.put("ville", "Marseille");
                contentValues.put("pays", "France");
                contentValues.put("cp", "13006");
                contentValues.put("description", "Maison de ville.");
                contentValues.put("prix", 280000);
                contentValues.put("statut", true);
                contentValues.put("idAgent", 2);
                contentValues.put("idType", 2);

                db.insert("BienImmobilier", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 3);
                contentValues.put("surface", 129);
                contentValues.put("chambres", 5);
                contentValues.put("sdb", 1);
                contentValues.put("pieces", 9);
                contentValues.put("dateEntree", "15/01/2019");
                contentValues.put("rue", "3, boulevard Aristide Briand");
                contentValues.put("ville", "Le Creusot");
                contentValues.put("pays", "France");
                contentValues.put("cp", "71200");
                contentValues.put("description", "Bel appartement en duplex de 129 m² offrant en 1er niveau cuisine entièrement équipée et aménagée vaste pièce de vie de 44 m² baignée de lumière avec cheminée et accès sur terrasse de 33 m² au sud. l'étage : 2 chambres une suite parentale avec salle d'eau privative et dressing salle de bain wc et rangements.Garage fermé et une cave viennent compléter ce bien.Quartier calme proche toutes commodités et écoles.");
                contentValues.put("prix", 198000);
                contentValues.put("statut", true);
                contentValues.put("idAgent", 1);
                contentValues.put("idType", 3);

                db.insert("BienImmobilier", OnConflictStrategy.IGNORE, contentValues);


                contentValues = new ContentValues();
                contentValues.put("id", 4);
                contentValues.put("surface", 129);
                contentValues.put("chambres", 5);
                contentValues.put("sdb", 1);
                contentValues.put("pieces", 9);
                contentValues.put("dateEntree", "15/01/2019");
                contentValues.put("rue", "3, boulevard Aristide Briand");
                contentValues.put("ville", "Lyon");
                contentValues.put("pays", "France");
                contentValues.put("cp", "71200");
                contentValues.put("description", "Bel appartement en duplex de 129 m² offrant en 1er niveau cuisine entièrement équipée et aménagée vaste pièce de vie de 44 m² baignée de lumière avec cheminée et accès sur terrasse de 33 m² au sud. l'étage : 2 chambres une suite parentale avec salle d'eau privative et dressing salle de bain wc et rangements.Garage fermé et une cave viennent compléter ce bien.Quartier calme proche toutes commodités et écoles.");
                contentValues.put("prix", 126000);
                contentValues.put("statut", true);
                contentValues.put("idAgent", 1);
                contentValues.put("idType", 2);

                db.insert("BienImmobilier", OnConflictStrategy.IGNORE, contentValues);


                contentValues = new ContentValues();
                contentValues.put("id", 5);
                contentValues.put("surface", 129);
                contentValues.put("chambres", 5);
                contentValues.put("sdb", 1);
                contentValues.put("pieces", 9);
                contentValues.put("dateEntree", "15/01/2019");
                contentValues.put("rue", "3, boulevard Aristide Briand");
                contentValues.put("ville", "Toulouse");
                contentValues.put("pays", "France");
                contentValues.put("cp", "71200");
                contentValues.put("description", "Bel appartement en duplex de 129 m² offrant en 1er niveau cuisine entièrement équipée et aménagée vaste pièce de vie de 44 m² baignée de lumière avec cheminée et accès sur terrasse de 33 m² au sud. l'étage : 2 chambres une suite parentale avec salle d'eau privative et dressing salle de bain wc et rangements.Garage fermé et une cave viennent compléter ce bien.Quartier calme proche toutes commodités et écoles.");
                contentValues.put("prix", 143000);
                contentValues.put("statut", true);
                contentValues.put("idAgent", 1);
                contentValues.put("idType", 3);

                db.insert("BienImmobilier", OnConflictStrategy.IGNORE, contentValues);


                contentValues = new ContentValues();
                contentValues.put("id", 6);
                contentValues.put("surface", 129);
                contentValues.put("chambres", 5);
                contentValues.put("sdb", 1);
                contentValues.put("pieces", 9);
                contentValues.put("dateEntree", "15/01/2019");
                contentValues.put("rue", "3, boulevard Aristide Briand");
                contentValues.put("ville", "Paris");
                contentValues.put("pays", "France");
                contentValues.put("cp", "71200");
                contentValues.put("description", "Bel appartement en duplex de 129 m² offrant en 1er niveau cuisine entièrement équipée et aménagée vaste pièce de vie de 44 m² baignée de lumière avec cheminée et accès sur terrasse de 33 m² au sud. l'étage : 2 chambres une suite parentale avec salle d'eau privative et dressing salle de bain wc et rangements.Garage fermé et une cave viennent compléter ce bien.Quartier calme proche toutes commodités et écoles.");
                contentValues.put("prix", 272000);
                contentValues.put("statut", true);
                contentValues.put("idAgent", 1);
                contentValues.put("idType", 1);

                db.insert("BienImmobilier", OnConflictStrategy.IGNORE, contentValues);

                // --- POINTS INTERET ---

                contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("libelle", "Gare ferroviaire");
                contentValues.put("idBien", 1);

                db.insert("PointInteret", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 2);
                contentValues.put("libelle", "Commerces de proximité");
                contentValues.put("idBien", 1);

                db.insert("PointInteret", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 3);
                contentValues.put("libelle", "Médecin");
                contentValues.put("idBien", 1);

                db.insert("PointInteret", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 4);
                contentValues.put("libelle", "Ecole");
                contentValues.put("idBien", 2);

                db.insert("PointInteret", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 5);
                contentValues.put("libelle", "Hôpital");
                contentValues.put("idBien", 2);

                db.insert("PointInteret", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 6);
                contentValues.put("libelle", "Ecole");
                contentValues.put("idBien", 3);

                db.insert("PointInteret", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 7);
                contentValues.put("libelle", "Commerces de proximité");
                contentValues.put("idBien", 3);

                db.insert("PointInteret", OnConflictStrategy.IGNORE, contentValues);

            }
        };
    }
}
