package com.akarbowy.tagop.ui.posts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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
import com.akarbowy.tagop.ui.search.HistoryStore;
import com.akarbowy.tagop.ui.search.TagopActionCreator;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PostsActivity extends AppCompatActivity implements ViewDispatch, RecyclerSupport.OnNextPageRequestListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String EXTRA_TAG = "extra_tag";
    private static final String EXTRA_HISTORY_SEARCH = "extra_history_search";

    @Inject PostStore postStore;
    @Inject HistoryStore historyStore;
    @Inject TagopActionCreator creator;

    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshWidget;
    @BindView(R.id.recycler) RecyclerView postsRecycler;
    @BindView(R.id.empty_state_results) View emptyStateView;

    private boolean isCreatedUponHistorySearch;
    private boolean wasStopped = false;

    private PostsAdapter adapter;
    private String tag;

    public static Intent getStartIntent(Context context, String tag, boolean isFromHistory) {
        Intent intent = new Intent(context, PostsActivity.class);
        intent.putExtra(EXTRA_TAG, tag);
        intent.putExtra(EXTRA_HISTORY_SEARCH, isFromHistory);
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);
        ((TagopApplication) getApplication()).component().inject(this);

        tag = getIntent().getStringExtra(EXTRA_TAG);
        isCreatedUponHistorySearch = getIntent().getBooleanExtra(EXTRA_HISTORY_SEARCH, true);

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
        RecyclerSupport.addTo(postsRecycler)
                .setEmptyStateView(emptyStateView)
                .setOnNextPageRequestListener(this, adapter.getAbsolutePartsCount(), true);

        loadInitialData();
        Timber.i("%s", System.currentTimeMillis());

    }

    @Override protected void onResume() {
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

    @Subscribe public void onStoreChange(Change change) {
        switch (change.getStoreId()) {
            case PostStore.ID:
                Action action = change.getAction();
                switch (action.getType()) {
                    case Actions.SEARCH_TAG:
                        boolean firstPage = action.get(Keys.FIRST_PAGE);
                        if (firstPage) {
                            refreshWidget.setRefreshing(false);
                        } else {
                            adapter.removePageLoader();
                        }
                        adapter.setItems(postStore.getResults(), firstPage);
                        break;
                }
                break;
        }
    }

    @Subscribe public void onActionError(ActionError error) {
        refreshWidget.setRefreshing(false);
        adapter.removePageLoader();
        Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
    }

    private void loadInitialData() {
        //load from cache
        refreshWidget.setRefreshing(true);
        creator.searchTag(tag, postStore.getFirstPageIndex());
    }

    @Override public void onLoadNextPage() {
        adapter.insertPageLoader();
        creator.searchTag(tag, postStore.getNextPageIndex());
    }

    @Override public void onRefresh() {
        refreshWidget.setRefreshing(true);
        creator.searchTag(tag, postStore.getFirstPageIndex());
    }

    @Override public List<? extends Store> getStoresToRegister() {
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(postStore);
        stores.add(historyStore);
        return stores;
    }
}