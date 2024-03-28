package com.namig.tahmazli.deezerandroidclient.artists.view;

import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.MvpView;

public interface ArtistsView extends MvpView {
    void displayGenre(final Genre genre);

    void startBackTransition();
}
