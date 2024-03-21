package com.namig.tahmazli.deezerandroidclient.genres;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.genres.presenter.GenresPresenter;
import com.namig.tahmazli.deezerandroidclient.utils.presenter.Presenter;
import com.namig.tahmazli.deezerandroidclient.genres.view.GenresView;
import com.namig.tahmazli.deezerandroidclient.interactors.FetchGenresAndCacheThemUseCase;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public class GenresViewModel extends ViewModel {
    private final FetchGenresAndCacheThemUseCase mFetchGenresAndCacheThemUseCase;

    private final GenresPresenter presenter;

    @AssistedInject
    GenresViewModel(@Assisted final SavedStateHandle handle,
                    final FetchGenresAndCacheThemUseCase fetchGenres) {
        this.mFetchGenresAndCacheThemUseCase = fetchGenres;

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

    @AssistedFactory
    public interface Factory extends ViewModelFactory<GenresViewModel> {
    }
}
