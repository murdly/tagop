package com.akarbowy.tagop.ui.search;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.Keys;
import com.akarbowy.tagop.flux.Action;
import com.akarbowy.tagop.flux.ActionCreator;
import com.akarbowy.tagop.flux.ActionError;
import com.akarbowy.tagop.flux.Dispatcher;
import com.akarbowy.tagop.data.network.WykopService;
import com.akarbowy.tagop.data.network.model.QueryResult;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TagopActionCreator extends ActionCreator implements Actions {

    private Lazy<WykopService> service;

    @Inject TagopActionCreator(Lazy<WykopService> service, Dispatcher dispatcher) {
        super(dispatcher);
        this.service = service;
    }

    @Override public void searchTag(final String tag, int page) {
        final Action action = newAction(SEARCH_TAG, Keys.QUERY, tag, Keys.FIRST_PAGE, page == 1);

        Callback<QueryResult> callback = new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                if (response.isSuccessful()) {
                    action.getData().put(Keys.QUERY_RESULT, response.body());
                    postAction(action);
                } else {
                    postError(new ActionError(action.getType()));
                }
            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                Timber.e(t, "fail");
                postError(new ActionError(action.getType(), t));
            }
        };

        service.get().search(tag, page).enqueue(callback);
    }

    @Override public void saveTag(String query) {
        postAction(newAction(SAVE_TAG, Keys.QUERY, query));
    }

    @Override public void filterHistory(String query) {
        postAction(newAction(FILTER_HISTORY_TAG, Keys.QUERY, query));
    }

    @Override public void clearHistory() {
        postAction(newAction(CLEAR_TAG_HISTORY));
    }
}