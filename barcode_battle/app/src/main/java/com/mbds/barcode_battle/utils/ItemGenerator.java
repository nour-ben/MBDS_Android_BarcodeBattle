package com.mbds.barcode_battle.utils;

import android.content.Context;

import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.localDatabase.DBHandler;
import com.mbds.barcode_battle.localDatabase.DatabaseHelper;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by nour.benmoussa on 05/02/2018.
 */

public class ItemGenerator {

    public static final String CREATURE = "Créature";
    public static final String EQUIPMENT = "Equipement";
    public static final String POTION = "Potion";

    private static HashMap<Integer, Creature> creaturesList = new HashMap<>();
    private static HashMap<Integer, Equipment> equipmentsList = new HashMap<>();
    private static HashMap<Integer, Potion> potionsList = new HashMap<>();


    public static void init (Context context) {

        // CREATURES
        Creature c1 = new Creature("PikaCacahuète", 100, 100, R.drawable.p1);
        Creature c2 = new Creature("PikaChurros", 100, 100,R.drawable.p2);
        Creature c3 = new Creature("Gaufrichu", 100, 100, R.drawable.p3);
        Creature c4 = new Creature("Framboichu", 100, 100, R.drawable.p4);
        Creature c5 = new Creature("Pikachette", 100, 100,R.drawable.p5);
        Creature c6 = new Creature("Crèpachu", 100, 100, R.drawable.p6);
        Creature c7 = new Creature("Beignechu", 100, 100, R.drawable.p7);

        creaturesList.put(1, c1);
        creaturesList.put(2, c2);
        creaturesList.put(3, c3);
        creaturesList.put(4, c4);
        creaturesList.put(5, c5);
        creaturesList.put(6, c6);
        creaturesList.put(7, c7);

        // EQUIPMENTS
        Equipment e1 = new Equipment("Bouclier", "Défense", 20, R.drawable.p1);
        Equipment e2 = new Equipment("Sabre laser", "Attaque", 40, R.drawable.p1);
        Equipment e3 = new Equipment("Attaque éclair", "Attaque", 60, R.drawable.p1);
        Equipment e4 = new Equipment("Marmite", "Défense", 30, R.drawable.p1);

        equipmentsList.put(1, e1);
        equipmentsList.put(2, e2);
        equipmentsList.put(3, e3);
        equipmentsList.put(4, e4);


        // POTIONS
        Potion p1 = new Potion(75);
        Potion p2 = new Potion(50);
        Potion p3 = new Potion(25);

        potionsList.put(1, p1);
        potionsList.put(2, p2);
        potionsList.put(3, p3);

    }


    public static Object generator(Long i) {

        if(i == null)
            return null;

        int type = Math.abs((int) (long) i%3);

        switch (type) {
            case 2 : return creaturesList.get(Math.abs((int) (long) i%creaturesList.size()));
            case 1 : return equipmentsList.get(Math.abs((int) (long) i%equipmentsList.size()));
            case 0: return potionsList.get(Math.abs((int) (long) i%potionsList.size()));
        }

        return 0;
    }

    public static Object generator(String type) {

        Random random = new Random();
        int i;

        switch (type) {
            case CREATURE :     i = random.nextInt(creaturesList.size());
                                return creaturesList.get(i);
            case EQUIPMENT :    i = random.nextInt(equipmentsList.size());
                                return equipmentsList.get(i);
            case POTION :       i = random.nextInt(potionsList.size());
                                return potionsList.get(i);
        }

        return 0;
    }

}