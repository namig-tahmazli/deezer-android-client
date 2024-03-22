package com.namig.tahmazli.deezerandroidclient.interactors;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public record Genre(int id,
                    String name,
                    String image) implements Parcelable {
    Genre(Parcel in) {
        this(in.readInt(),
                in.readString(),
                in.readString());
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
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
    }
}
