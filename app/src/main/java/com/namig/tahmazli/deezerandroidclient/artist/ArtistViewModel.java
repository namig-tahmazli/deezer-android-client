package com.namig.tahmazli.deezerandroidclient.artist;

import androidx.lifecycle.SavedStateHandle;

import com.namig.tahmazli.deezerandroidclient.artist.presenter.ArtistPresenter;
import com.namig.tahmazli.deezerandroidclient.artist.view.ArtistView;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.interactors.Artist;
import com.namig.tahmazli.deezerandroidclient.main.Navigator;
import com.namig.tahmazli.deezerandroidclient.utils.BaseViewModel;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public final class ArtistViewModel extends BaseViewModel<ArtistView, ArtistPresenter>
        implements ArtistView.Listener {
    public static final String EXTRA_ARTIST = "extra_artist";
    public static final String EXTRA_GENRE_IMAGE = "genre_image";
    public final Artist mSelectedArtist;
    private final Navigator mNavigator;

    @AssistedInject
    public ArtistViewModel(@Assisted final SavedStateHandle handle,
                           final Navigator navigator) {
        super(new ArtistPresenter(handle));
        mNavigator = navigator;
        mSelectedArtist = handle.get(EXTRA_ARTIST);
        mPresenter.displayArtistInfo(mSelectedArtist);
        mPresenter.displayGenreImage(handle.get(EXTRA_GENRE_IMAGE));
    }

    void onBackPressed() {
        mPresenter.startSharedElementReturnTransition();
    }

    @Override
    public void onSharedElementReturnTransitionEnqueued() {
        mNavigator.navigateBack();
    }

    @AssistedFactory
    public interface Factory extends ViewModelFactory<ArtistViewModel> {
    }
}
