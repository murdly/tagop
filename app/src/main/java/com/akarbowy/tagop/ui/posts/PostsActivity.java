package com.akarbowy.tagop.ui.posts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.TagopApplication;
import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;
import com.akarbowy.tagop.utils.RecyclerSupport;
import com.akarbowy.tagop.utils.StateSwitcher;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostsActivity extends AppCompatActivity implements PostsContract.View, RecyclerSupport.OnNextPageRequestListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String ACTION_SEARCH = "com.akarbowy.tagop.ACTION_SEARCH";
    public static final String EXTRA_TAG = "extra_tag";

    @BindView(R.id.appbar) AppBarLayout appBarLayout;
    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshWidget;
    @BindView(R.id.recycler) RecyclerView postsRecycler;
    @BindView(R.id.action_view) TextView actionView;
    @BindViews({R.id.recycler, R.id.state_empty_content, R.id.state_general_error}) List<View> stateViews;

    @Inject PostsPresenter presenter;
    private boolean isActive;

    private PostsAdapter adapter;
    private StateSwitcher stateSwitcher;
    private LinearLayoutManager layoutManager;

    public static Intent getStartIntent(Context context, String tag) {
        Intent intent = new Intent(context, PostsActivity.class);
        intent.putExtra(EXTRA_TAG, tag);
        intent.setAction(ACTION_SEARCH);
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);

        TagModel tag;
        if (getIntent().getAction().equals(ACTION_SEARCH)) {
            tag = new TagModel(getIntent().getStringExtra(EXTRA_TAG), true);
        } else {
            tag = new TagModel(getIntent().getData().getFragment(), false);
        }

        toolbarView.setTitle(tag.getTitle());
        toolbarView.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                finish();
            }
        });

        refreshWidget.setOnRefreshListener(this);

        adapter = new PostsAdapter();
        layoutManager = new LinearLayoutManager(this);
        postsRecycler.setLayoutManager(layoutManager);
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

        isActive = true;
        presenter.loadPosts(true);
    }

    @Override public void onResume() {
        super.onResume();
        if (!isActive) {
            isActive = true;
            presenter.loadPosts(false);
        }
    }

    @Override protected void onStop() {
        super.onStop();
        isActive = false;
    }

    @OnClick(R.id.action_view) public void showFreshData() {
        presenter.popFreshPosts();
    }

    @Override public void onLoadNextPage() {
        presenter.loadNextPosts();
    }

    @Override public void onRefresh() {
        presenter.loadPosts(false);
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

    @Override public void setActionIndicator(boolean visible) {
        actionView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override public void showError(boolean firstPage) {
        if (adapter.getItemCount() == 0) {
            stateSwitcher.setState(State.ERROR);
        } else {
            Toast.makeText(this, R.string.toast_loading_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override public void setItems(List<PostModel> data, boolean firstPage) {
        adapter.setItems(data, firstPage);

        stateSwitcher.setState(firstPage && data.isEmpty() ? State.CONTENT_EMPTY : PostsActivity.State.CONTENT);

        if (firstPage) {
            layoutManager.scrollToPosition(0);
            appBarLayout.setExpanded(true);
        }
    }

    @Override public boolean isAtTop() {
        return adapter.getItemCount() == 0 || layoutManager.findFirstVisibleItemPosition() == 0;
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