package com.namig.tahmazli.deezerandroidclient.remotestore;

import com.namig.tahmazli.deezerandroidclient.error.AppError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

class NetworkUtils {

    static <T> T executeCall(final Call<T> call) throws AppError.NetworkError {
        try {
            final Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }

            final int statusCode = response.code();
            try (final ResponseBody responseBody = response.errorBody()) {
                if (responseBody != null) {
                    try (final InputStream stream = responseBody.byteStream()) {
                        final String errorBody = new BufferedReader(new InputStreamReader(stream))
                                .lines()
                                .collect(Collectors.joining("\n"));

                        throw new AppError.NetworkError(
                                statusCode,
                                errorBody
                        );
                    }
                }
            }

            throw new AppError.NetworkError(statusCode, "");
        } catch (IOException e) {
            throw new AppError.NetworkError(e);
        }
    }
}
