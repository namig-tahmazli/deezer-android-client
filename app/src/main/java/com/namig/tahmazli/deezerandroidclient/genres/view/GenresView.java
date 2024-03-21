package com.namig.tahmazli.deezerandroidclient.genres.view;

import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface GenresView {
    void showLoader();
    void hideLoader();
    void setErrorText(@Nullable final String message);
    void showError();
    void hideError();
    void displayGenres(final List<Genre> genres);
    void hideGenres();
}
