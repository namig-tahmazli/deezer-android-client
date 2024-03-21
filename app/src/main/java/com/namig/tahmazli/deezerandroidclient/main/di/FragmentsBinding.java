package com.namig.tahmazli.deezerandroidclient.main.di;

import com.namig.tahmazli.deezerandroidclient.di.android.FragmentKey;
import com.namig.tahmazli.deezerandroidclient.di.android.Injector;
import com.namig.tahmazli.deezerandroidclient.genres.GenresFragment;
import com.namig.tahmazli.deezerandroidclient.genres.di.GenresSubcomponent;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {GenresSubcomponent.class})
public abstract class FragmentsBinding {

    @Binds
    @IntoMap
    @FragmentKey(GenresFragment.class)
    abstract Injector.Factory<?> bindGenresFragment(final GenresSubcomponent.Factory factory);
}
