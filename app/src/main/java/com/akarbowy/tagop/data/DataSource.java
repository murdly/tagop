package com.akarbowy.tagop.data;

import android.support.annotation.NonNull;

import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;

import java.util.List;

public interface DataSource {

    void loadPosts(@NonNull TagModel tag, @NonNull int page, @NonNull GetPostsCallback callback);

    void savePosts(List<PostModel> posts);

    void allowCache(boolean allow);

    void getTags(GetHistoryCallback callback);

    void saveTag(TagModel tag);

    void deleteTag(TagModel tag);

    void deleteTagPosts(TagModel tag);

    void deleteAllTags();

    interface GetHistoryCallback {
        void onDataLoaded(List<TagModel> data);

        void onDataNotAvailable();
    }

    interface GetPostsCallback {

        void onDataLoaded(List<PostModel> data, boolean remoteSource);
        void onDataNotAvailable();

    }

}
