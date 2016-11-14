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
    RemoteDataSource providePostsRemoteRepository(Lazy<WykopService> wykopService){
        return new RemoteDataSource(wykopService);
    }

    @Singleton
    @Provides
    @Local
    LocalDataSource providePostsLocalRepository(Context context){
        return new LocalDataSource(context);
    }
}
