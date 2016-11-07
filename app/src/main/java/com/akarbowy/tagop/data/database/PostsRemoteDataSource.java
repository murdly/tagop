package com.akarbowy.tagop.data.database;


import com.akarbowy.tagop.data.network.WykopService;

import javax.inject.Singleton;

import dagger.Lazy;

@Singleton
public class PostsRemoteDataSource implements PostsDataSource {
    public PostsRemoteDataSource(Lazy<WykopService> serviceLazy) {

    }
}
