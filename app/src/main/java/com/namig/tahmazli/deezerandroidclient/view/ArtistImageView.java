package com.namig.tahmazli.deezerandroidclient.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;
import com.namig.tahmazli.deezerandroidclient.R;

public class ArtistImageView extends ConstraintLayout
        implements NetworkImageView.Listener, ImageLoader {
    private final NetworkImageView mNetworkImageView;
    private final MaterialCardView mCardView;

    public ArtistImageView(@NonNull Context context) {
        this(context, null);
    }

    public ArtistImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArtistImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_artist_image, this);

        mNetworkImageView = findViewById(R.id.image);
        mNetworkImageView.setRequestOptions(RequestOptions.circleCropTransform());
        mNetworkImageView.addListener(this);

        mCardView = findViewById(R.id.background_card);
    }

    @Override
    public void load(String url) {
        mNetworkImageView.load(url);
    }

    @Override
    public void onImageLoaded(int dominantColor, Bitmap loadedImage) {
        mCardView.setCardBackgroundColor(dominantColor);
    }
}
