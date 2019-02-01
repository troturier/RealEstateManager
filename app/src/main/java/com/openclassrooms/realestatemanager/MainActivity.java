package com.openclassrooms.realestatemanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.openclassrooms.realestatemanager.Database.DatabaseAccess;
import com.openclassrooms.realestatemanager.Model.BienImmobilier;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<BienImmobilier> realEstate = databaseAccess.getRealEstate();
        databaseAccess.close();

        List<String> test = new ArrayList<>();
        for (int i=0; i<realEstate.size(); i++){
            String statut = "Vendu";
            if (realEstate.get(i).isStatut()){ statut = "En vente"; }
            String str = "ID : " + String.valueOf(realEstate.get(i).get_id())
                    + "\n" + "Date de mise en vente : " + realEstate.get(i).getDateEntree()
                    + "\n" + "Prix : " + String.valueOf(Utils.convertDollarToEuro(Math.round(realEstate.get(i).getPrix()))) + "€"
                    + "\n" + "Statut : " + statut;
            test.add(str);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);
        this.listView.setAdapter(adapter);
    }

}
