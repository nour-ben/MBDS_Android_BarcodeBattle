package com.mbds.barcode_battle.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.models.Creature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yasmine on 07/02/2018.
 */

public class CreaturesAdapter extends ArrayAdapter<HashMap<String, String>> {


    public CreaturesAdapter(@NonNull Context context, @NonNull ArrayList<HashMap<String,String>> creatures) {
        super(context, 0, creatures);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_creature,parent, false);
        }

        CreatureViewHolder viewHolder = (CreatureViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CreatureViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.title_list);
            viewHolder.attaque = (TextView) convertView.findViewById(R.id.attaque_list);
            viewHolder.defense = (TextView) convertView.findViewById(R.id.defense_list);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.photo_list);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        HashMap<String, String> item = (HashMap<String, String>) getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(item.get("CREATURE_name"));
        viewHolder.attaque.setText(item.get("CREATURE_ptAttack"));
        viewHolder.defense.setText(item.get("CREATURE_ptDefense"));
        System.out.println("hhhhhhhhhhhhhhhhhhh"+ Integer.parseInt(item.get("CREATURE_photo")));
        //viewHolder.avatar.setImageDrawable(new ColorDrawable(Color.BLUE));//item.get("CREATURE_photo"))
        viewHolder.avatar.setImageResource(Integer.parseInt(item.get("CREATURE_photo")));
        return convertView;
    }

    private class CreatureViewHolder{
        public TextView name;
        public TextView attaque;
        public TextView defense;
        public ImageView avatar;
    }
}
