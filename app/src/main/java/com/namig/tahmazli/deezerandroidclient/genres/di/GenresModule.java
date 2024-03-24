package com.namig.tahmazli.deezerandroidclient.genres.di;

import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelKey;
import com.namig.tahmazli.deezerandroidclient.genres.GenresViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class GenresModule {

    @Binds
    @IntoMap
    @ViewModelKey(GenresViewModel.class)
    abstract ViewModelFactory<?> bindViewModelFactory(final GenresViewModel.Factory factory);
}
