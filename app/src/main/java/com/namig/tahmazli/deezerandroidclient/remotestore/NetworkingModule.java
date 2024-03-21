package com.namig.tahmazli.deezerandroidclient.remotestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.namig.tahmazli.deezerandroidclient.di.AppScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    static Interceptor provideLoggingInterceptor() {
        var interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            public static final String TAG = HttpLoggingInterceptor.class.getSimpleName();

            @Override
            public void log(@NonNull String s) {
                Log.v(TAG, s);
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @AppScope
    static OkHttpClient provideOkHttpClient(final Interceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
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
