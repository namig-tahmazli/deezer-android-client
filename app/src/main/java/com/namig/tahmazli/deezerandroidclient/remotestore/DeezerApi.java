package com.namig.tahmazli.deezerandroidclient.remotestore;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface DeezerApi {

    @GET("/genre/")
    Call<GenresResponseDto> fetchGenres();

    @GET("/genre/{genre_id}/artists")
    Call<ArtistsResponseDto> fetchArtists(@Path("genre_id") final int genreId);
}
