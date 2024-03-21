package com.namig.tahmazli.deezerandroidclient.genres;

import com.namig.tahmazli.deezerandroidclient.DaggerTestComponent;
import com.namig.tahmazli.deezerandroidclient.error.AppError;
import com.namig.tahmazli.deezerandroidclient.interactors.FetchGenresAndCacheThemUseCase;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.remotestore.GenresResponseDto;
import com.namig.tahmazli.deezerandroidclient.remotestore.RemoteStore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class TestFetchingGenresAndDisplayingThem {

    @Inject
    FetchGenresAndCacheThemUseCase useCase;

    @Inject
    RemoteStore mRemoteStore;

    @Inject
    FetchGenresAndCacheThemUseCase.Listener mListener;

    private List<Genre> mExpectedGenres;

    @Before
    public void setUp() throws AppError.NetworkError {
        DaggerTestComponent.factory()
                .create()
                .inject(this);

        useCase.addListener(mListener);

        mockSuccessCase();
    }

    @After
    public void tearDown() {
        useCase.removeListener(mListener);
    }

    @Test
    public void show_Progress_Indicator() {
        useCase.executeAsync();
        Mockito.verify(mListener).showGenresLoadingIndicator();
    }

    @Test
    public void hide_Failure_Indicator() {
        useCase.executeAsync();
        Mockito.verify(mListener).hideFetchingGenresFailed();
    }

    @Test
    public void display_Genres() {
        useCase.executeAsync();
        Mockito.verify(mListener).displayGenres(mExpectedGenres);
    }

    private void mockSuccessCase() throws AppError.NetworkError {
        final int[] ids = new int[]{1, 2, 3, 4};
        mExpectedGenres =
                Arrays.stream(ids)
                        .mapToObj(id -> new Genre(id, "", ""))
                        .toList();

        Mockito.when(mRemoteStore.fetchGenres())
                .thenReturn(new GenresResponseDto(
                        mExpectedGenres.stream()
                                .map(g -> new GenresResponseDto.GenreDto(
                                        g.id(),
                                        g.name(),
                                        g.image(),
                                        g.image(),
                                        g.image(),
                                        g.image()))
                                .toList()));
    }
}
