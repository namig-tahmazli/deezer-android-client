package com.namig.tahmazli.deezerandroidclient.artists;

import androidx.lifecycle.SavedStateHandle;

import com.namig.tahmazli.deezerandroidclient.artists.presenter.ArtistsPresenter;
import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsView;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.main.Navigator;
import com.namig.tahmazli.deezerandroidclient.utils.BaseViewModel;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public class ArtistsViewModel extends BaseViewModel<ArtistsView, ArtistsPresenter> {
    private final Navigator mNavigator;
    @AssistedInject
    public ArtistsViewModel(@Assisted final SavedStateHandle handle,
                            final Navigator navigator) {
        super(new ArtistsPresenter(handle));
        mNavigator = navigator;
        mPresenter.displayGenreInfo(handle.get(EXTRA_GENRE));
    }

    @AssistedFactory
    public interface Factory extends ViewModelFactory<ArtistsViewModel> {
    }

    public static final String EXTRA_GENRE = "extra_genre";

    void navigateBack() {
        mPresenter.startSharedElementReturnTransition();
        mNavigator.navigateBack();
    }
}
