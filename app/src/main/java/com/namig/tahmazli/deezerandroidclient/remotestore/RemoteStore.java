package com.namig.tahmazli.deezerandroidclient.remotestore;

import com.namig.tahmazli.deezerandroidclient.error.AppError;

public interface RemoteStore {
    GenresResponseDto fetchGenres() throws AppError.NetworkError;
}
