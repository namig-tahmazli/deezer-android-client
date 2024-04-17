package com.namig.tahmazli.deezerandroidclient.artist.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.utils.view.BaseView;
import com.namig.tahmazli.deezerandroidclient.utils.view.SharedElementTransition;
import com.namig.tahmazli.deezerandroidclient.utils.view.ViewUtils;

public final class ArtistViewImpl extends BaseView implements ArtistView {
    private final HeaderAdapter mHeaderAdapter;
    private static final String[] mSharedTransitions = {"artist-image", "artist-name"};
    private final SharedElementTransition mSharedElementTransition;
    private final RecyclerView mArtistDataList;
    private final Listener mListener;

    public ArtistViewImpl(LayoutInflater inflater,
                          ViewGroup parent,
                          final SharedElementTransition sharedElementTransition,
                          final Listener listener) {
        super(inflater, parent, R.layout.fragment_artist);
        mSharedElementTransition = sharedElementTransition;
        mListener = listener;

        mArtistDataList = findViewById(R.id.artist_data);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mArtistDataList.setLayoutManager(layoutManager);

        mHeaderAdapter = HeaderAdapter.newInstance();

        final ConcatAdapter adapter = new ConcatAdapter(mHeaderAdapter);
        mArtistDataList.setAdapter(adapter);
    }

    @Override
    public void showArtistImage(String artistImage) {
        mHeaderAdapter.setArtistImage(artistImage);
    }

    @Override
    public void showArtistName(String artistName) {
        mHeaderAdapter.setArtistName(artistName);
    }

    @Override
    public void showGenreImage(String genreImage) {
        mHeaderAdapter.setGenreImage(genreImage);
    }

    @Override
    public void startSharedElementTransition() {
        ViewUtils.getNotifiedWhenViewIsAttached(mArtistDataList, 0,
                v -> mSharedElementTransition.transition(v, mSharedTransitions));
    }

    @Override
    public void enqueueSharedElementReturnTransition() {
        mSharedElementTransition.enqueue(mArtistDataList, 0,
                mSharedTransitions, mListener::onSharedElementReturnTransitionEnqueued);
    }
}
