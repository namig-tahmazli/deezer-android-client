package com.namig.tahmazli.deezerandroidclient.main;

import android.os.Bundle;

import com.namig.tahmazli.deezerandroidclient.artists.ArtistsFragment;
import com.namig.tahmazli.deezerandroidclient.genres.GenresFragment;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

import javax.inject.Inject;

class NavigatorImpl implements Navigator {
    private final NavigationManager mNavigationManager;

    @Inject
    NavigatorImpl(final NavigationManager navigationManager) {
        this.mNavigationManager = navigationManager;
    }

    @Override
    public void navigateToGenres() {
        mNavigationManager.navigateTo(new GenresFragment(), null);
    }

    @Override
    public void navigateToArtists(Genre genre) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable("genre", genre);
        mNavigationManager.navigateTo(new ArtistsFragment(), bundle);
    }
}
