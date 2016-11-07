package com.akarbowy.tagop.data.database;

import android.content.Context;

import com.akarbowy.tagop.data.network.WykopService;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    @Remote
    PostsDataSource providePostsRemoteRepository(Lazy<WykopService> wykopService){
        return new PostsRemoteDataSource(wykopService);
    }

    @Singleton
    @Provides
    @Local
    PostsDataSource providePostsLocalRepository(Context context){
        return new PostsLocalDataSource(context);
    }
}
