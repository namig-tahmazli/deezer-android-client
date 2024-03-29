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

    public ArtistsViewImpl(final LayoutInflater inflater,
                           final ViewGroup parent,
                           final SharedElementTransition sharedElementTransition) {
        super(inflater, parent, R.layout.fragment_artists);
        mSharedElementTransition = sharedElementTransition;

        mArtistsList = findViewById(R.id.artists_list);
        final GridLayoutManager layoutManager = new GridLayoutManager(
                getContext(), SPAN_COUNT,
                GridLayoutManager.VERTICAL,
                false);
        layoutManager.setSpanSizeLookup(new CustomSpanSizeLookup());
        mArtistsList.setLayoutManager(layoutManager);

        mHeaderAdapter = HeaderAdapter.newInstance();
        mArtistsAdapter = ArtistsAdapter.newInstance();
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
    public void enqueueSharedElementReturnTransition() {
        final var viewHolder = mArtistsList.findViewHolderForLayoutPosition(0);
        Objects.requireNonNull(viewHolder);
        mSharedElementTransition.enqueue(viewHolder.itemView, mSharedElements);
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

            final int spacing = DimensionUtils.getDp(16);

            int left;
            if(pos % SPAN_COUNT != 1) {
                left = spacing / 2;
            } else {
                left = spacing;
            }

            int right;
            if (pos % SPAN_COUNT != 0) {
                right = spacing / 2;
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
