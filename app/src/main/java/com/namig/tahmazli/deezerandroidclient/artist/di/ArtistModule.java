package com.namig.tahmazli.deezerandroidclient.artist.di;

import com.namig.tahmazli.deezerandroidclient.artist.ArtistViewModel;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class ArtistModule {
    @Binds
    @IntoMap
    @ViewModelKey(ArtistViewModel.class)
    abstract ViewModelFactory<?> bindViewModel(final ArtistViewModel.Factory factory);
}
