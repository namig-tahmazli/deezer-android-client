package com.namig.tahmazli.deezerandroidclient.genres.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.BaseView;

import java.util.List;

public class GenresViewImpl extends BaseView implements GenresView {

    private final GenresListAdapter mAdapter;
    private final ProgressBar progressBar;

    public GenresViewImpl(final LayoutInflater inflater,
                          @Nullable final ViewGroup parent,
                          final GenresView.Listener listener) {
        super(inflater, parent, R.layout.fragment_genres);

        final RecyclerView genresList = findViewById(R.id.genres_list);
        final GridLayoutManager layoutManager = new GridLayoutManager(
                getContext(), 2, GridLayoutManager.VERTICAL, false);
        genresList.setLayoutManager(layoutManager);
        mAdapter = GenresListAdapter.newInstance(listener::onGenreClicked);
        genresList.setAdapter(mAdapter);

        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setErrorText(@Nullable String message) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void displayGenres(List<Genre> genres) {
        mAdapter.submitList(genres);
    }

    @Override
    public void hideGenres() {

    }
}
