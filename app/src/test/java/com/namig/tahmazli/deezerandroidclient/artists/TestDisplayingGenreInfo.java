package com.namig.tahmazli.deezerandroidclient.artists;

import androidx.lifecycle.SavedStateHandle;

import com.namig.tahmazli.deezerandroidclient.DaggerTestComponent;
import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsView;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;

public class TestDisplayingGenreInfo {

    @Inject
    ArtistsViewModel.Factory mFactory;

    private ArtistsViewModel mViewModel;

    private final SavedStateHandle mHandle = new SavedStateHandle();

    @Mock
    ArtistsView mView;

    private AutoCloseable mocks;

    @Before
    public void setUp() {
        DaggerTestComponent.factory().create().inject(this);
        mocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void clear() throws Exception {
        mocks.close();
    }

    @Test
    public void display_Genre_Image() {
        final Genre genre = new Genre(
                1,
                "name",
                "image");
        mHandle.set(ArtistsViewModel.EXTRA_GENRE, genre);

        mViewModel = mFactory.create(mHandle);

        mViewModel.getPresenter().attachView(mView);

        Mockito.verify(mView).displayGenre(genre);
    }
}
