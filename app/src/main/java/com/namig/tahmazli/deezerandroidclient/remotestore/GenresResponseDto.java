package com.namig.tahmazli.deezerandroidclient.remotestore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record GenresResponseDto(List<GenreDto> data) {

    public record GenreDto(int id,
                           String name,
                           String picture,
                           @SerializedName("picture_small") String pictureSmall,
                           @SerializedName("picture_medium") String pictureMedium,
                           @SerializedName("picture_big") String pictureBig
    ) {
    }
}
