package com.akarbowy.tagop.dicomponent;

import com.akarbowy.tagop.ApplicationModule;
import com.akarbowy.tagop.presentation.posts.PostsActivity;
import com.akarbowy.tagop.presentation.search.MainSearchActivity;
import com.akarbowy.tagop.TagopApplication;
import com.akarbowy.tagop.flux.Flux;
import com.akarbowy.tagop.network.NetworkModule;

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
