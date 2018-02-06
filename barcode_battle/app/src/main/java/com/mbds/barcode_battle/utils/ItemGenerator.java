package com.mbds.barcode_battle.utils;

import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Equipment;
import com.mbds.barcode_battle.models.Potion;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by nour.benmoussa on 05/02/2018.
 */

public class ItemGenerator {

    private static HashMap<Integer, Object> itemList;

    public static void init () {

        // CREATURES
        Creature c1 = new Creature("PikaCacahuète", 100, 100, "1.png");
        Creature c2 = new Creature("PikaChurros", 100, 100, "2.png");
        Creature c3 = new Creature("Gaufrichu", 100, 100, "3.png");
        Creature c4 = new Creature("Framboichu", 100, 100, "4.png");
        Creature c5 = new Creature("Pikachette", 100, 100, "5.png");
        Creature c6 = new Creature("Crèpachu", 100, 100, "6.png");
        Creature c7 = new Creature("Beignechu", 100, 100, "7.png");

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


    public static Object generator(int i) {

        return itemList.get(i%itemList.size());

    }

}