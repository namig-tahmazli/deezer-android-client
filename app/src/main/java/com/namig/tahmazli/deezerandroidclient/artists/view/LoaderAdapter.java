package com.namig.tahmazli.deezerandroidclient.artists.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namig.tahmazli.deezerandroidclient.R;

final class LoaderAdapter extends RecyclerView.Adapter<LoaderAdapter.LoaderViewHolder> {

    static LoaderAdapter newInstance() {
        return new LoaderAdapter();
    }

    private boolean showLoader = false;

    @NonNull
    @Override
    public LoaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LoaderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_loader, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LoaderViewHolder holder, int position) {
        holder.toggle(showLoader);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    void show() {
        showLoader = true;
        notifyItemChanged(0);
    }

    void hide() {
        showLoader = false;
        notifyItemChanged(0);
    }

    static final class LoaderViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar mProgressBar;

        public LoaderViewHolder(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progress_bar);
        }

        void toggle(final boolean show) {
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
