package com.namig.tahmazli.deezerandroidclient.genres.di;

import androidx.lifecycle.ViewModelProvider;

import com.namig.tahmazli.deezerandroidclient.di.android.FragmentScope;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelKey;
import com.namig.tahmazli.deezerandroidclient.genres.GenresFragment;
import com.namig.tahmazli.deezerandroidclient.genres.GenresViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class GenresModule {

    @Binds
    @IntoMap
    @ViewModelKey(GenresViewModel.class)
    abstract ViewModelFactory<?> bindViewModelFactory(final GenresViewModel.Factory factory);

    @Provides
    @FragmentScope(GenresFragment.class)
    static ViewModelProvider provideViewModel(
            final GenresFragment fragment,
            final ViewModelFactory.Injector injector) {
        return new ViewModelProvider(fragment, injector);
    }
}
