package com.akarbowy.tagop.ui.posts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.Keys;
import com.akarbowy.tagop.R;
import com.akarbowy.tagop.TagopApplication;
import com.akarbowy.tagop.flux.Action;
import com.akarbowy.tagop.flux.ActionError;
import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Store;
import com.akarbowy.tagop.flux.ViewDispatch;
import com.akarbowy.tagop.helpers.RecyclerSupport;
import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.ui.search.HistoryStore;
import com.akarbowy.tagop.ui.search.TagopActionCreator;
import com.akarbowy.tagop.utils.StateSwitcher;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PostsActivity extends AppCompatActivity implements ViewDispatch, RecyclerSupport.OnNextPageRequestListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String EXTRA_TAG = "extra_tag";
    private static final String EXTRA_HISTORY_SEARCH = "extra_history_search";
    private static final String ACTION_SEARCH = "com.akarbowy.tagop.ui.posts.ACTION_SEARCH";

    @Inject PostStore postStore;
    @Inject HistoryStore historyStore;
    @Inject TagopActionCreator creator;

    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshWidget;
    @BindView(R.id.recycler) RecyclerView postsRecycler;
    @BindViews({R.id.recycler, R.id.state_empty_content, R.id.state_general_error}) List<View> stateViews;

    private boolean isCreatedUponHistorySearch;
    private boolean wasStopped = false;

    private PostsAdapter adapter;
    private StateSwitcher stateSwitcher;

    private String tag;
    private CountingIdlingResource idlingResource;

    public static Intent getStartIntent(Context context, String tag, boolean isFromHistory) {
        Intent intent = new Intent(context, PostsActivity.class);
        intent.putExtra(EXTRA_TAG, tag);
        intent.putExtra(EXTRA_HISTORY_SEARCH, isFromHistory);
        intent.setAction(ACTION_SEARCH);
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);
        ((TagopApplication) getApplication()).component().inject(this);

        if (getIntent().getAction().equals(ACTION_SEARCH)) {
            tag = getIntent().getStringExtra(EXTRA_TAG);
            isCreatedUponHistorySearch = getIntent().getBooleanExtra(EXTRA_HISTORY_SEARCH, true);
        } else {
            tag = getIntent().getData().getFragment();
            isCreatedUponHistorySearch = false;
        }

        toolbarView.setTitle(tag);
        toolbarView.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                finish();
            }
        });

        refreshWidget.setOnRefreshListener(this);

        adapter = new PostsAdapter();
        postsRecycler.setLayoutManager(new LinearLayoutManager(this));
        postsRecycler.setHasFixedSize(true);
        postsRecycler.setAdapter(adapter);

        stateSwitcher = new StateSwitcher();
        stateSwitcher.setViews(stateViews, new int[]{State.CONTENT, State.CONTENT_EMPTY, State.ERROR});

        RecyclerSupport.addTo(postsRecycler)
                .setOnNextPageRequestListener(this, adapter.getAbsolutePartsCount(), true);

        loadInitialData();
    }

    @Override public void onResume() {
        super.onResume();
        if (isCreatedUponHistorySearch || wasStopped) {
            Timber.i("should show cache and make call");
        } else {
            Timber.i("should only make call");
        }
    }

    @Override protected void onStop() {
        super.onStop();
        wasStopped = true;
    }

    private void loadInitialData() {
        getIdlingResource().increment();

        //load from cache
        refreshWidget.setRefreshing(true);
        creator.searchTag(tag, postStore.getFirstPageIndex());
    }

    @Override public void onLoadNextPage() {
        getIdlingResource().increment();

        adapter.insertPageLoader();
        creator.searchTag(tag, postStore.getNextPageIndex());
    }

    @Override public void onRefresh() {
        getIdlingResource().increment();

        refreshWidget.setRefreshing(true);
        creator.searchTag(tag, postStore.getFirstPageIndex());
    }

    @VisibleForTesting @Subscribe public void onStoreChange(Change change) {
        switch (change.getStoreId()) {
            case PostStore.ID:
                Action action = change.getAction();
                switch (action.getType()) {
                    case Actions.SEARCH_TAG:
                        boolean isFirstPage = action.get(Keys.FIRST_PAGE);
                        updateItemsAndState(isFirstPage);

                        getIdlingResource().decrement();
                        break;
                }
                break;
        }
    }

    @VisibleForTesting @Subscribe public void onActionError(ActionError error) {
        stateSwitcher.setState(State.ERROR);
        refreshWidget.setRefreshing(false);
        adapter.removePageLoader();

        getIdlingResource().decrement();
    }

    private void updateItemsAndState(boolean isFirstPage) {
        ArrayList<TagEntry> items = postStore.getResults();

        if (isFirstPage) {
            stateSwitcher.setState(!items.isEmpty() ? State.CONTENT : State.CONTENT_EMPTY);
            refreshWidget.setRefreshing(false);
        } else {
            adapter.removePageLoader();
        }
        adapter.setItems(items, isFirstPage);
    }

    @Override public List<? extends Store> getStoresToRegister() {
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(postStore);
        stores.add(historyStore);
        return stores;
    }

    @VisibleForTesting @NonNull public CountingIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new CountingIdlingResource("posts");
        }
        return idlingResource;
    }

    public interface State {
        int CONTENT = 0;
        int CONTENT_EMPTY = 1;
        int ERROR = 2;
    }
}