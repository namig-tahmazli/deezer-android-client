package com.namig.tahmazli.deezerandroidclient.genres;

import androidx.lifecycle.SavedStateHandle;

import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.genres.presenter.GenresPresenter;
import com.namig.tahmazli.deezerandroidclient.genres.view.GenresView;
import com.namig.tahmazli.deezerandroidclient.interactors.FetchGenresAndCacheThemUseCase;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.main.Navigator;
import com.namig.tahmazli.deezerandroidclient.utils.BaseViewModel;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public class GenresViewModel
        extends BaseViewModel<GenresView, GenresPresenter>
        implements GenresView.Listener {
    private final FetchGenresAndCacheThemUseCase mFetchGenresAndCacheThemUseCase;
    private final Navigator mNavigator;

    @AssistedInject
    GenresViewModel(@Assisted final SavedStateHandle handle,
                    final FetchGenresAndCacheThemUseCase fetchGenres,
                    final Navigator navigator) {
        super(new GenresPresenter(handle));
        this.mFetchGenresAndCacheThemUseCase = fetchGenres;
        this.mNavigator = navigator;
        mFetchGenresAndCacheThemUseCase.addListener(mPresenter);

        loadGenres();
    }

    @Override
    protected void onCleared() {
        mFetchGenresAndCacheThemUseCase.removeListener(mPresenter);
        super.onCleared();
    }

    void loadGenres() {
        mFetchGenresAndCacheThemUseCase.executeAsync();
    }

    @Override
    public void onGenreClicked(Genre genre) {
        mNavigator.navigateToArtists(genre);
    }

    @AssistedFactory
    public interface Factory extends ViewModelFactory<GenresViewModel> {
    }
}
