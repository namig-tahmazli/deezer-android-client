package com.namig.tahmazli.deezerandroidclient.artists.di;

import com.namig.tahmazli.deezerandroidclient.artists.ArtistsFragment;
import com.namig.tahmazli.deezerandroidclient.di.android.FragmentScope;
import com.namig.tahmazli.deezerandroidclient.di.android.Injector;

import dagger.Subcomponent;

@FragmentScope(ArtistsFragment.class)
@Subcomponent(modules = {ArtistsModule.class})
public interface ArtistsSubcomponent extends Injector<ArtistsFragment> {
    @Subcomponent.Factory
    interface Factory extends Injector.Factory<ArtistsFragment> {
    }
}
