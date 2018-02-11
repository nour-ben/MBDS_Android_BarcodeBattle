package com.mbds.barcode_battle.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.localDatabase.DBHandler;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;
import com.mbds.barcode_battle.utils.ItemGenerator;

public class ItemActivity extends AppCompatActivity {

    String itemType;
    int id;

    TextView name;
    TextView value1_description;
    TextView value1;
    TextView value2_description;
    TextView value2;
    ImageView photo;

    ImageView play;
    ImageView trash;

    Creature creature = null;
    Equipment equipment = null;
    Potion potion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        name = (TextView) findViewById(R.id.name);
        value1_description = (TextView) findViewById(R.id.value1_description);
        value1 = (TextView) findViewById(R.id.value1);
        value2_description = (TextView) findViewById(R.id.value2_description);
        value2 = (TextView) findViewById(R.id.value2);
        photo = (ImageView) findViewById(R.id.photo);

        play = (ImageView) findViewById(R.id.play);
        trash = (ImageView) findViewById(R.id.trash);


        Bundle bundle = getIntent().getExtras();

        /// CREATURES
        if (bundle.getParcelable(ItemGenerator.CREATURE) != null){
            itemType = ItemGenerator.CREATURE;
            creature = bundle.getParcelable(ItemGenerator.CREATURE);
            id = creature.getId();
            System.out.println(id);
            name.setText(creature.getName());
            value1_description.setText(R.string.attack);
            value1.setText(Integer.toString(creature.getPtAttack()));
            value2_description.setText(R.string.defense);
            value2.setText(Integer.toString(creature.getPtDefense()));
            photo.setImageResource(creature.getPhoto());
        }

        /// EQUIPEMENTS
        if (bundle.getParcelable(ItemGenerator.EQUIPMENT) != null){
            itemType = ItemGenerator.EQUIPMENT;
            equipment = bundle.getParcelable(ItemGenerator.EQUIPMENT);
            id = equipment.getId();
            name.setText(equipment.getName());
            value1_description.setText(R.string.type);
            value1.setText(equipment.getType());
            value2_description.setText(R.string.value);
            value2.setText(Integer.toString(equipment.getValue()));
            photo.setImageResource(equipment.getPhoto());
            play.setVisibility(View.INVISIBLE);
        }

        /// POTIONS
        if (bundle.getParcelable(ItemGenerator.POTION) != null){
            itemType = ItemGenerator.POTION;
            potion = bundle.getParcelable(ItemGenerator.POTION);
            id = potion.getId();
            name.setVisibility(View.INVISIBLE);
            value1_description.setText(R.string.value);
            value1.setText(Integer.toString(potion.getValue()));
            value2_description.setVisibility(View.INVISIBLE);
            value2.setVisibility(View.INVISIBLE);
            photo.setImageResource(potion.getPhoto());
            play.setVisibility(View.INVISIBLE);
        }


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(ItemActivity.this);

                ad.setCancelable(false);
                ad.setIcon(android.R.drawable.ic_dialog_alert);
                ad.setMessage("Choisis le type de combat à lancer avec ce Pikachu : ");
                ad.setNegativeButton("EN LOCAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(ItemActivity.this, BattleActivity.class);
                        intent.putExtra("COMBAT", creature);
                        startActivity(intent);
                        finish();
                    }
                });
                ad.setPositiveButton("EN RESEAU", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(ItemActivity.this, BattleActivity.class);
                        intent.putExtra("COMBAT", creature);
                        startActivity(intent);
                        finish();
                    }
                });

                ad.show();
            }
        });

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(ItemActivity.this);
                ad.setCancelable(false);
                ad.setIcon(android.R.drawable.ic_dialog_alert);
                ad.setMessage("Confirme la suppression : ");
                ad.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        DBHandler dbHandler = new DBHandler(ItemActivity.this);
                        dbHandler.open();

                        switch (itemType) {
                            case ItemGenerator.CREATURE : dbHandler.deleteCreature(id); break;
                            case ItemGenerator.EQUIPMENT : dbHandler.deleteEquipment(id); break;
                            case ItemGenerator.POTION : dbHandler.deletePotion(id); break;
                        }

                        Intent intent = new Intent(ItemActivity.this, ItemsListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                ad.show();
            }
        });





    }
}
