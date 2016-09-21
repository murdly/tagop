package com.akarbowy.tagop.ui.search;

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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class HistoryStore extends Store {
    public static final String ID = "HistoryStore";
    public static final Comparator<TagHistory> ALPHABETICAL_COMPARATOR = new Comparator<TagHistory>() {
        @Override
        public int compare(TagHistory a, TagHistory b) {
            return a.getName().compareTo(b.getName());
        }
    };

    private List<TagHistory> cached;
    private List<TagHistory> workingCopies;
    private Dao<TagHistory, Long> dao;

    @Inject public HistoryStore(Dispatcher dispatcher, DatabaseHelper helper) {
        super(dispatcher);
        try {
            dao = helper.getHistoryDao();
            cached = dao.queryForAll();
            workingCopies = new ArrayList<>(cached);
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
                    if (!cached.contains(entry)) {
                        workingCopies.add(entry);
                        cached.add(entry);
                        dao.create(entry);
                        postStoreChange(new Change(ID, action));
                    }
                } catch (SQLException e) {
                    Timber.i(e.getMessage(), "Error when creating history tags.");
                }
                break;
            case Actions.CLEAR_TAG_HISTORY:
                cached.clear();
                workingCopies.clear();
                try {
                    dao.deleteBuilder().delete();
                    postStoreChange(new Change(ID, action));
                } catch (SQLException e) {
                    Timber.i(e.getMessage(), "Error when deleting history tags.");
                }
                break;
            case Actions.FILTER_HISTORY_TAG:
                String q = action.get(Keys.QUERY);
                workingCopies = filter(q);
                postStoreChange(new Change(ID, action));
                break;
        }
    }

    private List<TagHistory> filter(String query) {
        final String lowerCaseQuery = query.toLowerCase();

        List<TagHistory> filteredList = new ArrayList<>();
        for (TagHistory model : cached) {
            final String text = model.getName().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredList.add(model);
            }
        }
        return filteredList;
    }

    public List<TagHistory> getFilteredEntries() {
        return workingCopies;
    }

    public boolean hasEntries() {
        return !cached.isEmpty();
    }
}
