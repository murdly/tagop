package com.akarbowy.tagop;

import com.akarbowy.tagop.data.database.PostsRepository;
import com.akarbowy.tagop.data.database.RepositoryModule;
import com.akarbowy.tagop.data.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        RepositoryModule.class})
public interface ApplicationComponent {

    PostsRepository getPostsRepository();

    final class Initializer {
        public static ApplicationComponent init(TagopApplication application) {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .networkModule(new NetworkModule())
                    .repositoryModule(new RepositoryModule())
                    .build();
        }
    }
}
