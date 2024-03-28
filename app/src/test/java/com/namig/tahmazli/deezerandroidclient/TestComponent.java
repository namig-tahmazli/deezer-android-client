package com.namig.tahmazli.deezerandroidclient;

import com.namig.tahmazli.deezerandroidclient.artists.TestDisplayingGenreInfo;
import com.namig.tahmazli.deezerandroidclient.genres.TestFetchingGenresAndDisplayingThem;

import dagger.Component;

@TestScope
@Component(modules = {TestModule.class})
public interface TestComponent {

    @Component.Factory interface Factory {
        TestComponent create();
    }

    void inject(final TestFetchingGenresAndDisplayingThem test);
    void inject(final TestDisplayingGenreInfo test);
}
