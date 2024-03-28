package com.namig.tahmazli.deezerandroidclient.genres.view;

import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.MvpView;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface GenresView extends MvpView {
    void showLoader();

    void hideLoader();

    void setErrorText(@Nullable final String message);

    void showError();

    void hideError();

    void displayGenres(final List<Genre> genres);

    void hideGenres();

    void enqueueSharedElementTransition(final Genre genre);

    void startSharedElementReturnTransition(final Genre genre);

    interface Listener {
        void onGenreClicked(final Genre genre);
    }
}
