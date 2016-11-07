package com.akarbowy.tagop.data.database;


import com.akarbowy.tagop.data.network.WykopService;
import com.akarbowy.tagop.data.network.model.QueryResult;
import com.akarbowy.tagop.data.network.model.TagEntry;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class PostsRepository implements PostsDataSource {

    private Lazy<WykopService> service;
    private final PostsDataSource localDataSource;
    private final PostsDataSource remoteDataSource;

    @Inject public PostsRepository(@Local PostsDataSource localDataSource,
                                   @Remote PostsDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public void search(String tag, int page, final GetPostsCallback callback) {
        final Callback<QueryResult> request = new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                if (response.isSuccessful()) {
                    callback.onDataLoaded(response.body().entries);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                Timber.e(t, "fail");
                callback.onDataNotAvailable();
            }
        };

        service.get().search(tag, page).enqueue(request);
    }

    public interface GetPostsCallback {
        void onDataLoaded(List<TagEntry> data);

        void onDataNotAvailable();
    }
}
