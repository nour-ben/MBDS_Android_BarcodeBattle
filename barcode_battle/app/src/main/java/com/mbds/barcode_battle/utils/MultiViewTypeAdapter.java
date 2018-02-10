package com.mbds.barcode_battle.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbds.barcode_battle.R;
import com.mbds.barcode_battle.localDatabase.DatabaseHelper;
import com.mbds.barcode_battle.models.Creature;
import com.mbds.barcode_battle.models.Potion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yasmine on 07/02/2018.
 */

public class MultiViewTypeAdapter extends RecyclerView.Adapter{

    private ArrayList<HashMap<String,String>>  dataSet;
    Context mContext;
    int total_types;

  public MultiViewTypeAdapter(ArrayList<HashMap<String,String>> data, Context context) {
      this.dataSet = data;
      System.out.println("hhhhhh dans le cons "+data);
      this.mContext = context;
      total_types = dataSet.size();
  }
    public static class CreatureViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView attaque;
        public TextView defense;
        public ImageView avatar;

        public CreatureViewHolder(View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.title_list);
            this.attaque = (TextView) itemView.findViewById(R.id.attaque_list);
            this.defense = (TextView) itemView.findViewById(R.id.defense_list);
            this.avatar = (ImageView) itemView.findViewById(R.id.photo_list);
        }
    }

    public static class EquipementViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView effect;
        public TextView value;
        public ImageView avatar;

        public EquipementViewHolder(View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.title_list);
            this.effect = (TextView) itemView.findViewById(R.id.effect_list);
            this.value = (TextView) itemView.findViewById(R.id.value_list);
            this.avatar = (ImageView) itemView.findViewById(R.id.photo_list);
        }
    }
    public static class PotionViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public TextView value;

        public PotionViewHolder(View itemView) {
            super(itemView);

            this.avatar = (ImageView) itemView.findViewById(R.id.photo_list);
            this.value = (TextView) itemView.findViewById(R.id.value_list);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_creature, parent, false);
                return new CreatureViewHolder(view);
           case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipment, parent, false);
                return new EquipementViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potion, parent, false);
               return new PotionViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        if(dataSet.get(position).get(DatabaseHelper.COL_NAME_CREATURE) != null) {
            return 0;
        }else if(dataSet.get(position).get(DatabaseHelper.COL_NAME_EQUIPMENT) != null){
            return 1;
        }else{
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        HashMap<String,String> object = dataSet.get(listPosition);

        if (object != null) {
            if(object.get(DatabaseHelper.COL_NAME_CREATURE) != null) {
                ((CreatureViewHolder) holder).name.setText(object.get(DatabaseHelper.COL_NAME_CREATURE));
                ((CreatureViewHolder) holder).attaque.setText(object.get(DatabaseHelper.COL_PTATTACK_CREATURE));
                ((CreatureViewHolder) holder).defense.setText(object.get(DatabaseHelper.COL_PTDEFENSE_CREATURE));
           //     ((CreatureViewHolder) holder).avatar.setImageResource(Integer.parseInt(object.get(DatabaseHelper.COL_PHOTO_CREATURE)));

            }else if(object.get(DatabaseHelper.COL_NAME_EQUIPMENT) != null){
                ((EquipementViewHolder) holder).name.setText(object.get(DatabaseHelper.COL_NAME_EQUIPMENT));
                ((EquipementViewHolder) holder).effect.setText(object.get(DatabaseHelper.COL_TYPE_EQUIPMENT));
//                ((EquipementViewHolder) holder).avatar.setImageResource(Integer.parseInt(object.get(DatabaseHelper.COL_PHOTO_EQUIPMENT)));
            }else{
                ((PotionViewHolder) holder).value.setText(object.get(DatabaseHelper.COL_VALUE_POTION));
           //     ((PotionViewHolder) holder).avatar.setImageResource(Integer.parseInt(object.get(DatabaseHelper.COL_PHOTO_POTION)));

            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    }
