package com.namig.tahmazli.deezerandroidclient.interactors;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public record Artist(int id,
                     String name,
                     String image,
                     String imageLarge) implements Parcelable {
    public Artist(Parcel in) {
        this(in.readInt(),
                in.readString(),
                in.readString(),
                in.readString());
    }

    public static final Creator<Artist> CREATOR = new Creator<>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(imageLarge);
    }
}
