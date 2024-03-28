package com.namig.tahmazli.deezerandroidclient.main;

import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

public interface Navigator {
    void navigateToGenres();
    void navigateToArtists(final Genre genre);
    void navigateBack();
}
