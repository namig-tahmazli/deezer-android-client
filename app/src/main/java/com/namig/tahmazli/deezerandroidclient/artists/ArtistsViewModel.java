package com.namig.tahmazli.deezerandroidclient.artists;

import androidx.lifecycle.SavedStateHandle;

import com.namig.tahmazli.deezerandroidclient.artists.presenter.ArtistsPresenter;
import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsView;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.interactors.FetchArtistsAndDisplayThemUseCase;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.main.Navigator;
import com.namig.tahmazli.deezerandroidclient.utils.BaseViewModel;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public class ArtistsViewModel extends BaseViewModel<ArtistsView, ArtistsPresenter>
        implements ArtistsView.Listener {
    private final Navigator mNavigator;
    private final FetchArtistsAndDisplayThemUseCase mFetchArtistsAndDisplayThemUseCase;
    private final Genre mSelectedGenre;

    @AssistedInject
    public ArtistsViewModel(@Assisted final SavedStateHandle handle,
                            final FetchArtistsAndDisplayThemUseCase useCase,
                            final Navigator navigator) {
        super(new ArtistsPresenter(handle));
        mFetchArtistsAndDisplayThemUseCase = useCase;
        mNavigator = navigator;
        mSelectedGenre = handle.get(EXTRA_GENRE);
        mPresenter.displayGenreInfo(mSelectedGenre);
        mFetchArtistsAndDisplayThemUseCase.addListener(mPresenter);

        loadArtists();
    }

    @Override
    protected void onCleared() {
        mFetchArtistsAndDisplayThemUseCase.removeListener(mPresenter);
        super.onCleared();
    }

    @Override
    public void onSharedElementTransitionEnqueued() {
        mNavigator.navigateBack();
    }

    @AssistedFactory
    public interface Factory extends ViewModelFactory<ArtistsViewModel> {
    }

    public static final String EXTRA_GENRE = "extra_genre";

    void navigateBack() {
        mPresenter.startSharedElementReturnTransition();
    }

    void loadArtists() {
        mFetchArtistsAndDisplayThemUseCase.executeAsync(mSelectedGenre);
    }
}
