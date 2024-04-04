package com.namig.tahmazli.deezerandroidclient.main.di;

import com.namig.tahmazli.deezerandroidclient.artist.ArtistFragment;
import com.namig.tahmazli.deezerandroidclient.artist.di.ArtistSubcomponent;
import com.namig.tahmazli.deezerandroidclient.artists.ArtistsFragment;
import com.namig.tahmazli.deezerandroidclient.artists.di.ArtistsSubcomponent;
import com.namig.tahmazli.deezerandroidclient.di.android.FragmentKey;
import com.namig.tahmazli.deezerandroidclient.di.android.Injector;
import com.namig.tahmazli.deezerandroidclient.genres.GenresFragment;
import com.namig.tahmazli.deezerandroidclient.genres.di.GenresSubcomponent;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        GenresSubcomponent.class,
        ArtistsSubcomponent.class,
        ArtistSubcomponent.class
})
public abstract class FragmentsBinding {

    @Binds
    @IntoMap
    @FragmentKey(GenresFragment.class)
    abstract Injector.Factory<?> bindGenresFragment(final GenresSubcomponent.Factory factory);

    @Binds
    @IntoMap
    @FragmentKey(ArtistsFragment.class)
    abstract Injector.Factory<?> bindArtistsFragment(final ArtistsSubcomponent.Factory factory);

    @Binds
    @IntoMap
    @FragmentKey(ArtistFragment.class)
    abstract Injector.Factory<?> bindArtistFragment(final ArtistSubcomponent.Factory factory);
}
