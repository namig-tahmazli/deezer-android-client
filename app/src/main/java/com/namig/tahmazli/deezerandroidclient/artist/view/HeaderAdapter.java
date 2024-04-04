package com.namig.tahmazli.deezerandroidclient.artist.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.view.ArtistImageView;
import com.namig.tahmazli.deezerandroidclient.view.GenreImageView;

final class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder> {

    static HeaderAdapter newInstance() {
        return new HeaderAdapter();
    }

    private HeaderAdapter.Item mHeaderData;

    private HeaderAdapter() {
        mHeaderData = new HeaderAdapter.Item(null, null, null);
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_artist_data_list_header, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, int position) {
        holder.bind(mHeaderData);
    }

    void setGenreImage(final String genreImage) {
        mHeaderData = new HeaderAdapter.Item(
                genreImage,
                mHeaderData.artistImage(),
                mHeaderData.artistName()
        );
        notifyItemChanged(0);
    }

    void setArtistImage(final String artistImage) {
        mHeaderData = new HeaderAdapter.Item(
                mHeaderData.genreImage(),
                artistImage,
                mHeaderData.artistName()
        );
        notifyItemChanged(0);
    }

    void setArtistName(final String artistName) {
        mHeaderData = new HeaderAdapter.Item(
                mHeaderData.genreImage(),
                mHeaderData.artistImage(),
                artistName
        );
        notifyItemChanged(0);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static final class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final GenreImageView mGenreImage;
        private final ArtistImageView mArtistImage;
        private final TextView mArtistName;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            mGenreImage = itemView.findViewById(R.id.genre_image);
            mArtistImage = itemView.findViewById(R.id.artist_image);
            mArtistImage.setTransitionName("artist-image");
            mArtistName = itemView.findViewById(R.id.artist_name);
        }

        void bind(final Item item) {
            if (item.genreImage != null)
                mGenreImage.load(item.genreImage);

            if (item.artistImage != null)
                mArtistImage.load(item.artistImage);

            mArtistName.setText(item.artistName);
        }
    }

    record Item(
            @Nullable String genreImage,
            @Nullable String artistImage,
            @Nullable String artistName
    ) {
    }
}
