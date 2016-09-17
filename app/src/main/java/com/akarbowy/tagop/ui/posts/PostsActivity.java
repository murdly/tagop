package com.akarbowy.tagop.ui.posts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.R;
import com.akarbowy.tagop.TagopApplication;
import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Store;
import com.akarbowy.tagop.flux.ViewDispatch;
import com.akarbowy.tagop.helpers.RecyclerSupport;
import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.ui.search.HistoryStore;
import com.akarbowy.tagop.ui.search.TagopActionCreator;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsActivity extends AppCompatActivity implements ViewDispatch {

    private static final String EXTRA_TAG = "extra_tag";

    @Inject PostStore postStore;
    @Inject HistoryStore historyStore;
    @Inject TagopActionCreator creator;

    @BindView(R.id.recycler_posts) RecyclerView postsRecycler;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.empty_state_results) View emptyStateView;

    private PostsAdapter adapter;
    private String tag;

    private RecyclerSupport.OnNextPageRequestListener onNextPageRequestListener =
            new RecyclerSupport.OnNextPageRequestListener() {
                @Override public void onLoadPage(int requestedPage) {
                    loadPage(requestedPage);

                    //return has more
                }
            };

    public static Intent getStartIntent(Context context, String tag) {
        Intent intent = new Intent(context, PostsActivity.class);
        intent.putExtra(EXTRA_TAG, tag);
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);

        ((TagopApplication) getApplication()).component().inject(this);

        adapter = new PostsAdapter();

        postsRecycler.setLayoutManager(new LinearLayoutManager(this));
        postsRecycler.setHasFixedSize(true);
        postsRecycler.setAdapter(adapter);

        RecyclerSupport.addTo(postsRecycler)
                .setEmptyStateView(emptyStateView)
                .setOnNextPageRequestListener(onNextPageRequestListener, adapter.getAbsolutePartsCount(), true);

        tag = getIntent().getStringExtra(EXTRA_TAG);
        loadPage(1);
    }

    @Subscribe public void onStoreChange(Change change) {
        switch (change.getStoreId()) {
            case PostStore.ID:
                switch (change.getAction().getType()) {
                    case Actions.SEARCH_TAG:
                        showPosts();
                        break;
                }
                break;
        }
    }

    private void showPosts() {
        adapter.removeLoader();
        ArrayList<TagEntry> entries = postStore.getEntries();
        adapter.setItems(entries);

        //page 0
        if (adapter.getItemCount() == 0) {
            Toast.makeText(PostsActivity.this, "Brak wyników", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPage(int page) {
        adapter.insertLoader();
        creator.searchTag(tag, page);
    }

    @Override public List<? extends Store> getStoresToRegister() {
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(postStore);
        stores.add(historyStore);
        return stores;
    }
}