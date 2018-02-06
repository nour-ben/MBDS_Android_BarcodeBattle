package com.mbds.barcode_battle.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nour_b on 24/10/2017.
 */

public class Potion implements Parcelable {
    int value;

    public Potion(int value) {
        this.value = value;
    }


    protected Potion(Parcel in) {
        value = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
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
}
