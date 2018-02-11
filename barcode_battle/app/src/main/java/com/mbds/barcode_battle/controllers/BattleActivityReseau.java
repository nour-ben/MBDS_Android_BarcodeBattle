package com.mbds.barcode_battle.controllers;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbds.barcode_battle.controllers.BattleActivity;
import com.mbds.barcode_battle.controllers.MainActivity;
import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.utils.AcceptThreadServer;
import com.mbds.barcode_battle.utils.ItemGenerator;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Yasmine on 11/02/2018.
 */

public class BattleActivityReseau extends AppCompatActivity {

    // Enemy
    TextView name_enemy;
    TextView life_enemy;
    TextView attack_enemy;
    TextView defense_enemy;
    ImageView photo_enemy;

    // Ma creature
    TextView name_myself;
    TextView life_myself;
    TextView attack_myself;
    TextView defense_myself;
    ImageView photo_myself;

    // Boutons de jeu
    Button action;
    ImageButton equipment_choice;
    ImageButton potion_choice;

    //Reseau
    ArrayList<BluetoothDevice> players;
    Creature creatureSelected;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice playerSelected;
    Boolean sendCreature;
    Creature creatureAdverse;
    AcceptThreadServer server;
    int REQUEST_ENABLE_BT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);


        // Enemy
        name_enemy = (TextView) findViewById(R.id.name_enemy);
        life_enemy = (TextView) findViewById(R.id.life_enemy);
        attack_enemy = (TextView) findViewById(R.id.attaque_enemy);
        defense_enemy = (TextView) findViewById(R.id.defense_enemy);
        photo_enemy = (ImageView) findViewById(R.id.battle_enemy);

        // Choix aléatoire
        Creature enemy = (Creature) ItemGenerator.generator(ItemGenerator.CREATURE);

        //System.out.println(enemy.toString());
        /*  name_enemy.setText(enemy.getName());
         life_enemy.setText(enemy.getLife());
        attack_enemy.setText(enemy.getPtAttack());
        defense_enemy.setText(enemy.getPtDefense());
        //photo_enemy.setImageDrawable(enemy.getPhoto());

        // Ma creature
        name_myself = (TextView) findViewById(R.id.name_myself);
        life_myself = (TextView) findViewById(R.id.life_myself);
        attack_myself = (TextView) findViewById(R.id.attaque_myself);
        defense_myself = (TextView) findViewById(R.id.defense_myself);
        photo_myself = (ImageView) findViewById(R.id.battle_myself);

        // Boutons de jeu
        action = (Button) findViewById(R.id.battle_action);
        equipment_choice = (ImageButton) findViewById(R.id.battle_equipment);
        potion_choice = (ImageButton) findViewById(R.id.battle_potion);*/

        //bluetouth
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        sendCreature = false;

        // bluetooth
        if (mBluetoothAdapter == null) {
            // si l’adapter est null, le téléphone ne supporte pas le bluetooth
            AlertDialog.Builder builder = new AlertDialog.Builder(BattleActivityReseau.this);

            builder.setMessage("La fonctionnalité bluetooth est indisponible sur ce device")
                    .setTitle("Info");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            if (!mBluetoothAdapter.isEnabled()) {
                activeBluetooth();
            }else {
                initializeDevices();
            }
        }


    }

    // Mise en place du menu dans l'ActionBar (items répertoriés dans menu_main.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_battle, menu);
        return true;
    }

    // Gestion des actions à réaliser selon choix d'un item du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent intent = new Intent(BattleActivityReseau.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initializeDevices(){
        players = new ArrayList<BluetoothDevice>(mBluetoothAdapter.getBondedDevices());
        String[] info = new String[players.size()];
        for(int i = 0; i < players.size(); i++){
            info[i] = players.get(i).getName() + "\n" + players.get(i).getAddress();
        }
    }

    private void activeBluetooth(){
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        server = new AcceptThreadServer("", new UUID(Long.MAX_VALUE, Long.MIN_VALUE), mBluetoothAdapter){

        };
    }
}
