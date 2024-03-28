package com.namig.tahmazli.deezerandroidclient.artists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsView;
import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsViewImpl;
import com.namig.tahmazli.deezerandroidclient.utils.SharedElementTransition;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher()
                .addCallback(this, new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        getViewModel().navigateBack();
                    }
                });
    }
}
