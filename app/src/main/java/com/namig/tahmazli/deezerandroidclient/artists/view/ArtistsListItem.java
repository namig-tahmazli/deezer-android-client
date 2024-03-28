package com.namig.tahmazli.deezerandroidclient.artists.view;

import com.namig.tahmazli.deezerandroidclient.interactors.Genre;

public sealed interface ArtistsListItem {
    final class Header implements ArtistsListItem {
        final Genre genre;

        public Header(Genre genre) {
            this.genre = genre;
        }
    }
}
