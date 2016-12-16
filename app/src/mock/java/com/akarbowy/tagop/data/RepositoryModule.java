package com.akarbowy.tagop.data;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    @Remote
    DataSource providePostsRemoteRepository(){
        return new FakeRemoteDataSource();
    }

    @Singleton
    @Provides
    @Local
    DataSource providePostsLocalRepository(Context context){
        return new LocalDataSource(context);
    }
}
