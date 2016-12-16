package com.akarbowy.tagop.data;

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
    DataSource providePostsRemoteRepository(Lazy<WykopService> wykopService){
        return new RemoteDataSource(wykopService);
    }

    @Singleton
    @Provides
    @Local
    DataSource providePostsLocalRepository(Context context){
        return new LocalDataSource(context);
    }
}
