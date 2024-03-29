package com.namig.tahmazli.deezerandroidclient.artists.presenter;

import androidx.lifecycle.SavedStateHandle;

import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsView;
import com.namig.tahmazli.deezerandroidclient.interactors.Artist;
import com.namig.tahmazli.deezerandroidclient.interactors.FetchArtistsAndDisplayThemUseCase;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.presenter.Presenter;

import java.util.List;

public class ArtistsPresenter extends Presenter<ArtistsView, ArtistsState>
    implements FetchArtistsAndDisplayThemUseCase.Listener {
    public ArtistsPresenter(SavedStateHandle handle) {
        super(handle);
    }

    @Override
    protected ArtistsState initialState() {
        return new ArtistsState();
    }

    @Override
    protected void initViewWithLatestState(ArtistsView artistsView) {
        final Genre genre = mState.genre;
        if (genre != null) {
            artistsView.displayGenre(genre);
            artistsView.startSharedElementTransition(genre);

            mState.isSharedElementTransitionStarted = true;
        }

        if (mState.isLoading) {
            artistsView.displayLoader();
        } else {
            artistsView.hideLoader();
        }

        artistsView.displayArtists(mState.loadedArtists);
    }

    public void displayGenreInfo(final Genre genre) {
        mState.genre = genre;
        updateView(v -> v.displayGenre(genre));
    }

    public void startSharedElementReturnTransition() {
        updateView(ArtistsView::enqueueSharedElementReturnTransition);
    }

    @Override
    public void showFetchingArtists() {
        mState.isLoading = true;
        updateView(ArtistsView::displayLoader);
    }

    @Override
    public void hideFetchingArtists() {
        mState.isLoading = false;
        updateView(ArtistsView::hideLoader);
    }

    @Override
    public void showFetchingArtistsFailed() {

    }

    @Override
    public void hideFetchingArtistsFailed() {

    }

    @Override
    public void showArtists(List<Artist> artists) {
        mState.loadedArtists = artists;
        updateView(v -> v.displayArtists(artists));
    }
}
