package com.namig.tahmazli.deezerandroidclient.remotestore;

import com.namig.tahmazli.deezerandroidclient.error.AppError;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

public interface RemoteStore {
    GenresResponseDto fetchGenres() throws AppError.NetworkError;

    ArtistsResponseDto fetchArtists(final Genre genre) throws AppError.NetworkError;
}
