package com.akarbowy.tagop.data.database;


import android.support.annotation.NonNull;

import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.data.database.model.TagModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;


@Singleton
public class PostsRepository {

    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;
    private boolean supportCache = false;

    @Inject public PostsRepository(@Local LocalDataSource localDataSource,
                                   @Remote RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public void getHistory(final GetHistoryCallback callback) {
        localDataSource.getHistory(new LocalDataSource.GetSearchHistoryCallback() {
            @Override public void onDataLoaded(List<TagModel> data) {
                callback.onDataLoaded(data);
            }

            @Override public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void loadPosts(@NonNull final TagModel tag, @NonNull final int page, @NonNull final GetPostsCallback callback) {
        if (supportCache) {
            localDataSource.loadPosts(tag, page, new LocalDataSource.LoadPostsCallback() {
                @Override public void onDataLoaded(List<PostModel> data) {
                    Timber.i("local callback items: %s", data.size());
                    callback.onDataLoaded(data, true);
                }

                @Override public void onDataNotAvailable() {
                    Timber.i("local");
                }
            });
        }

        remoteDataSource.loadPosts(tag, page, new LocalDataSource.LoadPostsCallback() {
            @Override public void onDataLoaded(List<PostModel> data) {
                Timber.i("remote callback items: %s", data.size());
                if (supportCache) {
                    refreshCachedPosts(tag, data);
                }
                callback.onDataLoaded(data, false);
            }

            @Override public void onDataNotAvailable() {
                Timber.i("remote");
                callback.onDataNotAvailable();
            }
        });
    }

    public void saveTagInHistory(@NonNull TagModel tag) {
        localDataSource.saveTag(tag);
    }

    public void allowCache(boolean allow) {
        this.supportCache = allow;
    }

    private void refreshCachedPosts(TagModel tag, List<PostModel> posts) {
        for (PostModel p : posts) {
            p.setTag(tag.getName());
        }

        localDataSource.deletePostsWithTag(tag);
        localDataSource.savePosts(posts);
    }

    public void deleteSearchHistory() {
        localDataSource.deleteHistory();
    }

    public interface GetPostsCallback {
        void onDataLoaded(List<PostModel> data, boolean localSource);

        void onDataNotAvailable();
    }

    public interface GetHistoryCallback {
        void onDataLoaded(List<TagModel> data);

        void onDataNotAvailable();
    }
}
