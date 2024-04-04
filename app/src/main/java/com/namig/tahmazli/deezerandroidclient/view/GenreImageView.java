package com.namig.tahmazli.deezerandroidclient.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.request.RequestOptions;
import com.namig.tahmazli.deezerandroidclient.R;

public class GenreImageView extends ConstraintLayout
        implements NetworkImageView.Listener, ImageLoader {
    private final NetworkImageView mNetworkImage;
    private final View mMask;

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

        mNetworkImage = findViewById(R.id.image);
        mNetworkImage.setRequestOptions(RequestOptions.centerCropTransform());
        mNetworkImage.addListener(this);

        mMask = findViewById(R.id.mask);
    }

    @Override
    public void onImageLoaded(int dominantColor, Bitmap loadedImage) {
        mMask.setBackgroundColor(dominantColor);
        mMask.setAlpha(0.5f);
    }

    @Override
    public void load(String url) {
        mNetworkImage.load(url);
    }
}
