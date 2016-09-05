package com.akarbowy.tagop.presentation.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.R;
import com.akarbowy.tagop.TagopApplication;
import com.akarbowy.tagop.database.TagHistory;
import com.akarbowy.tagop.flux.ActionError;
import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Store;
import com.akarbowy.tagop.flux.ViewDispatch;
import com.akarbowy.tagop.helpers.RecyclerSupport;
import com.akarbowy.tagop.presentation.posts.PostsActivity;
import com.akarbowy.tagop.utils.KeyboardUtil;
import com.akarbowy.tagop.utils.TextUtil;
import com.squareup.otto.Subscribe;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainSearchActivity extends AppCompatActivity implements ViewDispatch, RecyclerSupport.OnItemClickListener {

    private static ButterKnife.Setter<View, Boolean> SET_QUERY_CANCEL_VIEW_VISIBILITY;

    @Inject HistoryStore historyStore;
    @Inject TagopActionCreator creator;

    @BindView(R.id.toolbar_search) Toolbar toolbarView;
    @BindView(R.id.toolbar_layout_action) FrameLayout toolbarActionLayout;
    @BindView(R.id.toolbar_layout_searchable) FrameLayout toolbarSearchableLayout;
    @BindView(R.id.field_query) EditText queryView;
    @BindView(R.id.button_field_cancel) ImageView queryCancelView;
    @BindView(R.id.recycler_history) RecyclerView historyRecycler;
    @BindView(R.id.empty_state_history) View emptyHistoryView;

    private SearchHistoryAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TagopApplication) getApplication()).component().inject(this);

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        configureToolbarBehaviour();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(layoutManager);
        historyRecycler.setHasFixedSize(true);
        adapter = new SearchHistoryAdapter();
        historyRecycler.setAdapter(adapter);

        RecyclerSupport.addTo(historyRecycler)
                .setOnItemClickListener(this)
                .setEmptyStateView(emptyHistoryView);
    }

    // history store gets unregistered onPause.
    // onStoreChange wont be call when returning from PostActivity
    @Override protected void onResume() {
        super.onResume();
        refreshHistoryList();
    }

    private void configureToolbarBehaviour() {
        toolbarView.inflateMenu(R.menu.menu_search);
        toolbarActionLayout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                toolbarActionLayout.setVisibility(View.GONE);
                toolbarSearchableLayout.setVisibility(View.VISIBLE);
                queryView.getText().clear();
                queryView.requestFocus();
                KeyboardUtil.show(queryView);
                toolbarView.getMenu().clear();
                toolbarView.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        toolbarView.setNavigationIcon(null);
                        toolbarView.getMenu().clear();
                        toolbarView.inflateMenu(R.menu.menu_search);
                        queryView.getText().clear();
                        toolbarActionLayout.setVisibility(View.VISIBLE);
                        toolbarSearchableLayout.setVisibility(View.GONE);
                        KeyboardUtil.hide(queryView);
                    }
                });
            }
        });

        toolbarView.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_clear_history:
                        creator.clearHistory();
                        return true;
                }

                return false;
            }
        });

        SET_QUERY_CANCEL_VIEW_VISIBILITY = new ButterKnife.Setter<View, Boolean>() {
            @Override public void set(@NonNull View view, Boolean visible, int index) {
                view.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        };

        queryCancelView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                queryView.getText().clear();
                ButterKnife.apply(queryCancelView, SET_QUERY_CANCEL_VIEW_VISIBILITY, false);

            }
        });

        queryView.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                ButterKnife.apply(queryCancelView, SET_QUERY_CANCEL_VIEW_VISIBILITY, !text.toString().isEmpty());

            }

            @Override public void afterTextChanged(final Editable query) {
                if (!query.toString().isEmpty()) {
                    creator.filterHistory(query.toString());
                }

            }
        });

        queryView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (!TextUtil.empty(textView)) {
                    String query = queryView.getText().toString().trim();
                    Timber.i("Query searching %s", query);
                    searchForPostsWithTag(query);
                    return true;
                }

                return false;
            }
        });
    }

    @Override public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Timber.i("Item clicked: %s", position);
        searchForPostsWithTag(adapter.getItem(position).getName());
    }

    private void searchForPostsWithTag(String tag) {
        startActivity(PostsActivity.getStartIntent(MainSearchActivity.this, tag));
    }

    private void refreshHistoryList() {
        List<TagHistory> tags = historyStore.getTagNames();
        Timber.i("refreshing with: %s", tags.toString());
        adapter.refresh(tags);
    }

    @Subscribe public void onStoreChange(Change change) {
        switch (change.getStoreId()) {
            case HistoryStore.ID:
                switch (change.getAction().getType()) {
                    case Actions.CLEAR_TAG_HISTORY:
                        refreshHistoryList();
                        break;
                }
                break;
        }
    }

    @Subscribe public void onError(ActionError error) {
        Timber.e(error.getThrowable(), "hola hola mamy problem z akcja: %s", error.getActionType());
    }

    @Override public List<? extends Store> getStoresToRegister() {
        return Collections.singletonList(historyStore);
    }
}