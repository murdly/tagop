package com.akarbowy.tagop.ui.posts;


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;

import com.akarbowy.tagop.data.DataManager;
import com.akarbowy.tagop.data.DataSource;
import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PostsPresenter implements PostsContract.Presenter {

    private TagModel tag;
    private DataManager repository;
    private PostsContract.View view;

    private List<PostModel> freshDataToPickUp = new ArrayList<>();
    private int nextPage = 1;

    private CountingIdlingResource idlingResource;

    @Inject PostsPresenter(@NonNull TagModel tag,
                           DataManager repository,
                           PostsContract.View view) {
        this.tag = tag;
        this.repository = repository;
        this.view = view;
    }

    public void loadPosts(boolean entry) {
        nextPage = 1;
        view.setActionIndicator(false); // case: refreshing when indicator visible
        load(entry);
    }

    public void loadNextPosts() {
        view.setPageLoader(true);

        repository.allowCache(false);
        repository.loadPosts(tag, nextPage, new DataSource.GetPostsCallback() {
            @Override public void onDataLoaded(List<PostModel> data, boolean remoteSource) {
                if (!view.isActive()) {
                    return;
                }

                view.setPageLoader(false);
                view.setItems(data, false);
                nextPage++;
            }

            @Override public void onDataNotAvailable() {
                if (!view.isActive()) {
                    return;
                }

                view.setPageLoader(false);
            }
        });
    }

    private void load(final boolean entryLoad) {
        view.setRefreshing(true);

        repository.allowCache(entryLoad);

        if (entryLoad && tag.isForSaving()) {
            repository.saveTag(tag);
        }

        repository.loadPosts(tag, nextPage, new DataSource.GetPostsCallback() {
            @Override public void onDataLoaded(List<PostModel> data, boolean remoteSource) {
                if (!view.isActive()) {
                    return;
                }

                view.setRefreshing(false);

                if (view.isAtTop()) {
                    view.setItems(data, true);
                } else {
                    view.setActionIndicator(true);
                    freshDataToPickUp = new ArrayList<>(data);
                }

                if (remoteSource) {
                    if(data.isEmpty()){
                        view.showContentEmpty();
                    } else {
                        nextPage++;
                    }
                }
            }

            @Override public void onDataNotAvailable() {
                if (!view.isActive()) {
                    return;
                }

                view.showError(nextPage == 1);
                view.setRefreshing(false);
            }
        });
    }

    @Override public void popFreshPosts() {
        view.setItems(freshDataToPickUp, true);
        view.setActionIndicator(false);

        freshDataToPickUp = null;
    }

    @VisibleForTesting @NonNull public CountingIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new CountingIdlingResource("posts");
        }
        return idlingResource;
    }

}
