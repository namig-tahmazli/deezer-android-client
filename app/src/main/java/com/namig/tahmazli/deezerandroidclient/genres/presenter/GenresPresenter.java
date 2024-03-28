package com.namig.tahmazli.deezerandroidclient.genres.presenter;

import androidx.lifecycle.SavedStateHandle;

import com.namig.tahmazli.deezerandroidclient.genres.view.GenresView;
import com.namig.tahmazli.deezerandroidclient.error.AppError;
import com.namig.tahmazli.deezerandroidclient.interactors.FetchGenresAndCacheThemUseCase;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.presenter.Presenter;

import java.util.List;

public class GenresPresenter
        extends Presenter<GenresView, GenresState>
        implements FetchGenresAndCacheThemUseCase.Listener {

    public GenresPresenter(final SavedStateHandle handle) {
        super(handle);
    }

    @Override
    protected GenresState initialState() {
        return new GenresState();
    }

    @Override
    protected void initViewWithLatestState(GenresView genresView) {
        if (mState.showLoading) {
            genresView.showLoader();
        } else {
            genresView.hideLoader();
        }

        genresView.setErrorText(mState.errorContent);
        if (mState.showError) {
            genresView.showError();
        } else {
            genresView.hideError();
        }

        genresView.displayGenres(mState.loadedGenres);

        if (mState.transitioningGenre != null)
            genresView.startSharedElementReturnTransition(mState.transitioningGenre);
    }

    @Override
    public void showGenresLoadingIndicator() {
        mState.showLoading = true;
        updateView(GenresView::showLoader);
    }

    @Override
    public void displayGenres(List<Genre> genres) {
        mState.loadedGenres = genres;
        updateView(v -> v.displayGenres(genres));
    }

    @Override
    public void hideGenresLoadingIndicator() {
        mState.showLoading = false;
        updateView(GenresView::hideLoader);
    }

    @Override
    public void showFetchingGenresFailed(AppError error) {
        mState.showError = true;
        mState.errorContent = error.getMessage();
        updateView(v -> {
            v.showError();
            v.setErrorText(mState.errorContent);
        });
    }

    @Override
    public void hideFetchingGenresFailed() {
        mState.showError = false;
        mState.errorContent = null;
        updateView(v -> {
            v.showError();
            v.setErrorText(null);
        });
    }

    public void startSharedElementTransition(final Genre genre) {
        mState.transitioningGenre = genre;
        updateView(v -> v.startSharedElementTransition(genre));
    }
}
