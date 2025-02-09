package com.namig.tahmazli.deezerandroidclient.artists.view;

import com.namig.tahmazli.deezerandroidclient.interactors.Artist;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.view.MvpView;

import java.util.List;

public interface ArtistsView extends MvpView {
    void displayGenre(final Genre genre);
    void startSharedElementTransition(final Genre genre);
    void startSharedElementReturnTransition(final Artist artist);
    void enqueueSharedElementTransition(final Artist artist);
    void enqueueSharedElementReturnTransition();
    void displayLoader();
    void hideLoader();
    void displayArtists(final List<Artist> artists);

    interface Listener {
        void onSharedElementReturnTransitionEnqueued();
        void onSharedElementTransitionEnqueued();
        void onArtistClicked(final Artist artist);
    }
}
