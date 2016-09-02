package com.akarbowy.tagop.presentation.search;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.Keys;
import com.akarbowy.tagop.flux.Action;
import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Dispatcher;
import com.akarbowy.tagop.flux.Store;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

public class HistoryStore extends Store {
    public static final String ID = "HistoryStore";
    public ArrayList<String> queries;

    @Inject public HistoryStore(Dispatcher dispatcher) {
        super(dispatcher);
        queries = new ArrayList<>();
    }

    @Subscribe @Override protected void onAction(Action action) {
        switch (action.getType()) {
            case Actions.SEARCH_TAG:
                String query = action.get(Keys.QUERY);
                queries.add(query);
                break;
            default:
                return;
        }

        postStoreChange(new Change(ID, action));

    }

    public ArrayList<String> getTagNames() {
        return queries;
    }
}
