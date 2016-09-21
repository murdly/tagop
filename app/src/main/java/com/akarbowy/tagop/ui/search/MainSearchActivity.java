package com.akarbowy.tagop.ui.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.akarbowy.tagop.ui.search.SearchableToolbarView.Mode.Normal;

public class MainSearchActivity extends AppCompatActivity implements ViewDispatch, RecyclerSupport.OnItemClickListener {

    @Inject HistoryStore historyStore;
    @Inject TagopActionCreator creator;

    @BindView(R.id.toolbar_action_view) SearchableToolbarView toolbar;
    @BindView(R.id.recycler_history) RecyclerView historyRecycler;
    @BindView(R.id.empty_state_history) View emptyHistoryView;

    private SearchHistoryAdapter adapter;

    private SearchableToolbarView.Callback toolbarActionsCallback = new SearchableToolbarView.Callback() {
        @Override public void onSearchPerform(String query) {
            Timber.i("Query searching %s", query);
            searchForPostsWithTag(query, false);
        }

        @Override public void onQueryTextChange(String queryText) {
            creator.filterHistory(queryText);
        }

        @Override public void onMenuClearHistoryClick() {
            creator.clearHistory();
        }

        @Override public void onModeChanged(SearchableToolbarView.Mode newMode) {
            Timber.i("Mode %s", newMode);
            if (newMode == Normal) {
//                setEmptyStateIfNoHistory();
            }
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        toolbar.setCallback(toolbarActionsCallback);
        ((TagopApplication) getApplication()).component().inject(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(layoutManager);
        historyRecycler.setHasFixedSize(true);
        adapter = new SearchHistoryAdapter(HistoryStore.ALPHABETICAL_COMPARATOR);
        historyRecycler.setAdapter(adapter);

        RecyclerSupport.addTo(historyRecycler)
                .setOnItemClickListener(this)
//                .setEmptyStateView(emptyHistoryView)
        ;
    }

    // history store gets unregistered onPause.
    // onStoreChange wont be call when returning from PostActivity
    @Override protected void onResume() {
        super.onResume();
        updateList(historyStore.getFilteredEntries());

        if (toolbar.isSearchMode()) {
            KeyboardUtil.show(this);
        }
    }

    @Override public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        searchForPostsWithTag(adapter.getItem(position).getName(), true);
    }

    private void searchForPostsWithTag(String tag, boolean isHistorySearch) {
        startActivity(PostsActivity.getStartIntent(MainSearchActivity.this, tag, isHistorySearch));
    }

    private void setEmptyStateIfNoHistory() {
        emptyHistoryView.setVisibility(historyStore.hasEntries() ? View.GONE : View.VISIBLE);
        historyRecycler.setVisibility(historyStore.hasEntries() ? View.VISIBLE : View.GONE);
    }

    @Subscribe public void onStoreChange(Change change) {
        switch (change.getStoreId()) {
            case HistoryStore.ID:
                switch (change.getAction().getType()) {
                    case Actions.CLEAR_TAG_HISTORY:
                        updateList(new ArrayList<TagHistory>());
//                        setEmptyStateIfNoHistory();
                        break;
                    case Actions.FILTER_HISTORY_TAG:
                        updateList(historyStore.getFilteredEntries());
                        historyRecycler.scrollToPosition(0);
                        break;
                }
                break;
        }
    }

    private void updateList(List<TagHistory> items) {
        adapter.replaceAll(items);
        Timber.i("Updated with %s items: %s", items.size(), items);
    }

    @Subscribe public void onError(ActionError error) {
        Timber.e(error.getThrowable(), "hola hola mamy problem z akcja: %s", error.getActionType());
    }

    @Override public List<? extends Store> getStoresToRegister() {
        return Collections.singletonList(historyStore);
    }

    @Override public void onBackPressed() {
        if (toolbar.isSearchMode()) {
            toolbar.setMode(Normal);
        } else {
            super.onBackPressed();
        }
    }
}