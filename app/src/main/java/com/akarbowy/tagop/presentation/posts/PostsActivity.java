package com.akarbowy.tagop.presentation.posts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.TagopApplication;
import com.akarbowy.tagop.flux.Store;
import com.akarbowy.tagop.flux.ViewDispatch;
import com.akarbowy.tagop.presentation.search.HistoryStore;
import com.akarbowy.tagop.presentation.search.SearchHistoryAdapter;
import com.akarbowy.tagop.presentation.search.TagopActionCreator;

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

    @BindView(R.id.recycler_posts) RecyclerView historyRecycler;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private SearchHistoryAdapter adapter;

    public static Intent getStartIntent(Context context, String tag) {
        Intent intent = new Intent(context, PostsActivity.class);
        intent.putExtra(EXTRA_TAG, tag);
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ((TagopApplication) getApplication()).component().inject(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(layoutManager);
        historyRecycler.setHasFixedSize(true);
        adapter = new SearchHistoryAdapter();
        historyRecycler.setAdapter(adapter);

        String tag = getIntent().getStringExtra(EXTRA_TAG);
        creator.searchTag(tag);
    }

    @Override public List<? extends Store> getStoresToRegister() {
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(postStore);
        stores.add(historyStore);
        return stores;
    }
}
