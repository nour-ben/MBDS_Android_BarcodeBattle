package com.mbds.barcode_battle.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.localDatabase.DBHandler;
import com.mbds.barcode_battle.localDatabase.DatabaseHelper;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;
import com.mbds.barcode_battle.utils.ItemGenerator;
import com.mbds.barcode_battle.utils.ScanHandler;

public class ItemsListActivity extends AppCompatActivity {

    TextView creature_title;
    ListView creatures_list;
    TextView creatures_nolist;
    TextView equipment_title;
    ListView equipments_list;
    TextView equipments_nolist;
    TextView potions_title;
    ListView potions_list;
    TextView potions_nolist;

    boolean display_creatures = true;
    boolean display_equipment = true;
    boolean display_potions = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        final DBHandler dbHandler = new DBHandler(getApplicationContext());
        dbHandler.open();

        Bundle bundle = getIntent().getExtras();
        if (bundle !=null) {
            String display = bundle.getString("DISPLAY");

            if(display.equals("ALL")) {
                System.out.println(bundle.getString("DISPLAY"));
                display_creatures = true;
                display_equipment = true;
                display_potions = true;
            } else if(display.equals(ItemGenerator.CREATURE)) {
                display_creatures = true;
                display_equipment = false;
                display_potions = false;
            } else if(display.equals(ItemGenerator.EQUIPMENT)){
                display_creatures = false;
                display_equipment = true;
                display_potions = false;
            } else if(display.equals(ItemGenerator.POTION)){
                display_creatures = false;
                display_equipment = false;
                display_potions = true;
            }
        }


        /////////////////////////////////////////////
        //  CREATURES
        /////////////////////////////////////////////


        creatures_list = (ListView) findViewById(R.id.creatures_list);
        creature_title = (TextView) findViewById(R.id.creatures_list_title);
        creatures_nolist = (TextView) findViewById(R.id.creatures_nolist);

        if(display_creatures) {
            creature_title.setVisibility(View.VISIBLE);
            if(dbHandler.readAllCreatures().size() == 0) {
                creatures_nolist.setVisibility(View.VISIBLE);
                creatures_nolist.setText("Liste vide");
            } else {

                ListAdapter listAdapter1 = new SimpleAdapter(
                        getApplicationContext(), dbHandler.readAllCreatures(),
                        R.layout.item_creature,
                        new String[]{DatabaseHelper.COL_ID_CREATURE, DatabaseHelper.COL_NAME_CREATURE, DatabaseHelper.COL_PTATTACK_CREATURE, DatabaseHelper.COL_PTDEFENSE_CREATURE, DatabaseHelper.COL_PHOTO_CREATURE},
                        new int[]{R.id.creature_id_list, R.id.creature_title_list, R.id.creature_attaque_list, R.id.creature_defense_list, R.id.creature_photo_list});

                creatures_list.setAdapter(listAdapter1);

                creatures_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ItemsListActivity.this, ItemActivity.class);
                        String _id = ((TextView) view.findViewById(R.id.creature_id_list)).getText().toString();
                        Creature creature = dbHandler.readCreature(Integer.parseInt(_id));
                        intent.putExtra(ItemGenerator.CREATURE, creature);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }

        /////////////////////////////////////////////
        //  EQUIPEMENTS
        /////////////////////////////////////////////

        equipments_list = (ListView) findViewById(R.id.equipment_list);
        equipment_title = (TextView) findViewById(R.id.equipments_list_title);
        equipments_nolist = (TextView) findViewById(R.id.equipment_nolist);

