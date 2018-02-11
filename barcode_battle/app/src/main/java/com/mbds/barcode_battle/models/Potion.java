package com.mbds.barcode_battle.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.mbds.barcode_battle.R;

/**
 * Created by nour_b on 24/10/2017.
 */

public class Potion implements Parcelable {
    int id = -1;
    int value;
    int photo;

    public Potion(int value) {
        this.value = value;

        switch (value) {
            case 25 : this.photo = R.drawable.coeur1; break;
            case 50 : this.photo = R.drawable.coeur2; break;
            case 75 : this.photo = R.drawable.coeur3; break;
        }
    }


    public Potion(int id, int value) {
        this.id = id;
        this.value = value;

        switch (value) {
            case 25 : this.photo = R.drawable.coeur1; break;
            case 50 : this.photo = R.drawable.coeur2; break;
            case 75 : this.photo = R.drawable.coeur3; break;
        }
    }

    public Potion(int id, int value, int photo) {
        this.id = id;
        this.value = value;
        this.photo = photo;
    }

    protected Potion(Parcel in) {
        id = in.readInt();
        value = in.readInt();
        photo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(value);
        dest.writeInt(photo);
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


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
