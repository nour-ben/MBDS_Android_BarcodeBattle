package com.mbds.barcode_battle.utils;

import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by nour.benmoussa on 05/02/2018.
 */

public class ItemGenerator {

    private static HashMap<Integer, Object> itemList = new HashMap<>();

    public static void init () {

        // CREATURES
        Creature c1 = new Creature("PikaCacahuète", 100, 100, R.drawable.p1);
        Creature c2 = new Creature("PikaChurros", 100, 100,R.drawable.p2);
        Creature c3 = new Creature("Gaufrichu", 100, 100, R.drawable.p3);
        Creature c4 = new Creature("Framboichu", 100, 100, R.drawable.p4);
        Creature c5 = new Creature("Pikachette", 100, 100,R.drawable.p5);
        Creature c6 = new Creature("Crèpachu", 100, 100, R.drawable.p6);
        Creature c7 = new Creature("Beignechu", 100, 100, R.drawable.p7);

        itemList.put(1, c1);
        itemList.put(2, c2);
        itemList.put(3, c3);
        itemList.put(4, c4);
        itemList.put(5, c5);
        itemList.put(6, c6);
        itemList.put(7, c7);


        // EQUIPMENTS
        Equipment e1 = new Equipment("Bouclier", "Défense", 20, "");
        Equipment e2 = new Equipment("Sabre laser", "Attaque", 40, "");
        Equipment e3 = new Equipment("Attaque éclair", "Attaque", 60, "");
        Equipment e4 = new Equipment("Marmite", "Défense", 30, "");

        itemList.put(8, e1);
        itemList.put(9, e2);
        itemList.put(10, e3);
        itemList.put(11, e4);


        // POTIONS
        Potion p1 = new Potion(75);
        Potion p2 = new Potion(50);
        Potion p3 = new Potion(25);

        itemList.put(12, p1);
        itemList.put(13, p2);
        itemList.put(14, p3);

    }


    public static Object generator(Long i) {

        if(i == null) {
            return null;
        }
        System.out.println("le id est "+(int) (long) i%itemList.size());
        return itemList.get((int) (long) i%itemList.size());

    }

}