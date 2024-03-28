package com.namig.tahmazli.deezerandroidclient.artists;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsView;
import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsViewImpl;
import com.namig.tahmazli.deezerandroidclient.utils.view.SharedElementTransition;
import com.namig.tahmazli.deezerandroidclient.utils.fragment.BaseFragment;

import javax.inject.Inject;

public class ArtistsFragment extends BaseFragment<ArtistsView, ArtistsViewModel> {
    @Inject
    SharedElementTransition mSharedElementTransition;

    @Override
    protected ArtistsView createView(@NonNull LayoutInflater inflater,
                                     @Nullable ViewGroup container) {
        return new ArtistsViewImpl(inflater, container, mSharedElementTransition);
    }

    @Override
    protected boolean shouldInterceptBackPress() {
        return true;
    }

    @Override
    protected void onBackPressed() {
        getViewModel().navigateBack();
    }
}
