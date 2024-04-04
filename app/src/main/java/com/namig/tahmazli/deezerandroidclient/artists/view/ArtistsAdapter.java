package com.namig.tahmazli.deezerandroidclient.artists.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.interactors.Artist;
import com.namig.tahmazli.deezerandroidclient.view.ArtistImageView;

import java.util.Objects;
import java.util.concurrent.Executors;

final class ArtistsAdapter extends ListAdapter<Artist, ArtistsAdapter.ArtistsViewHolder> {

    static ArtistsAdapter newInstance(final ItemClickListener itemClickListener) {
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
                .build(),
                itemClickListener);
    }

    private final ItemClickListener mItemClickListener;

    private ArtistsAdapter(@NonNull AsyncDifferConfig<Artist> config,
                           final ItemClickListener itemClickListener) {
        super(config);
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ArtistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_artist_list_item, parent, false),
                mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ArtistsViewHolder extends RecyclerView.ViewHolder {

        private final ArtistImageView mArtistImage;
        private final TextView mArtistName;
        private Artist mArtist;
        private final View.OnClickListener mOnClickListener;

        public ArtistsViewHolder(@NonNull View itemView,
                                 final ItemClickListener clickListener) {
            super(itemView);
            mArtistImage = itemView.findViewById(R.id.artist_image);
            mArtistImage.setTransitionName("artist-image");

            mArtistName = itemView.findViewById(R.id.artist_name);
            mOnClickListener = v -> clickListener.onItemClicked(Objects.requireNonNull(mArtist));
        }

        void bind(final Artist artist) {
            mArtist = artist;
            mArtistImage.load(artist.image());

            mArtistName.setText(artist.name());
            itemView.setOnClickListener(mOnClickListener);
        }
    }

    interface ItemClickListener {
        void onItemClicked(final Artist artist);
    }
}
