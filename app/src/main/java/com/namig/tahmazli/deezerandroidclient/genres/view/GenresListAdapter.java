package com.namig.tahmazli.deezerandroidclient.genres.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.interactors.Genre;
import com.namig.tahmazli.deezerandroidclient.utils.GenreImageView;

import java.util.Objects;
import java.util.concurrent.Executors;

class GenresListAdapter extends ListAdapter<Genre, GenresListAdapter.GenresViewHolder> {

    static GenresListAdapter newInstance(final ItemClickListener listener) {
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

        return new GenresListAdapter(differConfig, listener);
    }

    private final ItemClickListener mItemClickListener;

    private GenresListAdapter(final AsyncDifferConfig<Genre> config,
                              final ItemClickListener listener) {
        super(config);
        this.mItemClickListener = listener;
    }

    @NonNull
    @Override
    public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenresViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_genre_list_item,
                                parent,
                                false),
                mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresViewHolder holder, int position) {
        final Genre genre = getItem(position);
        holder.bind(genre);
    }

    static class GenresViewHolder extends RecyclerView.ViewHolder {
        private final GenreImageView genreImage;
        private final TextView genreTitle;
        private final View.OnClickListener mClickListener;
        @Nullable
        private Genre mGenre;

        public GenresViewHolder(@NonNull View itemView,
                                final ItemClickListener listener) {
            super(itemView);
            genreImage = itemView.findViewById(R.id.genre_image);
            genreTitle = itemView.findViewById(R.id.genre_title);

            mClickListener = (v) -> listener.onGenreClicked(Objects.requireNonNull(mGenre));
        }

        void bind(final Genre genre) {
            mGenre = genre;
            genreImage.load(genre.image());
            genreImage.setTransitionName("genre_image");

            genreTitle.setText(genre.name());
            genreTitle.setTransitionName("genre_title");

            itemView.setOnClickListener(mClickListener);
        }
    }

    public interface ItemClickListener {
        void onGenreClicked(final Genre genre);
    }
}
