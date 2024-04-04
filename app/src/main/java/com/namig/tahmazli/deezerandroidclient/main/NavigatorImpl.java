package com.namig.tahmazli.deezerandroidclient.main;

import android.os.Bundle;

import com.namig.tahmazli.deezerandroidclient.artist.ArtistFragment;
import com.namig.tahmazli.deezerandroidclient.artist.ArtistViewModel;
import com.namig.tahmazli.deezerandroidclient.artists.ArtistsFragment;
import com.namig.tahmazli.deezerandroidclient.artists.ArtistsViewModel;
import com.namig.tahmazli.deezerandroidclient.genres.GenresFragment;
import com.namig.tahmazli.deezerandroidclient.interactors.Artist;
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
        bundle.putParcelable(ArtistsViewModel.EXTRA_GENRE, genre);
        mNavigationManager.navigateTo(new ArtistsFragment(), bundle);
    }

    @Override
    public void navigateToArtist(Artist artist, String genreImage) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(ArtistViewModel.EXTRA_ARTIST, artist);
        bundle.putString(ArtistViewModel.EXTRA_GENRE_IMAGE, genreImage);
        mNavigationManager.navigateTo(new ArtistFragment(), bundle);
    }

    @Override
    public void navigateBack() {
        mNavigationManager.navigateBack();
    }
}
