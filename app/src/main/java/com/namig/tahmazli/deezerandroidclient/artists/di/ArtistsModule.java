package com.namig.tahmazli.deezerandroidclient.artists.di;

import com.namig.tahmazli.deezerandroidclient.artists.ArtistsViewModel;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ArtistsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArtistsViewModel.class)
    abstract ViewModelFactory<?> bindViewModel(final ArtistsViewModel.Factory factory);
}
