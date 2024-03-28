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
import com.namig.tahmazli.deezerandroidclient.utils.SharedElementTransition;
import com.namig.tahmazli.deezerandroidclient.utils.ViewUtils;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class GenresViewImpl extends BaseView implements GenresView {

    private final GenresListAdapter mAdapter;
    private final ProgressBar mProgressBar;
    private final RecyclerView mGenresList;
    private final SharedElementTransition mSharedElementTransition;

    public GenresViewImpl(final LayoutInflater inflater,
                          @Nullable final ViewGroup parent,
                          final GenresView.Listener listener,
                          final SharedElementTransition sharedElementTransition) {
        super(inflater, parent, R.layout.fragment_genres);
        mSharedElementTransition = sharedElementTransition;

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
    public void hideGenres() {

    }

    @Override
    public void startSharedElementTransition(Genre genre) {
        final List<Genre> genreList = mAdapter.getCurrentList();
        final OptionalInt index = IntStream.range(0, genreList.size())
                .filter(i -> genreList.get(i).id() == genre.id())
                .findFirst();

        if (index.isPresent()) {
            final var viewHolder = mGenresList.findViewHolderForAdapterPosition(
                    index.getAsInt());
            Objects.requireNonNull(viewHolder,
                    String.format("Could not find view for %s", genre));
            mSharedElementTransition.enqueue(viewHolder.itemView, "genre_image");
        }
    }

    @Override
    public void startSharedElementReturnTransition(Genre genre) {
        List<Genre> genreList = mAdapter.getCurrentList();
        final int position = IntStream.range(0, genreList.size())
                .filter(i -> genreList.get(i).id() == genre.id())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Could not find genre %s in the list",
                                genre)
                ));

        ViewUtils.getNotifiedWhenViewIsAttached(mGenresList, position,
                v -> mSharedElementTransition.transition(v, "genre_image"));
    }
}