        if(display_equipment) {
            equipment_title.setVisibility(View.VISIBLE);
            if(dbHandler.readAllEquipments().size() == 0) {
                equipments_nolist.setVisibility(View.VISIBLE);
                equipments_nolist.setText("Liste vide");
            } else {

                ListAdapter listAdapter2 = new SimpleAdapter(
                        getApplicationContext(), dbHandler.readAllEquipments(),
                        R.layout.item_equipment,
                        new String[]{DatabaseHelper.COL_ID_EQUIPMENT, DatabaseHelper.COL_NAME_EQUIPMENT, DatabaseHelper.COL_TYPE_EQUIPMENT, DatabaseHelper.COL_VALUE_EQUIPMENT, DatabaseHelper.COL_PHOTO_EQUIPMENT},
                        new int[]{R.id.equipment_id_list, R.id.equipment_title_list, R.id.equipment_type_list, R.id.equipment_value_list, R.id.equipment_photo_list});

                equipments_list.setAdapter(listAdapter2);


                equipments_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = getIntent().getExtras();
                        if (bundle != null) {
                            if (bundle.getBoolean("PLAYING") == true) {
                                String _id = ((TextView) view.findViewById(R.id.equipment_id_list)).getText().toString();
                                Equipment equipment = dbHandler.readEquipment(Integer.parseInt(_id));
                                dbHandler.deleteEquipment(Integer.parseInt(_id));
                                Intent intent = new Intent();
                                intent.putExtra("BATTLE_EQUIPMENT", equipment);
                                setResult(BattleActivity.OK_EQUIPMENT, intent);
                                finish();
                            } else {
                                Intent intent = new Intent(ItemsListActivity.this, ItemActivity.class);
                                String _id = ((TextView) view.findViewById(R.id.equipment_id_list)).getText().toString();
                                Equipment equipment = dbHandler.readEquipment(Integer.parseInt(_id));
                                intent.putExtra(ItemGenerator.EQUIPMENT, equipment);
                                startActivity(intent);
                                finish();
                            }
                        }else {
                            Intent intent = new Intent(ItemsListActivity.this, ItemActivity.class);
                            String _id = ((TextView) view.findViewById(R.id.equipment_id_list)).getText().toString();
                            Equipment equipment = dbHandler.readEquipment(Integer.parseInt(_id));
                            intent.putExtra(ItemGenerator.EQUIPMENT, equipment);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }

        /////////////////////////////////////////////
        //  POTIONS
        /////////////////////////////////////////////

        potions_list = (ListView) findViewById(R.id.potions_list);
        potions_title = (TextView) findViewById(R.id.potions_list_title);
        potions_nolist = (TextView) findViewById(R.id.potion_nolist);

        if(display_potions){
            potions_title.setVisibility(View.VISIBLE);

            if(dbHandler.readAllPotions().size() == 0) {
                potions_nolist.setVisibility(View.VISIBLE);
                potions_nolist.setText("Liste vide");
            } else {
                ListAdapter listAdapter3 = new SimpleAdapter(
                        getApplicationContext(), dbHandler.readAllPotions(),
                        R.layout.item_potion,
                        new String[]{DatabaseHelper.COL_ID_POTION, DatabaseHelper.COL_VALUE_POTION, DatabaseHelper.COL_PHOTO_POTION},
                        new int[]{  R.id.potion_id_list, R.id.potion_value_list, R.id.potion_photo_list});

                potions_list.setAdapter(listAdapter3);

                potions_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = getIntent().getExtras();
                        if(bundle != null) {
                            if (bundle.getBoolean("PLAYING") == true) {
                                String _id = ((TextView) view.findViewById(R.id.potion_id_list)).getText().toString();
                                Potion potion = dbHandler.readPotion(Integer.parseInt(_id));
                                dbHandler.deletePotion(Integer.parseInt(_id));
                                Intent intent = new Intent();
                                intent.putExtra("BATTLE_POTION", potion);
                                setResult(BattleActivity.OK_POTION, intent);
                                finish();
                            } else {
                                Intent intent = new Intent(ItemsListActivity.this, ItemActivity.class);
                                String _id = ((TextView) view.findViewById(R.id.potion_id_list)).getText().toString();
                                Potion potion = dbHandler.readPotion(Integer.parseInt(_id));
                                intent.putExtra(ItemGenerator.POTION, potion);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Intent intent = new Intent(ItemsListActivity.this, ItemActivity.class);
                            String _id = ((TextView) view.findViewById(R.id.potion_id_list)).getText().toString();
                            Potion potion = dbHandler.readPotion(Integer.parseInt(_id));
                            intent.putExtra(ItemGenerator.POTION, potion);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }

        }
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

        if (id == R.id.action_add) {
            ScanHandler.scanCodeBarre(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
