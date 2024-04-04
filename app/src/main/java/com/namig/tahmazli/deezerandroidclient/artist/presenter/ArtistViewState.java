package com.namig.tahmazli.deezerandroidclient.artist.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namig.tahmazli.deezerandroidclient.interactors.Artist;

public final class ArtistViewState implements Parcelable {
    @Nullable
    Artist artist;
    @Nullable
    String genreImage;
    boolean isSharedElementTransitionStarted = false;

    ArtistViewState(Parcel in) {
        artist = in.readParcelable(Artist.class.getClassLoader());
        genreImage = in.readString();
        isSharedElementTransitionStarted = in.readByte() != 0;
    }

    ArtistViewState() {
    }

    public static final Creator<ArtistViewState> CREATOR = new Creator<>() {
        @Override
        public ArtistViewState createFromParcel(Parcel in) {
            return new ArtistViewState(in);
        }

        @Override
        public ArtistViewState[] newArray(int size) {
            return new ArtistViewState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (artist != null)
            dest.writeParcelable(artist, flags);
        if (genreImage != null)
            dest.writeString(genreImage);
        dest.writeByte((byte) (isSharedElementTransitionStarted ? 1 : 0));
    }
}
