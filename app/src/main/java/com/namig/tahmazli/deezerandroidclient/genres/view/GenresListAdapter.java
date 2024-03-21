package com.namig.tahmazli.deezerandroidclient.genres.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

class GenresListAdapter extends ListAdapter<Genre, GenresListAdapter.GenresViewHolder> {

    static GenresListAdapter newInstance() {
        final DiffUtil.ItemCallback<Genre> itemCallback = new DiffUtil.ItemCallback<>() {
            @Override
            public boolean areItemsTheSame(@NonNull Genre oldItem, @NonNull Genre newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Genre oldItem, @NonNull Genre newItem) {
                return oldItem.equals(newItem);
            }
        };

        final AsyncDifferConfig<Genre> differConfig = new AsyncDifferConfig.Builder<>(itemCallback)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build();

        return new GenresListAdapter(differConfig);
    }

    private GenresListAdapter(final AsyncDifferConfig<Genre> config) {
        super(config);
    }

    @NonNull
    @Override
    public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenresViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_genre_list_item,
                                parent,
                                false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenresViewHolder holder, int position) {
        final Genre genre = getItem(position);
        holder.bind(genre);
    }

    static class GenresViewHolder extends RecyclerView.ViewHolder implements RequestListener<Drawable> {
        private final AppCompatImageView genreImage;
        private final TextView genreTitle;
        private final View mask;
        public static final String TAG = GenresViewHolder.class.getSimpleName();
        private final Map<String, Bitmap> mDownloadedResource = new HashMap<>();

        public GenresViewHolder(@NonNull View itemView) {
            super(itemView);
            genreImage = itemView.findViewById(R.id.genre_image);
            genreTitle = itemView.findViewById(R.id.genre_title);
            mask = itemView.findViewById(R.id.mask);
        }

        void bind(final Genre genre) {

            if (mDownloadedResource.containsKey(genre.image())) {
                final Bitmap bitmap = mDownloadedResource.get(genre.image());
                useDownloadedBitmap(bitmap);
            } else {
                Glide.with(genreImage)
                        .load(genre.image())
                        .addListener(this)
                        .optionalCenterCrop()
                        .submit();
            }

            genreTitle.setText(genre.name());
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
                                       @NonNull Object model, Target<Drawable> target,
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
            genreImage.setImageBitmap(bitmap);

            final int dominantColor =
                    Palette.from(bitmap).generate()
                            .getMutedColor(Color.BLACK);

            Log.d(TAG, "dominant color " + dominantColor);

            mask.setBackgroundColor(dominantColor);
            mask.setAlpha(0.5f);
        }
    }
}
