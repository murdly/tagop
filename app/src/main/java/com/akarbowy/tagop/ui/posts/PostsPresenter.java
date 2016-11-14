package com.akarbowy.tagop.ui.posts;


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;

import com.akarbowy.tagop.data.database.PostsRepository;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.data.database.model.TagModel;

import java.util.List;

import javax.inject.Inject;

public class PostsPresenter implements PostsContract.Presenter {

    private TagModel tag;
    private PostsRepository repository;
    private PostsContract.View view;

    private int nextPage = 1;
    private boolean entryLoad = false;

    private CountingIdlingResource idlingResource;

    @Inject PostsPresenter(@NonNull TagModel tag,
                           PostsRepository repository,
                           PostsContract.View view) {
        this.tag = tag;
        this.repository = repository;
        this.view = view;
    }

    public void loadPosts() {
        nextPage = 1;
        entryLoad = true;
        load(nextPage);
    }

    public void loadNextPosts() {
        entryLoad = false;
        load(nextPage);
    }

    private void load(int page) {

        if (entryLoad) {
            view.setRefreshing(true);
        } else {
            view.setPageLoader(true);
        }

        repository.allowCache(entryLoad);

        if (entryLoad && tag.isForSaving()) {
            repository.saveTagInHistory(tag);
        }

        repository.loadPosts(tag, page, new PostsRepository.GetPostsCallback() {
            @Override public void onDataLoaded(List<PostModel> data, boolean localSource) {
                if (!view.isActive()) {
                    return;
                }

                view.setRefreshing(false); //do zrobienia popup
                view.setItems(data, entryLoad);

                if (!localSource) {

                    /*
                    kolejny error state gdy: juz mamy wyniki np toast
                    inaczej zasloni caly ekran
                     */
                    view.setState(!data.isEmpty() ? PostsActivity.State.CONTENT : PostsActivity.State.CONTENT_EMPTY);
                    nextPage++;
                }
            }

            @Override public void onDataNotAvailable() {
                if (!view.isActive()) {
                    return;
                }

                view.setState(PostsActivity.State.ERROR);

                if (entryLoad) {
                    view.setRefreshing(false);
                } else {
                    view.setPageLoader(false);
                }

            }
        });
    }


    @VisibleForTesting @NonNull public CountingIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new CountingIdlingResource("posts");
        }
        return idlingResource;
    }

}
