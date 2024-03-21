package com.namig.tahmazli.deezerandroidclient.remotestore;

import com.namig.tahmazli.deezerandroidclient.error.AppError;

import javax.inject.Inject;

class RemoteStoreImpl implements RemoteStore {

    private final DeezerApi mApi;

    @Inject
    RemoteStoreImpl(final DeezerApi api) {
        mApi = api;
    }

    @Override
    public GenresResponseDto fetchGenres() throws AppError.NetworkError {
        return NetworkUtils.executeCall(mApi.fetchGenres());
    }
}
