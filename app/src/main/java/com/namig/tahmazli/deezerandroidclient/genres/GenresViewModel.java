package com.namig.tahmazli.deezerandroidclient.genres;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.genres.presenter.GenresPresenter;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.main.Navigator;
import com.namig.tahmazli.deezerandroidclient.utils.presenter.Presenter;
import com.namig.tahmazli.deezerandroidclient.genres.view.GenresView;
import com.namig.tahmazli.deezerandroidclient.interactors.FetchGenresAndCacheThemUseCase;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public class GenresViewModel extends ViewModel implements GenresView.Listener {
    private final FetchGenresAndCacheThemUseCase mFetchGenresAndCacheThemUseCase;

    private final GenresPresenter presenter;
    private final Navigator mNavigator;

    @AssistedInject
    GenresViewModel(@Assisted final SavedStateHandle handle,
                    final FetchGenresAndCacheThemUseCase fetchGenres,
                    final Navigator navigator) {
        this.mFetchGenresAndCacheThemUseCase = fetchGenres;
        this.mNavigator = navigator;

        presenter = new GenresPresenter(handle);

        mFetchGenresAndCacheThemUseCase.addListener(presenter);

        loadGenres();
    }

    @Override
    protected void onCleared() {
        mFetchGenresAndCacheThemUseCase.removeListener(presenter);
        presenter.saveState();
        super.onCleared();
    }

    void loadGenres() {
        mFetchGenresAndCacheThemUseCase.executeAsync();
    }

    Presenter<GenresView, ?> getPresenter() {
        return presenter;
    }

    @Override
    public void onGenreClicked(Genre genre) {
        mNavigator.navigateToArtists(genre);
    }

    @AssistedFactory
    public interface Factory extends ViewModelFactory<GenresViewModel> {
    }
}
