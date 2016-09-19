package com.akarbowy.tagop.ui.posts;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.Keys;
import com.akarbowy.tagop.flux.Action;
import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Dispatcher;
import com.akarbowy.tagop.flux.Store;
import com.akarbowy.tagop.network.model.QueryResult;
import com.akarbowy.tagop.network.model.TagEntry;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import timber.log.Timber;

public class PostStore extends Store {
    public static final String ID = "PostStore";
    private final int firstPageIndex = 1;
    private ArrayList<TagEntry> pageResults;

    private int nextPageIndex = firstPageIndex;

    @Inject public PostStore(Dispatcher dispatcher) {
        super(dispatcher);
        pageResults = new ArrayList<>();
    }

    @Subscribe @Override protected void onAction(Action action) {
        switch (action.getType()) {
            case Actions.SEARCH_TAG:
                QueryResult result = action.get(Keys.QUERY_RESULT);
                pageResults.clear();
                pageResults.addAll(result.entries);

                boolean refreshed = action.get(Keys.FIRST_PAGE);
                nextPageIndex = refreshed ? firstPageIndex + 1 : nextPageIndex + 1;

                break;
            default:
                return;
        }

        postStoreChange(new Change(ID, action));

    }

    public ArrayList<TagEntry> getResults() {
        Timber.i("Page results size %s", pageResults.size());
        return pageResults;
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public int getFirstPageIndex() {
        return firstPageIndex;
    }
}
