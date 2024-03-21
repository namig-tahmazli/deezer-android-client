package com.namig.tahmazli.deezerandroidclient;

import com.namig.tahmazli.deezerandroidclient.di.BackgroundExecutor;
import com.namig.tahmazli.deezerandroidclient.di.MainExecutor;
import com.namig.tahmazli.deezerandroidclient.interactors.FetchGenresAndCacheThemUseCase;
import com.namig.tahmazli.deezerandroidclient.remotestore.RemoteStore;

import org.mockito.Mockito;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class TestModule {

    @Provides
    @TestScope
    @MainExecutor
    static Executor provideMainExecutor() {
        return Runnable::run;
    }

    @Provides
    @TestScope
    @BackgroundExecutor
    static Executor provideBackgroundExecutor() {
        return Runnable::run;
    }

    @Provides
    @TestScope
    static RemoteStore provideRemoteStore() {
        return Mockito.mock();
    }

    @Provides
    @TestScope
    static FetchGenresAndCacheThemUseCase.Listener provideListener() {
        return Mockito.mock();
    }
}
