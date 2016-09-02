package com.akarbowy.tagop.presentation.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.R;
import com.akarbowy.tagop.TagopApplication;
import com.akarbowy.tagop.flux.ActionError;
import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Store;
import com.akarbowy.tagop.flux.ViewDispatch;
import com.akarbowy.tagop.utils.KeyboardUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainSearchActivity extends AppCompatActivity implements ViewDispatch {

    private static final long REQUEST_QUERY_DELAY_MS = 600;

    @Inject HistoryStore historyStore;
    @Inject TagopActionCreator creator;

    @BindView(R.id.toolbar_search) Toolbar toolbarView;
    @BindView(R.id.toolbar_layout_descriptive) FrameLayout toolbarDescriptiveLayout;
    @BindView(R.id.toolbar_layout_searchable) FrameLayout toolbarSearchableLayout;
    @BindView(R.id.field_query) EditText queryView;
    @BindView(R.id.button_field_cancel) ImageView queryCancelView;
    @BindView(R.id.recycler_history) RecyclerView historyRecycler;

    private SearchHistoryAdapter adapter;
    private Timer typingTimer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        configureToolbarBehaviour();
        setSupportActionBar(toolbarView);

        ((TagopApplication) getApplication()).component().inject(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(layoutManager);
        adapter = new SearchHistoryAdapter(new ArrayList<String>());
        historyRecycler.setAdapter(adapter);
    }

    private void configureToolbarBehaviour(){
        toolbarView.inflateMenu(R.menu.menu_search);
        toolbarDescriptiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                toolbarDescriptiveLayout.setVisibility(View.GONE);
                toolbarSearchableLayout.setVisibility(View.VISIBLE);
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
                        toolbarDescriptiveLayout.setVisibility(View.VISIBLE);
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
                        return true;
                }

                return false;
            }
        });

        queryView.addTextChangedListener(queryWatcher);
    }

    private static ButterKnife.Setter<View, Boolean> SET_QUERY_CANCEL_VIEW_VISIBILITY = new ButterKnife.Setter<View,Boolean>() {
        @Override public void set(@NonNull View view, Boolean visible, int index) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    };

    @OnClick(R.id.button_field_cancel)
    public void onCancelTypedQuery(){
        queryView.getText().clear();
        ButterKnife.apply(queryCancelView, SET_QUERY_CANCEL_VIEW_VISIBILITY, false);
    }

    private TextWatcher queryWatcher = new TextWatcher() {
        @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override public void onTextChanged(CharSequence text, int i, int i1, int i2) {
            ButterKnife.apply(queryCancelView, SET_QUERY_CANCEL_VIEW_VISIBILITY, !text.toString().isEmpty());

            if (typingTimer != null) {
                typingTimer.cancel();
            }
        }

        @Override public void afterTextChanged(final Editable query) {
            Timber.i("query:%s", getQueryViewText());
            if(query.toString().isEmpty()){
                return;
            }

            typingTimer = new Timer();
            typingTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    String query = queryView.getText().toString().trim();
                    Timber.i("Query searching fired %s", query);
                    creator.searchTag(query);
                }
            }, REQUEST_QUERY_DELAY_MS);
        }
    };

    private String getQueryViewText(){
     return queryView.getText().toString().trim();
    }

    private void refreshHistoryList() {
        ArrayList<String> tags = historyStore.getTagNames();
        adapter.refresh(tags);
    }

    @Subscribe public void onStoreChange(Change change) {
        switch (change.getStoreId()) {
            case HistoryStore.ID:
                switch (change.getAction().getType()) {
                    case Actions.SEARCH_TAG:
                        refreshHistoryList();
                        break;
                }
                break;
        }
    }

    @Subscribe public void onError(ActionError error){
        Timber.e(error.getThrowable(), "hola hola mamy problem z akcja: %s", error.getActionType());
    }

    @Override public List<? extends Store> getStoresToRegister() {
        return Collections.singletonList(historyStore);
    }
}