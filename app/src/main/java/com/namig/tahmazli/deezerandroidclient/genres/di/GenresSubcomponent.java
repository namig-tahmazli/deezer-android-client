package com.namig.tahmazli.deezerandroidclient.genres.di;

import com.namig.tahmazli.deezerandroidclient.di.android.FragmentScope;
import com.namig.tahmazli.deezerandroidclient.di.android.Injector;
import com.namig.tahmazli.deezerandroidclient.genres.GenresFragment;

import dagger.Subcomponent;

@FragmentScope(GenresFragment.class)
@Subcomponent(modules = {GenresModule.class})
public interface GenresSubcomponent extends Injector<GenresFragment> {
    @Subcomponent.Factory
    interface Factory extends Injector.Factory<GenresFragment> {
    }
}
