package com.namig.tahmazli.deezerandroidclient.artists.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.interactors.Artist;

import java.util.concurrent.Executors;

final class ArtistsAdapter extends ListAdapter<Artist, ArtistsAdapter.ArtistsViewHolder> {

    static ArtistsAdapter newInstance() {
        final DiffUtil.ItemCallback<Artist> itemCallback = new DiffUtil.ItemCallback<>() {
            @Override
            public boolean areItemsTheSame(@NonNull Artist oldItem, @NonNull Artist newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Artist oldItem, @NonNull Artist newItem) {
                return oldItem.equals(newItem);
            }
        };

        return new ArtistsAdapter(new AsyncDifferConfig.Builder<>(itemCallback)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build());
    }

    private ArtistsAdapter(@NonNull AsyncDifferConfig<Artist> config) {
        super(config);
    }

    @NonNull
    @Override
    public ArtistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_artist_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ArtistsViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView mArtistImage;
        private final TextView mArtistName;

        public ArtistsViewHolder(@NonNull View itemView) {
            super(itemView);
            mArtistImage = itemView.findViewById(R.id.artist_image);
            mArtistName = itemView.findViewById(R.id.artist_name);
        }

        void bind(final Artist artist) {
            Glide.with(mArtistImage)
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(artist.image())
                    .into(mArtistImage);

            mArtistName.setText(artist.name());
        }
    }
}
