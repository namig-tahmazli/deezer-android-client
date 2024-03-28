package com.namig.tahmazli.deezerandroidclient.genres;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namig.tahmazli.deezerandroidclient.genres.view.GenresView;
import com.namig.tahmazli.deezerandroidclient.genres.view.GenresViewImpl;
import com.namig.tahmazli.deezerandroidclient.utils.SharedElementTransition;
import com.namig.tahmazli.deezerandroidclient.utils.fragment.BaseFragment;

import javax.inject.Inject;

public class GenresFragment extends BaseFragment<GenresView, GenresViewModel> {

    @Inject
    SharedElementTransition mSharedElementTransition;

    @Override
    protected GenresView createView(@NonNull LayoutInflater inflater,
                                    @Nullable ViewGroup container) {
        return new GenresViewImpl(inflater, container, getViewModel(),
                mSharedElementTransition);
    }
}
