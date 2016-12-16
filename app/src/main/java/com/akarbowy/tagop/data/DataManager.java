package com.akarbowy.tagop.data;


import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;


@Singleton
public class DataManager implements DataSource {

    private final DataSource localDataSource;
    private final DataSource remoteDataSource;

    private boolean supportCache = false;

    @Inject public DataManager(@Local DataSource localDataSource,
                               @Remote DataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void getTags(final GetHistoryCallback callback) {
        localDataSource.getTags(new GetHistoryCallback() {
            @Override public void onDataLoaded(List<TagModel> data) {
                callback.onDataLoaded(data);
            }

            @Override public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override public void loadPosts(@NonNull final TagModel tag, @NonNull final int page, @NonNull final GetPostsCallback callback) {
        if (supportCache) {
            localDataSource.loadPosts(tag, page, new GetPostsCallback() {
                @Override public void onDataLoaded(List<PostModel> data, boolean isRemoteSource) {
                    Timber.i("local callback items: %s", data.size());
                    callback.onDataLoaded(data, isRemoteSource);
                }

                @Override public void onDataNotAvailable() {
                    Timber.i("local");
                }
            });
        }

        remoteDataSource.loadPosts(tag, page, new GetPostsCallback() {
            @Override public void onDataLoaded(List<PostModel> data, boolean isRemoteSource) {
                Timber.i("remote callback items: %s", data.size());
                if (page == 1) {
                    refreshCachedPosts(tag, data);
                }
                callback.onDataLoaded(data, isRemoteSource);
            }

            @Override public void onDataNotAvailable() {
                Timber.i("remote");
                callback.onDataNotAvailable();
            }
        });
    }

    @Override public void allowCache(boolean allow) {
        this.supportCache = allow;
    }

    private void refreshCachedPosts(final TagModel tag, final List<PostModel> posts) {
        AsyncTask.execute(new Runnable() {
            @Override public void run() {
                for (PostModel p : posts) {
                    p.setTag(tag);
                }

                localDataSource.deleteTagPosts(tag);
                localDataSource.savePosts(posts);
            }
        });
    }

    @Override public void savePosts(List<PostModel> posts) {
        //no-op, impl in local data source
    }

    @Override public void deleteAllTags() {
        localDataSource.deleteAllTags();
    }

    @Override public void deleteTagPosts(TagModel tag) {
        //no-op, impl in local data source
    }

    @Override public void saveTag(TagModel tag) {
        localDataSource.saveTag(tag);
    }

    @Override public void deleteTag(final TagModel tag) {
        AsyncTask.execute(new Runnable() {
            @Override public void run() {
                localDataSource.deleteTag(tag);
            }
        });
    }
}
