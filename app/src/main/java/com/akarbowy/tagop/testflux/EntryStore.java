package com.akarbowy.tagop.testflux;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.Keys;
import com.akarbowy.tagop.flux.Action;
import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Dispatcher;
import com.akarbowy.tagop.flux.Store;
import com.akarbowy.tagop.model.QueryResult;
import com.akarbowy.tagop.model.TagEntry;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

public class EntryStore extends Store {
    public static final String ID = "EntryStore";
    public ArrayList<TagEntry> entries;

    @Inject
    public EntryStore(Dispatcher dispatcher) {
        super(dispatcher);
        entries = new ArrayList<>();
    }

    @Subscribe
    @Override
    protected void onAction(Action action) {
        switch (action.getType()) {
            case Actions.SEARCH_TAG:
                QueryResult result = action.get(Keys.QUERY_RESULT);
                entries.addAll(result.entries);
                break;
            default:
                return;
        }

        postStoreChange(new Change(ID, action));

    }

    public ArrayList<TagEntry> getEntries() {
        return entries;
    }
}
