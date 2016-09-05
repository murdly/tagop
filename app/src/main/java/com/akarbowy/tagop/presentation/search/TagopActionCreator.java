package com.akarbowy.tagop.presentation.search;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.Keys;
import com.akarbowy.tagop.flux.Action;
import com.akarbowy.tagop.flux.ActionCreator;
import com.akarbowy.tagop.flux.Dispatcher;
import com.akarbowy.tagop.flux.ActionError;
import com.akarbowy.tagop.model.QueryResult;
import com.akarbowy.tagop.network.WykopService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TagopActionCreator extends ActionCreator implements Actions {

    private WykopService service;

    @Inject
    public TagopActionCreator(WykopService service, Dispatcher dispatcher) {
        super(dispatcher);
        this.service = service;
    }

    @Override
    public void searchTag(final String query) {
        final Action action = newAction(SEARCH_TAG, Keys.QUERY, query);

        service.search(query, 1)
                .enqueue(new Callback<QueryResult>() {
                    @Override
                    public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                        if (response.isSuccessful()) {
                            action.getData().put(Keys.QUERY_RESULT, response.body());
                            postAction(action);
                        }else{
                            postError(new ActionError(action.getType()));
                        }
                    }

                    @Override
                    public void onFailure(Call<QueryResult> call, Throwable t) {
                        Timber.e("fail", t);
                        postError(new ActionError(action.getType(), t));
                    }
                });
    }

    public void filterHistory(String query) {
        postAction(newAction(FILTER_HISTORY_TAG, Keys.QUERY, query));
    }

    public void clearHistory() {
        postAction(newAction(CLEAR_TAG_HISTORY));
    }
}