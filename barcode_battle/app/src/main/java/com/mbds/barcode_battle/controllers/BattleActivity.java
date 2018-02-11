package com.mbds.barcode_battle.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.localDatabase.DBHandler;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;
import com.mbds.barcode_battle.utils.ItemGenerator;

import java.util.Random;

public class BattleActivity extends AppCompatActivity {

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
    public static int OK_EQUIPMENT = 99;
    ImageButton potion_choice;
    public static int OK_POTION = 98;

    TextView tourTitle;
    int tour = 1;

    Creature enemy;
    Creature me;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        tourTitle = (TextView) findViewById(R.id.tour);

        // Enemy
        name_enemy = (TextView) findViewById(R.id.name_enemy);
        life_enemy = (TextView) findViewById(R.id.life_enemy);
        attack_enemy = (TextView) findViewById(R.id.attaque_enemy);
        defense_enemy = (TextView) findViewById(R.id.defense_enemy);
        photo_enemy = (ImageView) findViewById(R.id.battle_enemy);

        // Choix aléatoire
        enemy = (Creature) ItemGenerator.generator(ItemGenerator.CREATURE);

        if(enemy != null) {
            name_enemy.setText(enemy.getName());
            life_enemy.setText(Integer.toString(enemy.getLife()));
            attack_enemy.setText(Integer.toString(enemy.getPtAttack()));
            defense_enemy.setText(Integer.toString(enemy.getPtDefense()));
            photo_enemy.setImageResource(enemy.getPhoto());
        }


        // Ma creature
        name_myself = (TextView) findViewById(R.id.name_myself);
        life_myself = (TextView) findViewById(R.id.life_myself);
        attack_myself = (TextView) findViewById(R.id.attaque_myself);
        defense_myself = (TextView) findViewById(R.id.defense_myself);
        photo_myself = (ImageView) findViewById(R.id.battle_myself);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            me = (Creature) bundle.getParcelable("COMBAT");
            name_myself.setText(me.getName());
            life_myself.setText(Integer.toString(me.getLife()));
            attack_myself.setText(Integer.toString(me.getPtAttack()));
            defense_myself.setText(Integer.toString(me.getPtDefense()));
            photo_myself.setImageResource(me.getPhoto());
        }


        // Boutons de jeu
        action = (Button) findViewById(R.id.battle_action);
        equipment_choice = (ImageButton) findViewById(R.id.battle_equipment);
        potion_choice = (ImageButton) findViewById(R.id.battle_potion);
        potion_choice.setVisibility(View.INVISIBLE);

        // ATTACK

        action.setText(R.string.attack);
        action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tour ++;
                    tourTitle.setText("TOUR " + tour);
                    if(tour%2 == 0) {
                        attack();
                        action.setText(R.string.defense);
                        potion_choice.setVisibility(View.VISIBLE);
                        equipment_choice.setVisibility(View.VISIBLE);
                    } else {
                        defense();
                        action.setText(R.string.attack);
                        potion_choice.setVisibility(View.INVISIBLE);
                        equipment_choice.setVisibility(View.VISIBLE);
                    }
                }
        });

        equipment_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BattleActivity.this, ItemsListActivity.class);
                intent.putExtra("DISPLAY", ItemGenerator.EQUIPMENT);
                intent.putExtra("PLAYING", true);
                startActivityForResult(intent, OK_EQUIPMENT);
            }
        });

        potion_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BattleActivity.this, ItemsListActivity.class);
                intent.putExtra("DISPLAY", ItemGenerator.POTION);
                intent.putExtra("PLAYING", true);
                startActivityForResult(intent, OK_POTION);
            }
        });

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
            Intent intent = new Intent(BattleActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("ON RESULT");
        if(requestCode == OK_EQUIPMENT){
            System.out.println("ON RESULT");

            Equipment equipment = data.getParcelableExtra("BATTLE_EQUIPMENT");
            if(equipment.getType().equals("Attaque")){
                int point = Integer.parseInt(attack_myself.getText().toString()) + equipment.getValue();
                attack_myself.setText(Integer.toString(point));
            } else {
                int point = Integer.parseInt(defense_myself.getText().toString()) + equipment.getValue();
                defense_myself.setText(Integer.toString(point));
            }
            equipment_choice.setVisibility(View.INVISIBLE);
        }

        if(requestCode == OK_POTION){
            Potion potion = data.getParcelableExtra("BATTLE_POTION");
            int point = Integer.parseInt(life_myself.getText().toString()) + potion.getValue();
            life_myself.setText(Integer.toString(point));
            potion_choice.setVisibility(View.INVISIBLE);
        }

    }

    private void attack(){
        Random random = new Random();
        int point = Integer.parseInt(life_enemy.getText().toString()) - random.nextInt(Integer.parseInt(attack_myself.getText().toString()));
        if(point > 0) {
            life_enemy.setText(Integer.toString(point));
        } else {
            gameover(true);
        }

    }

    private void defense(){
        Random random = new Random();
        int point = Integer.parseInt(life_myself.getText().toString()) - random.nextInt(Integer.parseInt(attack_enemy.getText().toString()));
        if(point > 0) {
            life_myself.setText(Integer.toString(point));
        } else {
            gameover(false);
        }
    }

    private void gameover(boolean win){
        AlertDialog.Builder ad = new AlertDialog.Builder(BattleActivity.this);

        ad.setCancelable(false);
        ad.setIcon(android.R.drawable.ic_dialog_alert);
        if(win)
            ad.setMessage("BRAVO ! Vous avez gagné :)");
        else
            ad.setMessage("LOOSER ! Vous avez perdu ...");
        ad.setNegativeButton("FIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(BattleActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ad.setPositiveButton("REJOUER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(BattleActivity.this, ItemsListActivity.class);
                intent.putExtra("DISPLAY", ItemGenerator.CREATURE);
                startActivity(intent);
                finish();
            }
        });

        ad.show();

    }
}
