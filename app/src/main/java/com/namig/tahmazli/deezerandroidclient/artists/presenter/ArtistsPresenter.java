package com.namig.tahmazli.deezerandroidclient.artists.presenter;

import androidx.lifecycle.SavedStateHandle;

import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsView;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.presenter.Presenter;

public class ArtistsPresenter extends Presenter<ArtistsView, ArtistsState> {
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
    }

    public void displayGenreInfo(final Genre genre) {
        mState.genre = genre;
        updateView(v -> v.displayGenre(genre));
    }

    public void startSharedElementReturnTransition() {
        updateView(ArtistsView::enqueueSharedElementReturnTransition);
    }
}
