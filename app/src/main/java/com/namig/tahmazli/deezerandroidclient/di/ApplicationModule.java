package com.namig.tahmazli.deezerandroidclient.di;

import androidx.core.content.ContextCompat;

import com.namig.tahmazli.deezerandroidclient.App;
import com.namig.tahmazli.deezerandroidclient.BackgroundThreadExecutor;
import com.namig.tahmazli.deezerandroidclient.remotestore.NetworkingModule;

import java.util.concurrent.Executor;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkingModule.class})
public abstract class ApplicationModule {

    @Provides
    @AppScope
    @MainExecutor
    static Executor provideMainExecutorService(final App app) {
        return ContextCompat.getMainExecutor(app.getApplicationContext());
    }

    @Binds
    @AppScope
    @BackgroundExecutor
    abstract Executor provideBackgroundExecutor(final BackgroundThreadExecutor executor);
}
