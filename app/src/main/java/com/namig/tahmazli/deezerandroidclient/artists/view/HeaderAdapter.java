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
import com.namig.tahmazli.deezerandroidclient.artists.view.ArtistsListItem.Header;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.GenreImageView;

import java.util.concurrent.Executors;

class HeaderAdapter extends ListAdapter<Header, HeaderAdapter.HeaderViewHolder> {

    static HeaderAdapter newInstance() {
        final DiffUtil.ItemCallback<Header> itemCallback = new DiffUtil.ItemCallback<>() {
            @Override
            public boolean areItemsTheSame(@NonNull Header oldItem, @NonNull Header newItem) {
                return oldItem.genre.id() == newItem.genre.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Header oldItem, @NonNull Header newItem) {
                return oldItem.genre.equals(newItem.genre);
            }
        };
        return new HeaderAdapter(new AsyncDifferConfig.Builder<>(itemCallback)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build());
    }

    private HeaderAdapter(@NonNull AsyncDifferConfig<Header> config) {
        super(config);
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_artists_header, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, int position) {
        holder.bind(getItem(position).genre);
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final GenreImageView mGenreImage;
        private final TextView mGenreTitle;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            mGenreImage = itemView.findViewById(R.id.genre_image);
            mGenreTitle = itemView.findViewById(R.id.genre_title);
        }

        void bind(final Genre genre) {
            mGenreImage.load(genre.image());
            mGenreTitle.setText(genre.name());

            mGenreImage.setTransitionName("genre_image");
            mGenreTitle.setTransitionName("genre_title");
        }
    }
}
