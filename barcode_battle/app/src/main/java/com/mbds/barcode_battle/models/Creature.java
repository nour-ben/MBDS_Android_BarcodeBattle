package com.mbds.barcode_battle.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nour_b on 24/10/2017.
 */

public class Creature implements Parcelable {
    String name;
    Equipment equipment;
    int life;
    int ptAttack;
    int ptDefense;
    int photo;

    public Creature() {}

    public Creature(String name, int ptAttack, int ptDefense, int photo) {
        this.name = name;
        this.ptAttack = ptAttack;
        this.ptDefense = ptDefense;
        this.life = 100;
        this.photo = photo;
      //  this.equipment = new Equipment();
    }

    public Creature(Parcel in) {
        name = in.readString();
        life = in.readInt();
        ptAttack = in.readInt();
        ptDefense = in.readInt();
        photo = in.readInt();
    }

    public static final Creator<Creature> CREATOR = new Creator<Creature>() {
        @Override
        public Creature createFromParcel(Parcel in) {
            return new Creature(in);
        }

        @Override
        public Creature[] newArray(int size) {
            return new Creature[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(life);
        dest.writeInt(ptAttack);
        dest.writeInt(ptDefense);
        dest.writeInt(photo);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getPtAttack() {
        return ptAttack;
    }

    public void setPtAttack(int ptAttack) {
        this.ptAttack = ptAttack;
    }

    public int getPtDefense() {
        return ptDefense;
    }

    public void setPtDefense(int ptDefense) {
        this.ptDefense = ptDefense;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
