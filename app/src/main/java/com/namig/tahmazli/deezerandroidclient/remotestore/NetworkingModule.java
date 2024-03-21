package com.namig.tahmazli.deezerandroidclient.remotestore;

import com.google.gson.Gson;
import com.namig.tahmazli.deezerandroidclient.di.AppScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class NetworkingModule {

    @Provides
    @AppScope
    @Named("baseUrl")
    static String provideBaseUrl() {
        return "https://api.deezer.com";
    }

    @Provides
    @AppScope
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @AppScope
    static Gson provideGson() {
        return new Gson();
    }

    @Provides
    @AppScope
    static GsonConverterFactory provideGsonConverterFactory(final Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @AppScope
    static Retrofit provideRetrofit(@Named("baseUrl") final String baseUrl,
                                    final OkHttpClient client,
                                    final GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    @AppScope
    static DeezerApi provideDeezerApi(final Retrofit retrofit) {
        return retrofit.create(DeezerApi.class);
    }

    @Binds
    @AppScope
    abstract RemoteStore bindRemoteStore(final RemoteStoreImpl remoteStore);
}
