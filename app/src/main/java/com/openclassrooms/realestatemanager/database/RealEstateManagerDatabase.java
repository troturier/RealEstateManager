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
                contentValues.put("description", "Joli T1 duplex - grande terrasse. Une belle vue dégagée sur bordeaux, venez découvrir au 4ème et dernier étage sans ascenseur, cet appartement lumineux compose d'une chambre, bureau et salle d'eau en mezzanine. Sa terrasse en fera un lieu très agréable a vivre ! Parking libre au sein de la petite résidence dans un quartier calme. Idéal premier achat/jeunes couples bien en copropriété de 52 lots dont 35 lots principaux, charges annuelles 900.00 euros, surface carrez 39.9m².");
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
                contentValues.put("description", " Une villa de type 4 de 83.89 m² avec jardin et garage située dans un lotissement. Cette villa sur deux niveaux est composée au rez-de-chaussée d'un WC, d'un salon/séjour avec cuisine ouverte sur un bel extérieur avec un accès garage (aujourd'hui aménagé en studio indépendant). L'étage se compose de trois chambres avec rangements, d'un WC et d'une salle d'eau. Chauffage individuel électrique et climatisation réversible, double vitrage. Charges annuelles de 930 e.");
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
                contentValues.put("description", "Située sur les quais de Saône, à proximité de toutes les commodités, bénéficiant du très bel environnement de l'île Barbe, cette maison de qualité de 250 m², de 1935 offre de beaux volumes avec de jolis éléments. Elle comprend vaste séjour traversant, cuisine indépendante, 5 chambres, véranda, terrasse. En rez-de-jardin, salon d'été ouvert sur jardin, studio équipé, hammam. Le jardin clos de 1000 m² offre une végétation luxuriante, piscine chauffée, dépendance aménageable. Garage. ");
                contentValues.put("prix", 126000);
                contentValues.put("statut", true);
                contentValues.put("idAgent", 1);
                contentValues.put("idType", 2);

                db.insert("BienImmobilier", OnConflictStrategy.IGNORE, contentValues);


                contentValues = new ContentValues();
                contentValues.put("id", 5);
                contentValues.put("surface", 43);
                contentValues.put("chambres", 1);
                contentValues.put("sdb", 1);
                contentValues.put("pieces", 2);
                contentValues.put("dateEntree", "15/01/2019");
                contentValues.put("rue", "3, boulevard Aristide Briand");
                contentValues.put("ville", "Toulouse");
                contentValues.put("pays", "France");
                contentValues.put("cp", "71200");
                contentValues.put("description", "Secteur Toulouse Sud-Parc de Géronis-Route de Seysses, notre agence a le plaisir de vous présenter ce charmant appartement type 2 au second et dernier étage. Au sein d'une résidence arborée, sécurisée et très bien entretenue, vous disposerez d'une place de parking sécurisée. L'appartement se compose, d'un séjour de 23 m², une cuisine semi-ouverte aménagée et équipée de 4 m². Le tout donnant sur une terrasse exposée Sud-Est de 6 m². Mais également d'une chambre avec placards aménagés. Bien à saisir, libre de toute occupation ! Dont 9.00 % honoraires TTC à la charge de l'acquéreur. Copropriété de 163 lots (Pas de procédure en cours). Charges annuelles: 1220.00 euros.");
                contentValues.put("prix", 85000);
                contentValues.put("statut", true);
                contentValues.put("idAgent", 1);
                contentValues.put("idType", 3);

                db.insert("BienImmobilier", OnConflictStrategy.IGNORE, contentValues);


                contentValues = new ContentValues();
                contentValues.put("id", 6);
                contentValues.put("surface", 33);
                contentValues.put("chambres", 1);
                contentValues.put("sdb", 1);
                contentValues.put("pieces", 2);
                contentValues.put("dateEntree", "15/01/2019");
                contentValues.put("rue", "3, boulevard Aristide Briand");
                contentValues.put("ville", "Paris");
                contentValues.put("pays", "France");
                contentValues.put("cp", "71200");
                contentValues.put("description", "mmeuble ancien idéalement situé. Appartement d'environ 33m² situé au 1er étage par escalier, donnant intégralement au calme sur cour et bénéficiant d'une absence de vis à vis. Il propose: entrée, séjour disposant d'une belle hauteur sous plafond et d'un espace nuit en mezzanine, cuisine ouverte, salle de bains avec toilettes. Diagnostics en cours. ");
                contentValues.put("prix", 340000);
                contentValues.put("statut", true);
                contentValues.put("idAgent", 1);
                contentValues.put("idType", 1);

                db.insert("BienImmobilier", OnConflictStrategy.IGNORE, contentValues);

                // --- PHOTOS ---

                contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("cheminAcces", "flat.jpg");
                contentValues.put("idBien", 1);
                contentValues.put("description", "Façade");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("idPhotoCouverture", 1);
                String where = "id=?";
                String[] whereArgs = new String[] {String.valueOf(1)};
                db.update("BienImmobilier", OnConflictStrategy.IGNORE,contentValues, where, whereArgs);

                // --------------------------------

                contentValues = new ContentValues();
                contentValues.put("id", 2);
                contentValues.put("cheminAcces", "house.jpg");
                contentValues.put("idBien", 2);
                contentValues.put("description", "Façade");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("idPhotoCouverture", 2);
                whereArgs = new String[] {String.valueOf(2)};
                db.update("BienImmobilier", OnConflictStrategy.IGNORE,contentValues, where, whereArgs);

                // --------------------------------

                contentValues = new ContentValues();
                contentValues.put("id", 3);
                contentValues.put("cheminAcces", "duplex.jpg");
                contentValues.put("idBien", 3);
                contentValues.put("description", "Façade");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("idPhotoCouverture", 3);
                whereArgs = new String[] {String.valueOf(3)};
                db.update("BienImmobilier", OnConflictStrategy.IGNORE,contentValues, where, whereArgs);

                // --------------------------------

                contentValues = new ContentValues();
                contentValues.put("id", 4);
                contentValues.put("cheminAcces", "house2.jpg");
                contentValues.put("idBien", 4);
                contentValues.put("description", "Façade");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("idPhotoCouverture", 4);
                whereArgs = new String[] {String.valueOf(4)};
                db.update("BienImmobilier", OnConflictStrategy.IGNORE,contentValues, where, whereArgs);

                // --------------------------------

                contentValues = new ContentValues();
                contentValues.put("id", 5);
                contentValues.put("cheminAcces", "duplex2.jpg");
                contentValues.put("idBien", 5);
                contentValues.put("description", "Façade");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("idPhotoCouverture", 5);
                whereArgs = new String[] {String.valueOf(5)};
                db.update("BienImmobilier", OnConflictStrategy.IGNORE,contentValues, where, whereArgs);

                // --------------------------------

                contentValues = new ContentValues();
                contentValues.put("id", 6);
                contentValues.put("cheminAcces", "flat2.jpg");
                contentValues.put("idBien", 6);
                contentValues.put("description", "Façade");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("idPhotoCouverture", 6);
                whereArgs = new String[] {String.valueOf(6)};
                db.update("BienImmobilier", OnConflictStrategy.IGNORE,contentValues, where, whereArgs);

                // ----------------------------------

                contentValues = new ContentValues();
                contentValues.put("id", 7);
                contentValues.put("cheminAcces", "duplex.jpg");
                contentValues.put("idBien", 1);
                contentValues.put("description", "Salon");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 8);
                contentValues.put("cheminAcces", "duplex2.jpg");
                contentValues.put("idBien", 1);
                contentValues.put("description", "Chambre");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 9);
                contentValues.put("cheminAcces", "duplex3.jpg");
                contentValues.put("idBien", 1);
                contentValues.put("description", "Salle de bain");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);

                contentValues = new ContentValues();
                contentValues.put("id", 10);
                contentValues.put("cheminAcces", "flat2.jpg");
                contentValues.put("idBien", 1);
                contentValues.put("description", "Cuisine");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValues);


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
