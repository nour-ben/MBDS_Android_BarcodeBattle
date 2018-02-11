package com.mbds.barcode_battle.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by nour_b on 24/10/2017.
 */

public class Equipment implements Parcelable {
    int id = -1;
    String name;
    String type;
    int value;
    int photo;

    public Equipment() {}

    public Equipment(String name, String effect, int value, int photo) {
        this.name = name;
        this.type = effect;
        this.value = value;
        this.photo = photo;
    }

    public Equipment(int id, String name, String effect, int value, int photo) {
        this.id = id;
        this.name = name;
        this.type = effect;
        this.value = value;
        this.photo = photo;
    }


    protected Equipment(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        value = in.readInt();
        photo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(value);
        dest.writeInt(photo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Equipment> CREATOR = new Creator<Equipment>() {
        @Override
        public Equipment createFromParcel(Parcel in) {
            return new Equipment(in);
        }

        @Override
        public Equipment[] newArray(int size) {
            return new Equipment[size];
        }
    };


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
