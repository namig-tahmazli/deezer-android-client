package com.namig.tahmazli.deezerandroidclient.artist.di;

import com.namig.tahmazli.deezerandroidclient.artist.ArtistFragment;
import com.namig.tahmazli.deezerandroidclient.di.android.FragmentScope;
import com.namig.tahmazli.deezerandroidclient.di.android.Injector;

import dagger.Subcomponent;

@FragmentScope(ArtistFragment.class)
@Subcomponent(modules = {ArtistModule.class})
public interface ArtistSubcomponent extends Injector<ArtistFragment> {
    @Subcomponent.Factory
    interface Factory extends Injector.Factory<ArtistFragment> {
    }
}
