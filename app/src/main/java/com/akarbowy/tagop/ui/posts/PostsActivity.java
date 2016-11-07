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

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.TagopApplication;
import com.akarbowy.tagop.data.network.model.TagEntry;
import com.akarbowy.tagop.utils.RecyclerSupport;
import com.akarbowy.tagop.utils.StateSwitcher;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PostsActivity extends AppCompatActivity implements PostsContract.View, RecyclerSupport.OnNextPageRequestListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String EXTRA_TAG = "extra_tag";
    private static final String EXTRA_HISTORY_SEARCH = "extra_history_search";
    private static final String ACTION_SEARCH = "com.akarbowy.tagop.ui.posts.ACTION_SEARCH";

    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshWidget;
    @BindView(R.id.recycler) RecyclerView postsRecycler;
    @BindViews({R.id.recycler, R.id.state_empty_content, R.id.state_general_error}) List<View> stateViews;

    boolean isActive;
    private PostsContract.Presenter presenter;

    private PostsAdapter adapter;
    private StateSwitcher stateSwitcher;

    private boolean isCreatedUponHistorySearch;
    private boolean wasStopped = false;

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

        String tag;
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
                .setOnNextPageRequestListener(this, adapter.getPostsBasicPartsCount(), true);

        DaggerPostsComponent.builder()
                .postsModule(new PostsModule(this, tag))
                .applicationComponent(((TagopApplication) getApplication()).component())
                .build()
                .inject(this);
    }

    @Override public void onResume() {
        super.onResume();
        if (isCreatedUponHistorySearch || wasStopped) {
            Timber.i("should show cache and make call");
        } else {
            Timber.i("should only make call");
        }

        isActive = true;
        presenter.loadPosts(false);
    }

    @Override protected void onPause() {
        super.onPause();
        isActive = false;
    }

    @Override protected void onStop() {
        super.onStop();
        wasStopped = true;
    }

    @Override public void setPresenter(PostsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void onLoadNextPage() {
        presenter.loadNextPosts();
    }

    @Override public void onRefresh() {
        presenter.loadPosts(true);
    }

    @Override public void setPageLoader(boolean insert) {
        if (insert) {
            adapter.insertPageLoader();
        } else {
            adapter.removePageLoader();
        }
    }

    @Override public void setRefreshing(boolean refreshing) {
        refreshWidget.setRefreshing(refreshing);
    }

    @Override public void setItems(List<TagEntry> data, boolean clear) {
        adapter.setItems(data, clear);
    }

    @Override public void setState(int state) {
        stateSwitcher.setState(state);
    }

    @Override public boolean isActive() {
        return isActive;
    }

    public interface State {
        int CONTENT = 0;
        int CONTENT_EMPTY = 1;
        int ERROR = 2;
    }
}