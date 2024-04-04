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
import com.namig.tahmazli.deezerandroidclient.utils.view.BaseView;
import com.namig.tahmazli.deezerandroidclient.utils.view.SharedElementTransition;
import com.namig.tahmazli.deezerandroidclient.utils.view.ViewUtils;

import java.util.List;

public class GenresViewImpl extends BaseView implements GenresView {

    private final GenresListAdapter mAdapter;
    private final ProgressBar mProgressBar;
    private final RecyclerView mGenresList;
    private final SharedElementTransition mSharedElementTransition;
    private final String[] mSharedElements = new String[]{"genre_image", "genre_title"};
    private final GenresView.Listener mListener;

    public GenresViewImpl(final LayoutInflater inflater,
                          @Nullable final ViewGroup parent,
                          final GenresView.Listener listener,
                          final SharedElementTransition sharedElementTransition) {
        super(inflater, parent, R.layout.fragment_genres);
        mSharedElementTransition = sharedElementTransition;
        mListener = listener;

        mGenresList = findViewById(R.id.genres_list);
        final GridLayoutManager layoutManager = new GridLayoutManager(
                getContext(), 2, GridLayoutManager.VERTICAL, false);
        mGenresList.setLayoutManager(layoutManager);
        mAdapter = GenresListAdapter.newInstance(listener::onGenreClicked);
        mGenresList.setAdapter(mAdapter);

        mProgressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void showLoader() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        mProgressBar.setVisibility(View.INVISIBLE);
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
    public void enqueueSharedElementTransition(Genre genre) {
        final var index = ViewUtils.indexOfItem(mAdapter, genre, (f, s) -> f.id() == s.id());
        mSharedElementTransition.enqueue(mGenresList, index, mSharedElements,
                mListener::onSharedElementTransitionEnqueued);
    }

    @Override
    public void startSharedElementReturnTransition(Genre genre) {
        final var index = ViewUtils.indexOfItem(mAdapter, genre, (f, s) -> f.id() == s.id());
        ViewUtils.getNotifiedWhenViewIsAttached(mGenresList, index,
                v -> mSharedElementTransition.transition(v, mSharedElements));
    }
}
