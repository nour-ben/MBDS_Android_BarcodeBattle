package com.mbds.barcode_battle.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nour_b on 24/10/2017.
 */

public class DBHandler {

    protected static final int VERSION_BDD = 1;
    protected static final String NOM_BDD = "battle.db";

    protected SQLiteDatabase db;
    protected DatabaseHelper databaseHelper;

    public DBHandler(Context context) {
        databaseHelper = new DatabaseHelper(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open() {
        db = databaseHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }


    //////////////////////////////////////////////////////////////////////
    // EQUIPMENT //
    //////////////////////////////////////////////////////////////////////


    public void createEquipment (Equipment equipment) {
        open();
        if (equipment != null  ) {
            ContentValues store = new ContentValues();
            store.put(DatabaseHelper.COL_NAME_EQUIPMENT, equipment.getName());
            store.put(DatabaseHelper.COL_TYPE_EQUIPMENT, equipment.getType());
            store.put(DatabaseHelper.COL_VALUE_EQUIPMENT, equipment.getValue());
            store.put(DatabaseHelper.COL_PHOTO_EQUIPMENT, equipment.getPhoto());
            db.insert(DatabaseHelper.TABLE_EQUIPMENT, null, store);
        }
        close();
    }

    public Equipment readEquipment (String name) {
        open();
        Cursor c = db.query(DatabaseHelper.TABLE_EQUIPMENT,
                new String[]{   DatabaseHelper.COL_NAME_EQUIPMENT, DatabaseHelper.COL_TYPE_EQUIPMENT,
                        DatabaseHelper.COL_VALUE_EQUIPMENT, DatabaseHelper.COL_PHOTO_EQUIPMENT
                },
                DatabaseHelper.COL_NAME_EQUIPMENT + " = ?",
                new String[] { name },
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            Equipment equipment = new Equipment(c.getString(0), c.getString(1), c.getInt(2), c.getInt(3));
            equipment.setPhoto(c.getInt(2));
            c.close(); // close the cursor
            return equipment;
        }
        close();
        return null;
    }

    public ArrayList<HashMap<String,String>> readAllEquipments () {
        open();
        ArrayList<HashMap<String,String>> equipments = new ArrayList<HashMap<String,String>>();
        Cursor c = db.query(DatabaseHelper.TABLE_EQUIPMENT,
                new String[]{   DatabaseHelper.COL_NAME_EQUIPMENT, DatabaseHelper.COL_TYPE_EQUIPMENT,
                        DatabaseHelper.COL_VALUE_EQUIPMENT, DatabaseHelper.COL_PHOTO_EQUIPMENT
                },
                null, null, null, null, null, null);
        if (c != null)  {
            for(c.moveToFirst(); c.isAfterLast() == false; c.moveToNext()) {   // add items to the list
                HashMap<String,String> equipment = new HashMap<String, String>();
                equipment.put(DatabaseHelper.COL_NAME_EQUIPMENT, c.getString(0));
                equipment.put(DatabaseHelper.COL_TYPE_EQUIPMENT, c.getString(1));
                equipment.put(DatabaseHelper.COL_VALUE_EQUIPMENT, c.getString(2));
                equipment.put(DatabaseHelper.COL_PHOTO_EQUIPMENT, c.getString(3));
                equipments.add(equipment);
            }
            c.close(); // close the cursor
            return equipments;
        }
        return null;
    }

    public void deleteEquipment (String name) {
        open();
        db.delete(DatabaseHelper.TABLE_EQUIPMENT, DatabaseHelper.COL_NAME_EQUIPMENT + "= ?", new String[]{ name });
        close();
    }

    public void deleteAllEquipments(){
        db.delete(DatabaseHelper.TABLE_EQUIPMENT, null, null);
    }

    public int getEquipmentId (String name) {
        open();
        Cursor c = db.query(DatabaseHelper.TABLE_EQUIPMENT,
                new String[]{   DatabaseHelper.COL_ID_EQUIPMENT },
                DatabaseHelper.COL_NAME_EQUIPMENT + " = ?",
                new String[] { name },
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            int id = c.getInt(0);
            c.close(); // close the cursor
            return id;
        }
        close();
        return -1;
    }


    //////////////////////////////////////////////////////////////////////
    // POTION //
    //////////////////////////////////////////////////////////////////////

    public void createPotion (Potion potion) {
        open();
        if (potion != null  ) {
            ContentValues store = new ContentValues();
            store.put(DatabaseHelper.COL_VALUE_POTION, potion.getValue());
            db.insert(DatabaseHelper.TABLE_POTION, null, store);
        }
        close();
    }

    public ArrayList<HashMap<String,String>> readAllPotions () {
        open();
        ArrayList<HashMap<String,String>> potions = new ArrayList<HashMap<String,String>>();
        Cursor c = db.query(DatabaseHelper.TABLE_POTION,
                new String[]{   DatabaseHelper.COL_VALUE_POTION },
                null, null, null, null, null, null);
        if (c != null)  {
            for(c.moveToFirst(); c.isAfterLast() == false; c.moveToNext()) {   // add items to the list
                HashMap<String,String> potion = new HashMap<String, String>();
                potion.put(DatabaseHelper.COL_VALUE_POTION, c.getString(0));
                potions.add(potion);
            }
            c.close(); // close the cursor
            return potions;
        }
        return null;
    }

    public void deletePotion (String name) {
        open();
        db.delete(DatabaseHelper.TABLE_POTION, DatabaseHelper.COL_VALUE_POTION + "=?", new String[]{ name });
        close();
    }

    public void deleteAllPotions(){
        db.delete(DatabaseHelper.TABLE_POTION, null, null);
    }


    //////////////////////////////////////////////////////////////////////
    // CREATURES //
    //////////////////////////////////////////////////////////////////////


    public void createCreature (Creature c) {
        open();
        if (c != null  ) {
            ContentValues store = new ContentValues();
            store.put(DatabaseHelper.COL_NAME_CREATURE, c.getName());
            store.put(DatabaseHelper.COL_PTATTACK_CREATURE, c.getPtAttack());
            store.put(DatabaseHelper.COL_PTDEFENSE_CREATURE, c.getPtDefense());
            store.put(DatabaseHelper.COL_PHOTO_CREATURE, c.getPhoto());
            db.insert(DatabaseHelper.TABLE_CREATURE, null, store);
        }
        close();
    }

    public Creature readCreature (String name) {
        open();
        Cursor c = db.query(DatabaseHelper.TABLE_CREATURE,
                new String[]{   DatabaseHelper.COL_NAME_CREATURE, DatabaseHelper.COL_PTATTACK_CREATURE,
                        DatabaseHelper.COL_PTDEFENSE_CREATURE, DatabaseHelper.COL_PHOTO_CREATURE
                },
                DatabaseHelper.COL_NAME_CREATURE + " = ?",
                new String[] { name },
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            Creature creature = new Creature(c.getString(0), c.getInt(1), c.getInt(2), c.getInt(3));
            creature.setPhoto(c.getInt(3));
            creature.setEquipment(readEquipment(c.getString(4)));
            c.close(); // close the cursor
            return creature;
        }
        close();
        return null;
    }

    public ArrayList<HashMap<String,String>> readAllCreatures () {
        open();
        ArrayList<HashMap<String,String>> creatures = new ArrayList<HashMap<String,String>>();
        Cursor c = db.query(DatabaseHelper.TABLE_CREATURE,
                new String[]{   DatabaseHelper.COL_NAME_CREATURE, DatabaseHelper.COL_PTATTACK_CREATURE,
                        DatabaseHelper.COL_PTDEFENSE_CREATURE, DatabaseHelper.COL_PHOTO_CREATURE
                },
                null, null, null, null, null, null);
        if (c != null)  {
            for(c.moveToFirst(); c.isAfterLast() == false; c.moveToNext()) {   // add items to the list
                HashMap<String,String> creature = new HashMap<String, String>();
                creature.put(DatabaseHelper.COL_NAME_CREATURE, c.getString(0));
                creature.put(DatabaseHelper.COL_PTATTACK_CREATURE, c.getString(1));
                creature.put(DatabaseHelper.COL_PTDEFENSE_CREATURE, c.getString(2));
                creature.put(DatabaseHelper.COL_PHOTO_CREATURE, c.getString(3));
                creatures.add(creature);
            }
            c.close(); // close the cursor
            return creatures;
        }
        return null;
    }


    public void deleteCreature (String name) {
        open();
        db.delete(DatabaseHelper.TABLE_CREATURE, DatabaseHelper.COL_NAME_CREATURE + "=?", new String[]{name});
        close();
    }

    public void deleteAllCreatures(){
        db.delete(DatabaseHelper.TABLE_CREATURE, null, null);
    }

    public int getCreatureId (String name) {
        open();
        Cursor c = db.query(DatabaseHelper.TABLE_CREATURE,
                new String[]{   DatabaseHelper.COL_ID_CREATURE },
                DatabaseHelper.COL_NAME_CREATURE + " = ?",
                new String[] { name },
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            int id = c.getInt(0);
            c.close(); // close the cursor
            return id;
        }
        close();
        return -1;
    }

}
