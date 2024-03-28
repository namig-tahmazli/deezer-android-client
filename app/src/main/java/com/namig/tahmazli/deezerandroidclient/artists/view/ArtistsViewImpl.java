package com.namig.tahmazli.deezerandroidclient.artists.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.view.BaseView;
import com.namig.tahmazli.deezerandroidclient.utils.view.SharedElementTransition;
import com.namig.tahmazli.deezerandroidclient.utils.view.ViewUtils;

import java.util.ArrayList;
import java.util.Objects;

public class ArtistsViewImpl extends BaseView implements ArtistsView {
    private final HeaderAdapter mHeaderAdapter;
    private final SharedElementTransition mSharedElementTransition;
    private final RecyclerView mArtistsList;
    private final String[] mSharedElements = new String[]{"genre_image", "genre_title"};

    public ArtistsViewImpl(final LayoutInflater inflater,
                           final ViewGroup parent,
                           final SharedElementTransition sharedElementTransition) {
        super(inflater, parent, R.layout.fragment_artists);
        mSharedElementTransition = sharedElementTransition;

        mArtistsList = findViewById(R.id.artists_list);
        final GridLayoutManager layoutManager = new GridLayoutManager(
                getContext(), 2,
                GridLayoutManager.VERTICAL,
                false);
        layoutManager.setSpanSizeLookup(new CustomSpanSizeLookup());
        mArtistsList.setLayoutManager(layoutManager);

        mHeaderAdapter = HeaderAdapter.newInstance();

        final ConcatAdapter adapter = new ConcatAdapter(mHeaderAdapter);
        mArtistsList.setAdapter(adapter);
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

    private static class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            if (position == 0)
                return 2;
            else
                return 1;
        }
    }
}
