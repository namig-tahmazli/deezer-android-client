package com.namig.tahmazli.deezerandroidclient.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

public class NetworkImageView extends AppCompatImageView
        implements RequestListener<Drawable>, ImageLoader {
    private final Map<String, Bitmap> mDownloadedResource = new HashMap<>();
    public static final String TAG = GenreImageView.class.getSimpleName();
    private final Set<Listener> mListeners = new HashSet<>();
    private RequestOptions mRequestOptions;

    public NetworkImageView(@NonNull Context context) {
        this(context, null);
    }

    public NetworkImageView(@NonNull Context context,
                            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetworkImageView(@NonNull Context context,
                            @Nullable AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRequestOptions(final RequestOptions requestOptions) {
        mRequestOptions = requestOptions;
    }

    public void addListener(final Listener listener) {
        mListeners.add(listener);
    }

    public void removeListener(final Listener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void load(final String url) {
        if (mDownloadedResource.containsKey(url)) {
            final Bitmap bitmap = mDownloadedResource.get(url);
            useDownloadedBitmap(bitmap);
        } else {
            final var glide = Glide.with(this);
            glide.applyDefaultRequestOptions(
                    mRequestOptions != null ? mRequestOptions : RequestOptions.circleCropTransform());
            glide.load(url)
                    .addListener(this)
                    .submit();
        }
    }


    @Override
    public boolean onLoadFailed(@Nullable GlideException e,
                                @Nullable Object model,
                                @NonNull Target<Drawable> target,
                                boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(@NonNull Drawable resource,
                                   @NonNull Object model,
                                   Target<Drawable> target,
                                   @NonNull DataSource dataSource,
                                   boolean isFirstResource) {
        Log.d(TAG, "onResourceReady: image width " + resource.getIntrinsicWidth());
        Log.d(TAG, "onResourceReady: image height " + resource.getIntrinsicHeight());

        final Bitmap bitmap = Bitmap.createBitmap(
                resource.getIntrinsicWidth(),
                resource.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        resource.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        resource.draw(canvas);

        if (model instanceof String m && !mDownloadedResource.containsKey(m)) {
            mDownloadedResource.put(m, bitmap);
        }

        useDownloadedBitmap(bitmap);
        return true;
    }

    private void useDownloadedBitmap(final Bitmap bitmap) {
        Log.d(TAG, "useDownloadedBitmap: Thread " + Thread.currentThread().getName());

        final int dominantColor =
                Palette.from(bitmap).generate()
                        .getMutedColor(Color.BLACK);

        Log.d(TAG, "useDownloadedBitmap: dominant color " + dominantColor);

        getMainExecutor().execute(() -> {
            setImageBitmap(bitmap);

            for (Listener mListener : mListeners) {
                mListener.onImageLoaded(dominantColor, bitmap);
            }
        });
    }

    @NonNull
    private Executor getMainExecutor() {
        return ContextCompat.getMainExecutor(getContext());
    }

    interface Listener {
        void onImageLoaded(@ColorInt final int dominantColor,
                           final Bitmap loadedImage);
    }
}
