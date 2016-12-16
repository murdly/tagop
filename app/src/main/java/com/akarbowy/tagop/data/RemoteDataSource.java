package com.akarbowy.tagop.data;


import android.support.annotation.NonNull;

import com.akarbowy.tagop.data.model.PostDataMapper;
import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;
import com.akarbowy.tagop.data.network.WykopService;
import com.akarbowy.tagop.data.network.model.Post;
import com.akarbowy.tagop.data.network.model.QueryResult;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class RemoteDataSource implements DataSource {
    private final Lazy<WykopService> service;

    @Inject RemoteDataSource(Lazy<WykopService> serviceLazy) {
        this.service = serviceLazy;
    }

    public void loadPosts(@NonNull final TagModel tag, @NonNull int page, @NonNull final DataSource.GetPostsCallback callback) {
        final Callback<QueryResult> request = new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                if (response.isSuccessful()) {
                    List<Post> entries = response.body().entries;
                    List<PostModel> postModels = PostDataMapper.map(entries, tag);
                    callback.onDataLoaded(postModels, true);
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

        service.get().search(tag.getTitle(), page).enqueue(request);
    }

    @Override public void savePosts(List<PostModel> posts) {

    }

    @Override public void allowCache(boolean allow) {

    }

    @Override public void getTags(GetHistoryCallback callback) {

    }

    @Override public void saveTag(TagModel tag) {

    }

    @Override public void deleteTag(TagModel tag) {

    }

    @Override public void deleteTagPosts(TagModel tag) {

    }

    @Override public void deleteAllTags() {

    }

}
