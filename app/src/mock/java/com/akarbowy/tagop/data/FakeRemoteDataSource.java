package com.akarbowy.tagop.data;


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;

import java.util.ArrayList;
import java.util.List;

public class FakeRemoteDataSource implements DataSource {

    private final static List<PostModel> POSTS_SERVICE_DATA = new ArrayList<>();

    @VisibleForTesting
    public void addPost(PostModel post){
        POSTS_SERVICE_DATA.add(post);
    }

    @Override public void loadPosts(@NonNull TagModel tag, @NonNull int page, @NonNull GetPostsCallback callback) {
        List<PostModel> posts = new ArrayList<>();
        for (PostModel p : POSTS_SERVICE_DATA) {
            if(p.getTag().equals(tag)){
                posts.add(p);
            }
        }

        callback.onDataLoaded(posts, true);

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
