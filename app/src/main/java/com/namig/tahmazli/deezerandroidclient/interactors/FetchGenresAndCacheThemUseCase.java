package com.namig.tahmazli.deezerandroidclient.interactors;

import com.namig.tahmazli.deezerandroidclient.di.BackgroundExecutor;
import com.namig.tahmazli.deezerandroidclient.di.MainExecutor;
import com.namig.tahmazli.deezerandroidclient.error.AppError;
import com.namig.tahmazli.deezerandroidclient.interactors.FetchGenresAndCacheThemUseCase.Listener;
import com.namig.tahmazli.deezerandroidclient.remotestore.GenresResponseDto;
import com.namig.tahmazli.deezerandroidclient.remotestore.RemoteStore;
import com.namig.tahmazli.deezerandroidclient.utils.usecase.UseCaseObservable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class FetchGenresAndCacheThemUseCase extends UseCaseObservable<Listener> {

    private final RemoteStore mRemoteStore;
    private final Executor mBackgroundTaskExecutor;

    @Inject
    public FetchGenresAndCacheThemUseCase(@MainExecutor final Executor mainExecutor,
                                          @BackgroundExecutor final Executor threadExecutor,
                                          final RemoteStore remoteStore) {
        super(mainExecutor);
        mRemoteStore = remoteStore;
        mBackgroundTaskExecutor = threadExecutor;
    }

    public void executeAsync() {
        mBackgroundTaskExecutor.execute(() -> {
            try {
                notifyListeners(Listener::showGenresLoadingIndicator);
                notifyListeners(Listener::hideFetchingGenresFailed);

                final GenresResponseDto response = mRemoteStore.fetchGenres();
                final List<Genre> genres =
                        response.data()
                                .stream()
                                .map(dto -> new Genre(
                                        dto.id(),
                                        dto.name(),
                                        dto.pictureMedium()))
                                .collect(Collectors.toList());
                notifyListeners(listener -> listener.displayGenres(genres));
            } catch (final AppError ex) {
                notifyListeners(l -> l.showFetchingGenresFailed(ex));
            } finally {
                notifyListeners(Listener::hideGenresLoadingIndicator);
            }
        });
    }

    public interface Listener {
        void showGenresLoadingIndicator();

        void displayGenres(final List<Genre> genres);

        void hideGenresLoadingIndicator();

        void showFetchingGenresFailed(final AppError error);

        void hideFetchingGenresFailed();
    }
}
