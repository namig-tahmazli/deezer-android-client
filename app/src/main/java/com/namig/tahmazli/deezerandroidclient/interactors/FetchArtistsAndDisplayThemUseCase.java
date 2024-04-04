package com.namig.tahmazli.deezerandroidclient.interactors;

import com.namig.tahmazli.deezerandroidclient.di.BackgroundExecutor;
import com.namig.tahmazli.deezerandroidclient.di.MainExecutor;
import com.namig.tahmazli.deezerandroidclient.error.AppError;
import com.namig.tahmazli.deezerandroidclient.remotestore.ArtistsResponseDto;
import com.namig.tahmazli.deezerandroidclient.remotestore.RemoteStore;
import com.namig.tahmazli.deezerandroidclient.utils.usecase.UseCaseObservable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class FetchArtistsAndDisplayThemUseCase extends UseCaseObservable<FetchArtistsAndDisplayThemUseCase.Listener> {

    private final Executor mBackgroundExecutor;
    private final RemoteStore mRemoteStore;

    @Inject
    public FetchArtistsAndDisplayThemUseCase(@MainExecutor final Executor mainExecutor,
                                             @BackgroundExecutor final Executor backgroundExecutor,
                                             final RemoteStore remoteStore) {
        super(mainExecutor);
        mBackgroundExecutor = backgroundExecutor;
        mRemoteStore = remoteStore;
    }

    public void executeAsync(final Genre genre) {
        mBackgroundExecutor.execute(() -> {
            try {
                notifyListeners(Listener::showFetchingArtists);
                notifyListeners(Listener::hideFetchingArtistsFailed);
                final ArtistsResponseDto response = mRemoteStore.fetchArtists(genre);
                final List<Artist> artists = response.data().stream()
                        .map(dto -> new Artist(
                                dto.id(),
                                dto.name(),
                                dto.pictureMedium(),
                                dto.pictureBig()))
                        .collect(Collectors.toList());

                notifyListeners(l -> l.showArtists(artists));
                notifyListeners(Listener::hideFetchingArtists);
            } catch (final AppError.NetworkError error) {
                notifyListeners(Listener::hideFetchingArtists);
                notifyListeners(Listener::showFetchingArtistsFailed);
            }
        });
    }

    public interface Listener {
        void showFetchingArtists();

        void hideFetchingArtists();

        void showFetchingArtistsFailed();

        void hideFetchingArtistsFailed();

        void showArtists(List<Artist> artists);
    }
}
