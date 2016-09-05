package com.akarbowy.tagop.presentation.search;

import com.akarbowy.tagop.Actions;
import com.akarbowy.tagop.Keys;
import com.akarbowy.tagop.database.DatabaseHelper;
import com.akarbowy.tagop.database.TagHistory;
import com.akarbowy.tagop.flux.Action;
import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Dispatcher;
import com.akarbowy.tagop.flux.Store;
import com.j256.ormlite.dao.Dao;
import com.squareup.otto.Subscribe;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class HistoryStore extends Store {
    public static final String ID = "HistoryStore";
    private List<TagHistory> queries;
    private Dao<TagHistory, Long> dao;

    @Inject public HistoryStore(Dispatcher dispatcher, DatabaseHelper helper) {
        super(dispatcher);
        try {
            dao = helper.getHistoryDao();
            queries = dao.queryForAll();
        } catch (SQLException e) {
            Timber.i(e.getMessage(), "Error when quering for history tags.");
        }

    }

    @Subscribe @Override protected void onAction(Action action) {
        switch (action.getType()) {
            case Actions.SEARCH_TAG:
                String query = action.get(Keys.QUERY);
                TagHistory entry = new TagHistory(query);
                try {
                    if(!queries.contains(entry)){
                        queries.add(entry);
                        dao.create(entry);
                        postStoreChange(new Change(ID, action));
                    }
                } catch (SQLException e) {
                    Timber.i(e.getMessage(), "Error when creating history tags.");
                }
                break;
            case Actions.CLEAR_TAG_HISTORY:
                queries.clear();
                try {
                    dao.deleteBuilder().delete();
                    postStoreChange(new Change(ID, action));
                } catch (SQLException e) {
                    Timber.i(e.getMessage(), "Error when deleting history tags.");
                }
        }
    }

    public List<TagHistory> getTagNames() {
        return queries;
    }
}
