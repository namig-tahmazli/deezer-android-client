package com.namig.tahmazli.deezerandroidclient.remotestore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
*     {
      "id": "230",
      "name": "Kanye West",
      "picture": "https://api.deezer.com/artist/230/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/bb76c2ee3b068726ab4c37b0aabdb57a/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/bb76c2ee3b068726ab4c37b0aabdb57a/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/bb76c2ee3b068726ab4c37b0aabdb57a/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/bb76c2ee3b068726ab4c37b0aabdb57a/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/230/top?limit=50",
      "type": "artist"
    },
* */
public record ArtistsResponseDto(List<ArtistDto> data) {
    public record ArtistDto(int id,
                            String name,
                            String picture,
                            @SerializedName("picture_small") String pictureSmall,
                            @SerializedName("picture_medium") String pictureMedium,
                            @SerializedName("picture_big") String pictureBig,
                            @SerializedName("tracklist") String trackList) {
    }
}
