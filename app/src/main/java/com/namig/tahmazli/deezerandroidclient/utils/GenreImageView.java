package com.namig.tahmazli.deezerandroidclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.namig.tahmazli.deezerandroidclient.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class GenreImageView extends ConstraintLayout implements RequestListener<Drawable> {
    private final ImageView mGenreImage;
    private final View mMask;
    private final Map<String, Bitmap> mDownloadedResource = new HashMap<>();
    public static final String TAG = GenreImageView.class.getSimpleName();

    public GenreImageView(@NonNull Context context) {
        this(context, null);
    }
    public GenreImageView(@NonNull Context context,
                          @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GenreImageView(@NonNull Context context,
                          @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_genre_image, this);

        mGenreImage = findViewById(R.id.image);
        mMask = findViewById(R.id.mask);
    }

    public void load(final String url) {
        if (mDownloadedResource.containsKey(url)) {
            final Bitmap bitmap = mDownloadedResource.get(url);
            useDownloadedBitmap(bitmap);
        } else {
            Glide.with(mGenreImage)
                    .load(url)
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
            mGenreImage.setImageBitmap(bitmap);
            mMask.setBackgroundColor(dominantColor);
            mMask.setAlpha(0.5f);
        });
    }

    @NonNull
    private Executor getMainExecutor() {
        return ContextCompat.getMainExecutor(getContext());
    }
}
