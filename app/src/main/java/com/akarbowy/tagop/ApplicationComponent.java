package com.akarbowy.tagop;

import com.akarbowy.tagop.flux.Flux;
import com.akarbowy.tagop.network.NetworkModule;
import com.akarbowy.tagop.ui.posts.PostsActivity;
import com.akarbowy.tagop.ui.search.MainSearchActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(MainSearchActivity mainSearchActivity);
    void inject(PostsActivity postsActivityActivity);

    final class Initializer {
        public static ApplicationComponent init(Flux flux, TagopApplication application) {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(flux, application))
                    .networkModule(new NetworkModule())
                    .build();
        }
    }
}
