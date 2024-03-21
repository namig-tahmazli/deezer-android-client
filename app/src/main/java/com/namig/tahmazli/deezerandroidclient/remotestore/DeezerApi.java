package com.namig.tahmazli.deezerandroidclient.remotestore;

import retrofit2.Call;
import retrofit2.http.GET;

interface DeezerApi {

    @GET("/genre/")
    Call<GenresResponseDto> fetchGenres();
}
