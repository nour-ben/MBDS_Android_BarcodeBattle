package com.mbds.barcode_battle.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;
import com.mbds.barcode_battle.utils.ItemGenerator;
import com.mbds.barcode_battle.utils.SoundService;

import java.io.IOException;
import java.util.Random;

public class BattleActivity extends AppCompatActivity {

    // Enemy
    TextView name_enemy;
    ProgressBar life_enemy;
    TextView attack_enemy;
    TextView defense_enemy;
    ImageView photo_enemy;

    // Ma creature
    TextView name_myself;
    ProgressBar life_myself;
    TextView attack_myself;
    TextView defense_myself;
    ImageView photo_myself;

    // Boutons de jeu
    Button action;
    ImageView equipment_choice;
    public static int OK_EQUIPMENT = 99;
    ImageView potion_choice;
    public static int OK_POTION = 98;

    TextView tourTitle;
    int tour = 1;
    ImageView my_tour;
    ImageView enemy_tour;
    TextView commentTextView;

    Creature enemy;
    Creature me;

    //SOUNDS
    Intent service;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        tourTitle = (TextView) findViewById(R.id.tour);

        // Enemy
        name_enemy = (TextView) findViewById(R.id.name_enemy);
        life_enemy = (ProgressBar) findViewById(R.id.p_life_progressBar2);
        attack_enemy = (TextView) findViewById(R.id.attaque_enemy);
        defense_enemy = (TextView) findViewById(R.id.defense_enemy);
        photo_enemy = (ImageView) findViewById(R.id.battle_enemy);

        // Choix aléatoire
        enemy = (Creature) ItemGenerator.generator(ItemGenerator.CREATURE);

        if(enemy != null) {
            name_enemy.setText(enemy.getName());
            life_enemy.setProgress(enemy.getLife());
            attack_enemy.setText(Integer.toString(enemy.getPtAttack()));
            defense_enemy.setText(Integer.toString(enemy.getPtDefense()));
            photo_enemy.setImageResource(enemy.getPhoto());
        }


        // Ma creature
        name_myself = (TextView) findViewById(R.id.name_myself);
        life_myself = (ProgressBar) findViewById(R.id.p_life_progressBar1);
        attack_myself = (TextView) findViewById(R.id.attaque_myself);
        defense_myself = (TextView) findViewById(R.id.defense_myself);
        photo_myself = (ImageView) findViewById(R.id.battle_myself);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            me = (Creature) bundle.getParcelable("COMBAT");
            name_myself.setText(me.getName());
            life_myself.setProgress(me.getLife());
            attack_myself.setText(Integer.toString(me.getPtAttack()));
            defense_myself.setText(Integer.toString(me.getPtDefense()));
            photo_myself.setImageResource(me.getPhoto());
        }


        // Boutons de jeu
        action = (Button) findViewById(R.id.battle_action);
        equipment_choice = (ImageView) findViewById(R.id.battle_equipment);
        potion_choice = (ImageView) findViewById(R.id.battle_potion);
        my_tour = (ImageView) findViewById(R.id.cadre_myself) ;
        enemy_tour = (ImageView) findViewById(R.id.cadre_enemy) ;
        potion_choice.setVisibility(View.INVISIBLE);

        commentTextView = (TextView) findViewById(R.id.comment_textView);

        my_tour.setVisibility(View.VISIBLE);
        enemy_tour.setVisibility(View.INVISIBLE);

        action.setEnabled(false);

        //SOUND
        mediaPlayer = new MediaPlayer();

        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("Attack.mp3");
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setVolume(50, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }
        service = new Intent(this, SoundService.class);
        startService(service);
        //WAITING
        welcomeMessage();

        // ATTACK
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tour ++;
//                tourTitle.setText("TOUR " + tour);
                if(tour%2 == 0) {
                    my_tour.setVisibility(View.INVISIBLE);
                    enemy_tour.setVisibility(View.VISIBLE);
                    attack();
                    action.setText(R.string.defense);
                    potion_choice.setVisibility(View.VISIBLE);
                    equipment_choice.setVisibility(View.VISIBLE);

                } else {
                    my_tour.setVisibility(View.VISIBLE);
                    enemy_tour.setVisibility(View.INVISIBLE);
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

        if(requestCode == OK_EQUIPMENT){
            if(data != null){
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

        }

        if(requestCode == OK_POTION){
            if(data != null){
                Potion potion = data.getParcelableExtra("BATTLE_POTION");
             //  int point = Integer.parseInt(life_myself.getProgress().toString()) + potion.getValue();
                life_myself.setProgress(life_myself.getProgress() + potion.getValue());
                potion_choice.setVisibility(View.INVISIBLE);
            }

        }

    }

    private void attack(){
        mediaPlayer.start();
        Random random = new Random();
        int point = life_enemy.getProgress() - random.nextInt(Integer.parseInt(attack_myself.getText().toString()));//Integer.parseInt(life_enemy.getText().toString()) - random.nextInt(Integer.parseInt(attack_myself.getText().toString()));
        if(point > 0) {
            life_enemy.setProgress(point);
            commentTextView.setText("L'ennemi a été touché avec " + point +" points");
        } else {
            gameover(true);
        }

    }

    private void defense(){
        Random random = new Random();
       int point = life_myself.getProgress() - random.nextInt(Integer.parseInt(attack_enemy.getText().toString()));
        if(point > 0) {
            life_myself.setProgress(point);
            commentTextView.setText("Tu as été touché avec " + point +" points");
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

    private void welcomeMessage() {
        commentTextView.setText("3");
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        commentTextView.setText("2");
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        commentTextView.setText("1");
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        commentTextView.setText("C'est parti!");
                                                        new android.os.Handler().postDelayed(
                                                                new Runnable() {
                                                                    public void run() {
                                                                        commentTextView.setText("");
                                                                        action.setEnabled(true);
                                                                    }
                                                                }, 2000);
                                                    }
                                                }, 2000);
                                    }
                                }, 2000);
                    }
                }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(service);
    }
}
