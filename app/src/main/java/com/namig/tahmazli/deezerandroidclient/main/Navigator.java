package com.namig.tahmazli.deezerandroidclient.main;

import com.namig.tahmazli.deezerandroidclient.interactors.Artist;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

public interface Navigator {
    void navigateToGenres();
    void navigateToArtists(final Genre genre);
    void navigateToArtist(final Artist artist,
                          final String genreImage);
    void navigateBack();
}
