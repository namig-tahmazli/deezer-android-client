package com.namig.tahmazli.deezerandroidclient.error;

import java.io.IOException;

public sealed class AppError extends Throwable {
    protected AppError(final String message) {
        super(message);
    }

    protected AppError(final Throwable cause) {
        super(cause);
    }

    public static final class NetworkError extends AppError {
        final int statusCode;

        public NetworkError(final int statusCode,
                            final String message) {
            super(message);
            this.statusCode = statusCode;
        }

        public NetworkError(final IOException cause) {
            super(cause);
            statusCode = -1;
        }
    }
}
