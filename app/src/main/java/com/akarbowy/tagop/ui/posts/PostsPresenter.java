package com.akarbowy.tagop.ui.posts;


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;

import com.akarbowy.tagop.data.database.PostsRepository;
import com.akarbowy.tagop.data.network.model.TagEntry;

import java.util.List;

import javax.inject.Inject;

public class PostsPresenter implements PostsContract.Presenter {

    private String tag;
    private PostsRepository repository;
    private PostsContract.View view;

    private CountingIdlingResource idlingResource;
    private int page = 0;

    @Inject PostsPresenter(@NonNull String tag,
                           PostsRepository repository,
                           PostsContract.View view) {
        this.tag = tag;
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Inject void setupListeners() {
        view.setPresenter(this);
    }

    private void loadPosts(boolean forceUpdate, final boolean isNextPage) {
        getIdlingResource().increment();

        if (isNextPage) {
            view.setPageLoader(true);
        }

        view.setRefreshing(true);

        repository.search(tag, page + 1, new PostsRepository.GetPostsCallback() {
            @Override public void onDataLoaded(List<TagEntry> data) {
                if (!view.isActive()) {
                    return;
                }

                if (!isNextPage) {
                    view.setState(!data.isEmpty() ? PostsActivity.State.CONTENT
                            : PostsActivity.State.CONTENT_EMPTY);
                    view.setRefreshing(false);
                }
                view.setItems(data, !isNextPage);

                page++;

                if (getIdlingResource().isIdleNow()) {
                    getIdlingResource().decrement();
                }
            }

            @Override public void onDataNotAvailable() {
                if (!view.isActive()) {
                    return;
                }

                view.setState(PostsActivity.State.ERROR);
                view.setRefreshing(false);

                if (isNextPage) {
                    view.setPageLoader(false);
                }

                if (getIdlingResource().isIdleNow()) {
                    getIdlingResource().decrement();
                }
            }
        });
    }

    @Override public void loadPosts(boolean forceUpdate) {
        page = 0;
        loadPosts(true, false);
    }

    public void loadNextPosts() {
        loadPosts(false, true);
    }

    @VisibleForTesting @NonNull public CountingIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new CountingIdlingResource("posts");
        }
        return idlingResource;
    }

}
