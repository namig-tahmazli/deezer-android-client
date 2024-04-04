package com.namig.tahmazli.deezerandroidclient.artist.view;

import com.namig.tahmazli.deezerandroidclient.interactors.Artist;
import com.namig.tahmazli.deezerandroidclient.utils.view.MvpView;

public interface ArtistView extends MvpView {
    void showArtistImage(final String artistImage);
    void showArtistName(final String artistName);
    void showGenreImage(final String genreImage);
    void startSharedElementTransition();
    void enqueueSharedElementReturnTransition();

    interface Listener {
        void onSharedElementReturnTransitionEnqueued();
    }
}
