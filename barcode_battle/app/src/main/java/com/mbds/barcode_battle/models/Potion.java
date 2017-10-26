package com.mbds.barcode_battle.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nour_b on 24/10/2017.
 */

public class Potion implements Parcelable {
    String name;
    String effect;
    String photo;

    public Potion() {}

    public Potion(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public Potion(Parcel in) {
        name = in.readString();
        effect = in.readString();
        photo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(effect);
        dest.writeString(photo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Potion> CREATOR = new Creator<Potion>() {
        @Override
        public Potion createFromParcel(Parcel in) {
            return new Potion(in);
        }

        @Override
        public Potion[] newArray(int size) {
            return new Potion[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
