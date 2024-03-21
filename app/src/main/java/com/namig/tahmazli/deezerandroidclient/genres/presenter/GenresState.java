package com.namig.tahmazli.deezerandroidclient.genres.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

class GenresState implements Parcelable {
    boolean showError;
    @Nullable
    String errorContent;
    boolean showLoading;

    List<Genre> loadedGenres = new ArrayList<>();

    protected GenresState(Parcel in) {
        showError = in.readByte() != 0;
        errorContent = in.readString();
        showLoading = in.readByte() != 0;
    }

    GenresState() {
        showError = false;
        errorContent = null;
        showLoading = false;
    }

    public static final Creator<GenresState> CREATOR = new Creator<GenresState>() {
        @Override
        public GenresState createFromParcel(Parcel in) {
            return new GenresState(in);
        }

        @Override
        public GenresState[] newArray(int size) {
            return new GenresState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (showError ? 1 : 0));
        dest.writeString(errorContent);
        dest.writeByte((byte) (showLoading ? 1 : 0));
    }
}
