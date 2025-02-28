package com.namig.tahmazli.deezerandroidclient.artists.view;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.interactors.Artist;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.DimensionUtils;
import com.namig.tahmazli.deezerandroidclient.utils.view.BaseView;
import com.namig.tahmazli.deezerandroidclient.utils.view.SharedElementTransition;
import com.namig.tahmazli.deezerandroidclient.utils.view.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtistsViewImpl extends BaseView implements ArtistsView {
    private final HeaderAdapter mHeaderAdapter;
    private final ArtistsAdapter mArtistsAdapter;
    private final LoaderAdapter mLoaderAdapter;
    private final SharedElementTransition mSharedElementTransition;
    private final RecyclerView mArtistsList;
    private final String[] mSharedElements = new String[]{"genre_image", "genre_title"};
    private static final int SPAN_COUNT = 3;
    private final Listener mListener;

    public ArtistsViewImpl(final LayoutInflater inflater,
                           final ViewGroup parent,
                           final SharedElementTransition sharedElementTransition,
                           final Listener listener) {
        super(inflater, parent, R.layout.fragment_artists);
        mSharedElementTransition = sharedElementTransition;
        mListener = listener;

        mArtistsList = findViewById(R.id.artists_list);
        final GridLayoutManager layoutManager = new GridLayoutManager(
                getContext(), SPAN_COUNT,
                GridLayoutManager.VERTICAL,
                false);
        layoutManager.setSpanSizeLookup(new CustomSpanSizeLookup());
        mArtistsList.setLayoutManager(layoutManager);

        mHeaderAdapter = HeaderAdapter.newInstance();
        mArtistsAdapter = ArtistsAdapter.newInstance(listener::onArtistClicked);
        mLoaderAdapter = LoaderAdapter.newInstance();

        final ConcatAdapter adapter = new ConcatAdapter(mHeaderAdapter, mArtistsAdapter, mLoaderAdapter);
        mArtistsList.setAdapter(adapter);

        mArtistsList.addItemDecoration(new SpacingItemDecoration());
    }

    @Override
    public void displayGenre(Genre genre) {
        final ArrayList<ArtistsListItem.Header> list = new ArrayList<>();
        list.add(new ArtistsListItem.Header(genre));
        mHeaderAdapter.submitList(list);
    }

    @Override
    public void startSharedElementTransition(Genre genre) {
        ViewUtils.getNotifiedWhenViewIsAttached(mArtistsList, 0,
                v -> mSharedElementTransition.transition(v, mSharedElements));
    }

    @Override
    public void startSharedElementReturnTransition(Artist artist) {
        final int index = ViewUtils.indexOfItem(mArtistsAdapter, artist,
                (f, s) -> f.id() == s.id());
        ViewUtils.getNotifiedWhenViewIsAttached(mArtistsList, index + 1,
                v -> mSharedElementTransition.transition(v,
                        new String[]{"artist-image", "artist-name"}));
    }

    @Override
    public void enqueueSharedElementTransition(Artist artist) {
        final int index = ViewUtils.indexOfItem(mArtistsAdapter, artist,
                (f, s) -> f.id() == s.id());
        mSharedElementTransition.enqueue(mArtistsList, index + 1,
                new String[]{"artist-image", "artist-name"},
                mListener::onSharedElementTransitionEnqueued);
    }

    @Override
    public void enqueueSharedElementReturnTransition() {
        mSharedElementTransition.enqueue(mArtistsList, 0, mSharedElements,
                mListener::onSharedElementReturnTransitionEnqueued);
    }

    @Override
    public void displayArtists(List<Artist> artists) {
        mArtistsAdapter.submitList(artists);
    }

    @Override
    public void displayLoader() {
        mLoaderAdapter.show();
    }

    @Override
    public void hideLoader() {
        mLoaderAdapter.hide();
    }

    private class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            final var lastIndex = Objects.requireNonNull(mArtistsList.getAdapter(),
                            "Recyclerview is not assigned an adapter")
                    .getItemCount() - 1;
            if (position == 0 || position == lastIndex)
                return SPAN_COUNT;
            else
                return 1;
        }
    }

    private static final class SpacingItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect,
                                   @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {

            final var pos = parent.getChildAdapterPosition(view);

            if (pos == 0) {
                return;
            }

            final int spacing = DimensionUtils.getDp(8);

            int left;
            if (pos % SPAN_COUNT != 1) {
                left = 0;
            } else {
                left = spacing;
            }

            int right;
            if (pos % SPAN_COUNT != 0) {
                right = 0;
            } else {
                right = spacing;
            }

            outRect.set(
                    left,
                    spacing,
                    right,
                    spacing
            );
        }
    }
}
