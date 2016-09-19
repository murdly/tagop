package com.akarbowy.tagop.ui.search;

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
import com.akarbowy.tagop.ui.posts.PostsActivity;
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
    private ToolbarMode mode;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        configureToolbarBehaviour();
        ((TagopApplication) getApplication()).component().inject(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(layoutManager);
        historyRecycler.setHasFixedSize(true);
        adapter = new SearchHistoryAdapter(HistoryStore.ALPHABETICAL_COMPARATOR);
        historyRecycler.setAdapter(adapter);

        RecyclerSupport.addTo(historyRecycler)
                .setOnItemClickListener(this)
                .setEmptyStateView(emptyHistoryView);
    }

    // history store gets unregistered onPause.
    // onStoreChange wont be call when returning from PostActivity
    @Override protected void onResume() {
        super.onResume();
        if (mode == ToolbarMode.Action) {
            refreshHistoryList();
        } else {
            KeyboardUtil.show(queryView);
            creator.filterHistory(TextUtil.getTrimmed(queryView));
        }
    }

    private void configureToolbarBehaviour() {
        mode = ToolbarMode.Action;
        toolbarView.inflateMenu(R.menu.menu_search);
        toolbarActionLayout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                switchToolbarMode(ToolbarMode.Searchable);
            }
        });

        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                switchToolbarMode(ToolbarMode.Action);
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
                creator.filterHistory(query.toString());
            }
        });

        queryView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (!TextUtil.empty(textView)) {
                    String query = TextUtil.getTrimmed(textView);
                    Timber.i("Query searching %s", query);
                    searchForPostsWithTag(query, false);
                    return true;
                }

                return false;
            }
        });
    }

    private void switchToolbarMode(ToolbarMode mode) {
        this.mode = mode;
        
        if (mode == ToolbarMode.Searchable) {
            toolbarActionLayout.setVisibility(View.GONE);
            toolbarSearchableLayout.setVisibility(View.VISIBLE);
            queryView.getText().clear();
            queryView.requestFocus();
            KeyboardUtil.show(queryView);
            toolbarView.getMenu().clear();
            toolbarView.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        } else if (mode == ToolbarMode.Action) {
            toolbarView.setNavigationIcon(null);
            toolbarView.getMenu().clear();
            toolbarView.inflateMenu(R.menu.menu_search);
            queryView.getText().clear();
            toolbarActionLayout.setVisibility(View.VISIBLE);
            toolbarSearchableLayout.setVisibility(View.GONE);
            KeyboardUtil.hide(queryView);
        }
    }

    @Override public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        searchForPostsWithTag(adapter.getItem(position).getName(), true);
    }

    private void searchForPostsWithTag(String tag, boolean isHistorySearch) {
        startActivity(PostsActivity.getStartIntent(MainSearchActivity.this, tag, isHistorySearch));
    }

    private void refreshHistoryList() {
        List<TagHistory> tags = historyStore.getTagNames();
        Timber.i("refreshing with: %s", tags.toString());
        adapter.add(tags);
    }

    @Subscribe public void onStoreChange(Change change) {
        switch (change.getStoreId()) {
            case HistoryStore.ID:
                switch (change.getAction().getType()) {
                    case Actions.CLEAR_TAG_HISTORY:
                        refreshHistoryList();
                        break;
                    case Actions.FILTER_HISTORY_TAG:
                        adapter.replaceAll(historyStore.getFilteredEntries());
                        historyRecycler.scrollToPosition(0);
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

    @Override public void onBackPressed() {
        if (mode == ToolbarMode.Searchable) {
            switchToolbarMode(ToolbarMode.Action);
        } else {
            super.onBackPressed();
        }
    }

    public enum ToolbarMode {
        Searchable, Action
    }
}