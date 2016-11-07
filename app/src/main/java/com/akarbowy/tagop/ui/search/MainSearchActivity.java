package com.akarbowy.tagop.ui.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.data.database.TagHistory;
import com.akarbowy.tagop.ui.posts.PostsActivity;
import com.akarbowy.tagop.ui.search.SearchableToolbarView.Mode;
import com.akarbowy.tagop.utils.DeprecatedHelper;
import com.akarbowy.tagop.utils.KeyboardUtil;
import com.akarbowy.tagop.utils.RecyclerSupport;
import com.akarbowy.tagop.utils.StateSwitcher;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.akarbowy.tagop.ui.search.SearchableToolbarView.Mode.Normal;

public class MainSearchActivity extends AppCompatActivity implements RecyclerSupport.OnItemClickListener {

    HistoryStore historyStore;

    @BindView(R.id.toolbar_action_view) SearchableToolbarView toolbar;
    @BindView(R.id.recycler_history) RecyclerView historyRecycler;
    @BindView(R.id.filter_query_param) TextView filterQueryParam;
    @BindViews({R.id.state_history_empty, R.id.state_history_empty_filter}) List<View> stateViews;

    private SearchHistoryAdapter adapter;
    private StateSwitcher stateSwitcher;

    private SearchableToolbarView.Callback toolbarActionsCallback = new SearchableToolbarView.Callback() {
        @Override public void onSearchPerform(String query) {
            searchForPostsWithTag(query, false); //false but it might be history
        }

        @Override public void onQueryTextChange(String queryText) {
            filterQueryParam.setTag(queryText);
            filterQueryParam.setText(DeprecatedHelper.fromHtml(getString(R.string.state_history_empty_filter_instruction, queryText)));
//            creator.filterHistory(queryText);
        }

        @Override public void onMenuClearHistoryClick() {
//            creator.clearHistory();
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        toolbar.setCallback(toolbarActionsCallback);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(layoutManager);
        historyRecycler.setHasFixedSize(true);
        adapter = new SearchHistoryAdapter(HistoryStore.ALPHABETICAL_COMPARATOR);
        historyRecycler.setAdapter(adapter);

        stateSwitcher = new StateSwitcher();
        stateSwitcher.setViews(stateViews, new int[]{State.HISTORY_EMPTY, State.FILTER_NO_RESULTS}); //recycler always visible for smoother filters

        RecyclerSupport.addTo(historyRecycler)
                .setOnItemClickListener(this);
        historyRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState != RecyclerView.SCROLL_STATE_IDLE ){
                    KeyboardUtil.hide(recyclerView);
                }
            }
        });
    }

    @Override protected void onResume() {
        super.onResume();

        updateItemsAndState();

        if (toolbar.getMode() == Mode.Search) {
            KeyboardUtil.show(this);
        }
    }

    @Override public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        searchForPostsWithTag(adapter.getItem(position).getName(), true);
    }

    @OnClick(R.id.filter_query_param) public void onFilterNoResultsStateViewClick() {
        searchForPostsWithTag((String) filterQueryParam.getTag(), false);
    }

    private void searchForPostsWithTag(String query, boolean isHistorySearch) {
//        creator.saveTag(query);
        startActivity(PostsActivity.getStartIntent(MainSearchActivity.this, query, isHistorySearch));
    }

//    @Subscribe public void onStoreChange(Change change) {
//        switch (change.getStoreId()) {
//            case HistoryStore.ID:
//                switch (change.getAction().getType()) {
//                    case Actions.CLEAR_TAG_HISTORY:
//                        updateItemsAndState();
//                        break;
//                    case Actions.FILTER_HISTORY_TAG:
//                        updateItemsAndState();
//                        historyRecycler.scrollToPosition(0);
//                        break;
//                }
//                break;
//        }
//    }

    private void updateItemsAndState() {
        List<TagHistory> items = historyStore.getFilteredEntries();
        int state = historyStore.resolveState();

        adapter.replaceAll(items);
        stateSwitcher.setState(state);

        Timber.i("State: %s, updated with %s items: %s", state, items.size(), items);
    }

    @Override public void onBackPressed() {
        if (toolbar.getMode() == Mode.Search) {
            toolbar.setMode(Normal);
        } else {
            super.onBackPressed();
        }
    }

    public interface State {
        int CONTENT = 0;
        int HISTORY_EMPTY = 1;
        int FILTER_NO_RESULTS = 2;
    }
}