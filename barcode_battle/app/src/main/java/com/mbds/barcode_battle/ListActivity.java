package com.mbds.barcode_battle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mbds.barcode_battle.localDatabase.DBHandler;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;
import com.mbds.barcode_battle.utils.MultiViewTypeAdapter;
import com.mbds.barcode_battle.utils.ItemGenerator;
import com.mbds.barcode_battle.utils.Service;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    ListView list_creatures;
    ListView list_potions;
    ListView list_equipments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        // Affichage des créatures
     //   list_creatures = (ListView) findViewById(R.id.creature_recycler_view);

        // Affichage des équipements
       // list_equipments = (ListView) findViewById(R.id.equipement_recycler_view);

        // Affichage des potions
     //   list_potions = (ListView) findViewById(R.id.potion_recycler_view);

    }

    // Mise en place du menu dans l'ActionBar (items répertoriés dans menu_main.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_list, menu);

        DBHandler db = new DBHandler(getApplicationContext());

        ArrayList<HashMap<String,String>> list = db.readAllCreatures();
        list.addAll( db.readAllEquipments());
        list.addAll( db.readAllPotions());

        System.out.println("les potions "+db.readAllPotions());
        MultiViewTypeAdapter adapter = new MultiViewTypeAdapter(list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        return true;
    }

    // Gestion des actions à réaliser selon choix d'un item du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Ajout de créatures, potions, équipements par scan
        if (id == R.id.action_add) {
            final Activity activity = this;
            Service.scanCodeBarre(activity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Object obj = ItemGenerator.generator(Long.parseLong(result.getContents()));
                DBHandler db = new DBHandler(getApplicationContext());
                if (obj != null) {
                    if(obj.getClass() == Creature.class){
                        db.createCreature((Creature) obj);
                    }else if (obj.getClass() == Equipment.class){
                        db.createEquipment((Equipment) obj);
                    }else {
                        db.createPotion((Potion) obj);
                    }
                }
            }
        } else {
            // This is important, otherwise the result will not be passed
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
