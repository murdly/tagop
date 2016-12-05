package com.akarbowy.tagop.ui.search;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.TagopApplication;
import com.akarbowy.tagop.data.database.model.TagModel;
import com.akarbowy.tagop.ui.posts.PostsActivity;
import com.akarbowy.tagop.ui.search.SearchableToolbarView.Mode;
import com.akarbowy.tagop.utils.DeprecatedHelper;
import com.akarbowy.tagop.utils.KeyboardUtil;
import com.akarbowy.tagop.utils.RecyclerSupport;
import com.akarbowy.tagop.utils.StateSwitcher;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.akarbowy.tagop.ui.search.SearchableToolbarView.Mode.Normal;

public class MainSearchActivity extends AppCompatActivity implements SearchContract.View, RecyclerSupport.OnItemClickListener, RecyclerSupport.OnItemLongClickListener {

    @BindView(R.id.toolbar_action_view) SearchableToolbarView toolbar;
    @BindView(R.id.recycler_history) RecyclerView historyRecycler;
    @BindView(R.id.filter_query_param) TextView filterQueryParam;
    @BindViews({R.id.state_history_empty, R.id.state_history_empty_filter}) List<View> stateViews;
    @BindString(R.string.dialog_msg_delete) String deleteMsgString;
    @BindString(R.string.dialog_button_ok) String okButtonString;

    @Inject SearchPresenter presenter;

    private SearchHistoryAdapter adapter;
    private StateSwitcher stateSwitcher;

    private SearchableToolbarView.Callback toolbarActionsCallback = new SearchableToolbarView.Callback() {
        @Override public void onSearchPerform(String query) {
            searchForPostsWithTag(query);
        }

        @Override public void onQueryTextChange(String queryText) {
            filterQueryParam.setTag(queryText);
            filterQueryParam.setText(DeprecatedHelper.fromHtml(getString(R.string.state_history_empty_filter_instruction, queryText)));
            presenter.filterHistory(queryText);
        }

        @Override public void onMenuClearHistoryClick() {
            presenter.clearHistory();
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
        adapter = new SearchHistoryAdapter(SearchPresenter.ALPHABETICAL_COMPARATOR);
        historyRecycler.setAdapter(adapter);

        stateSwitcher = new StateSwitcher();
        stateSwitcher.setViews(stateViews, new int[]{State.HISTORY_EMPTY, State.FILTER_NO_RESULTS}); //recycler always visible for smoother filters

        RecyclerSupport.addTo(historyRecycler)
                .setOnItemClickListener(this)
                .setOnItemLongClickListener(this);

        historyRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    KeyboardUtil.hide(recyclerView);
                }
            }
        });

        DaggerSearchComponent.builder()
                .searchModule(new SearchModule(this))
                .applicationComponent(((TagopApplication) getApplication()).component())
                .build()
                .inject(this);


    }

    @Override protected void onResume() {
        super.onResume();

        presenter.start();

        if (toolbar.getMode() == Mode.Search) {
            presenter.filterHistory((String) filterQueryParam.getTag());
            KeyboardUtil.show(this);
        }
    }

    @Override public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        searchForPostsWithTag(adapter.getItem(position).getTitle());
    }

    @Override public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
        new AlertDialog.Builder(this)
                .setMessage(deleteMsgString)
                .setPositiveButton(okButtonString, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                presenter.removeFromHistory(adapter.getItem(position));
            }
        }).show();

        return true;

    }

    @OnClick(R.id.filter_query_param) public void onFilterNoResultsStateViewClick() {
        searchForPostsWithTag((String) filterQueryParam.getTag());
    }

    private void searchForPostsWithTag(String query) {
        startActivity(PostsActivity.getStartIntent(this, query));
    }

    @Override public void setItems(List<TagModel> items) {
        adapter.replaceAll(items);
        historyRecycler.scrollToPosition(0);

        Timber.i("Updated with %s items: %s", items.size(), items);
    }

    @Override public void setState(int state) {
        stateSwitcher.setState(state);

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