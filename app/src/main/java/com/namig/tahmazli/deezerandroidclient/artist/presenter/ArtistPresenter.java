package com.namig.tahmazli.deezerandroidclient.artist.presenter;

import androidx.lifecycle.SavedStateHandle;

import com.namig.tahmazli.deezerandroidclient.artist.view.ArtistView;
import com.namig.tahmazli.deezerandroidclient.interactors.Artist;
import com.namig.tahmazli.deezerandroidclient.utils.presenter.Presenter;

public final class ArtistPresenter extends Presenter<ArtistView, ArtistViewState> {
    public ArtistPresenter(final SavedStateHandle handle) {
        super(handle);
    }

    @Override
    protected ArtistViewState initialState() {
        return new ArtistViewState();
    }

    @Override
    protected void initViewWithLatestState(ArtistView artistView) {
        if (mState.artist != null) {
            artistView.showArtistImage(mState.artist.imageLarge());
            artistView.showArtistName(mState.artist.name());
        }

        if (mState.genreImage != null) {
            artistView.showGenreImage(mState.genreImage);

            if(!mState.isSharedElementTransitionStarted) {
                mState.isSharedElementTransitionStarted = true;
                artistView.startSharedElementTransition();
            }
        }
    }

    public void displayArtistInfo(final Artist artist) {
        mState.artist = artist;
        updateView(v -> {
            v.showArtistImage(artist.imageLarge());
            v.showArtistName(artist.name());
        });
    }

    public void displayGenreImage(final String genreImage) {
        mState.genreImage = genreImage;
        updateView(v -> v.showGenreImage(genreImage));
    }

    public void startSharedElementReturnTransition() {
        updateView(ArtistView::enqueueSharedElementReturnTransition);
    }
}
