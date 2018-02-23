package com.mbds.barcode_battle.controllers;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.localDatabase.DBHandler;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;
import com.mbds.barcode_battle.utils.ItemGenerator;
import com.mbds.barcode_battle.utils.ScanHandler;
import com.mbds.barcode_battle.utils.SoundService;

public class MainActivity extends AppCompatActivity {

    Button btn_local;
    Button btn_reseau;
    //SOUNDS
    Intent service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ItemGenerator.init(getApplicationContext());

        // Lancement d'un combat en local
        btn_local = (Button) findViewById(R.id.local_button);
        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemsListActivity.class);
                intent.putExtra("DISPLAY", ItemGenerator.CREATURE);
                startActivity(intent);
            }
        });
        service = new Intent(this, SoundService.class);
        startService(service);

    }

    // Mise en place du menu dans l'ActionBar (items répertoriés dans menu_main.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Gestion des actions à réaliser selon choix d'un item du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Ajout de créatures, potions, équipements par scan
        if (id == R.id.action_scan) {
            ScanHandler.scanCodeBarre(this);
            return true;
        }

        // Affichage de la bibliothèque d'items scannés et stockés dans la base de données
        if (id == R.id.action_list) {
            Intent intent = new Intent(MainActivity.this, ItemsListActivity.class);
            intent.putExtra("DISPLAY", "ALL");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                Object obj = ItemGenerator.generator(Long.parseLong(result.getContents()));
                Toast.makeText(this, "Scanned: " + obj.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                DBHandler db = new DBHandler(getApplicationContext());
                db.open();
                if(obj != null){
                    if (obj.getClass()== Creature.class){
                        db.createCreature((Creature)obj);
                    }else if (obj.getClass()== Equipment.class){
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
