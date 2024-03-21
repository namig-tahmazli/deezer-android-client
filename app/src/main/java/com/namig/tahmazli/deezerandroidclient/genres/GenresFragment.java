package com.namig.tahmazli.deezerandroidclient.genres;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.namig.tahmazli.deezerandroidclient.di.android.AndroidInjection;
import com.namig.tahmazli.deezerandroidclient.genres.view.GenresViewImpl;

import javax.inject.Inject;

public class GenresFragment extends Fragment {

    @Inject
    ViewModelProvider mViewModelProvider;

    private GenresViewModel mViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = mViewModelProvider.get(GenresViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final GenresViewImpl view = new GenresViewImpl(inflater, container);
        mViewModel.getPresenter().attachView(view);
        return view.getRoot();
    }

    @Override
    public void onDestroyView() {
        mViewModel.getPresenter().detachView();
        super.onDestroyView();
    }
}
