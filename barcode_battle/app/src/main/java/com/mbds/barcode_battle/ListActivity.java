package com.mbds.barcode_battle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {

    ListView list_creatures;
    ListView list_potions;
    ListView list_equipments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        // Affichage des créatures
        list_creatures = (ListView) findViewById(R.id.creature_recycler_view);

        // Affichage des équipements
        list_equipments = (ListView) findViewById(R.id.equipement_recycler_view);

        // Affichage des potions
        list_potions = (ListView) findViewById(R.id.potion_recycler_view);
    }


    // Mise en place du menu dans l'ActionBar (items répertoriés dans menu_main.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    // Gestion des actions à réaliser selon choix d'un item du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Ajout de créatures, potions, équipements par scan
        if (id == R.id.action_add) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
