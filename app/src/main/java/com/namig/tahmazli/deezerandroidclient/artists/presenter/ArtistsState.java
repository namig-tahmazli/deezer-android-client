package com.namig.tahmazli.deezerandroidclient.artists.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

class ArtistsState implements Parcelable {
    @Nullable
    Genre genre;

    protected ArtistsState(Parcel in) {
        genre = in.readParcelable(Genre.class.getClassLoader());
    }

    ArtistsState() {
    }

    public static final Creator<ArtistsState> CREATOR = new Creator<>() {
        @Override
        public ArtistsState createFromParcel(Parcel in) {
            return new ArtistsState(in);
        }

        @Override
        public ArtistsState[] newArray(int size) {
            return new ArtistsState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(genre, flags);
    }
}
