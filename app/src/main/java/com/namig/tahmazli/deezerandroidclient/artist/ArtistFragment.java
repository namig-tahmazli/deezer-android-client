package com.namig.tahmazli.deezerandroidclient.artist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namig.tahmazli.deezerandroidclient.artist.view.ArtistView;
import com.namig.tahmazli.deezerandroidclient.artist.view.ArtistViewImpl;
import com.namig.tahmazli.deezerandroidclient.utils.fragment.BaseFragment;
import com.namig.tahmazli.deezerandroidclient.utils.view.SharedElementTransition;

import javax.inject.Inject;

public final class ArtistFragment extends BaseFragment<ArtistView, ArtistViewModel> {
    @Inject
    SharedElementTransition mSharedElementTransition;

    @Override
    protected ArtistView createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return new ArtistViewImpl(inflater, container, mSharedElementTransition, getViewModel());
    }

    @Override
    protected boolean shouldInterceptBackPress() {
        return true;
    }

    @Override
    protected void onBackPressed() {
        getViewModel().onBackPressed();
    }
}
