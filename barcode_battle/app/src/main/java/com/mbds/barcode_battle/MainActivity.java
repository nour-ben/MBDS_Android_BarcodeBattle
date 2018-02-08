package com.mbds.barcode_battle;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import com.mbds.barcode_battle.localDatabase.DBHandler;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;
import com.mbds.barcode_battle.utils.ItemGenerator;
import com.mbds.barcode_battle.utils.Service;

public class MainActivity extends AppCompatActivity {

    Button btn_local;
    Button btn_reseau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ItemGenerator.init();
        // Lancement d'un combat en local
        btn_local = (Button) findViewById(R.id.local_button);
        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Lancement d'un combat en réseau
        btn_reseau = (Button) findViewById(R.id.reseau_button);
        btn_reseau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
            final Activity activity = this;
            Service.scanCodeBarre(activity);
            return true;
        }

        // Affichage de la bibliothèque d'items scannés et stockés dans la base de données
        if (id == R.id.action_list) {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
            finish();
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
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Object obj = ItemGenerator.generator(Long.parseLong(result.getContents()));
                DBHandler db = new DBHandler(getApplicationContext());
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
