package com.akarbowy.tagop.ui.search;


import com.akarbowy.tagop.data.DataManager;
import com.akarbowy.tagop.data.DataSource;
import com.akarbowy.tagop.data.model.TagModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class SearchPresenter implements SearchContract.Presenter {

    public static final Comparator<TagModel> ALPHABETICAL_COMPARATOR = new Comparator<TagModel>() {
        @Override
        public int compare(TagModel a, TagModel b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    };

    private SearchContract.View view;
    private DataManager repository;

    private List<TagModel> entryCachedTags;

    @Inject public SearchPresenter(SearchContract.View view, DataManager repository) {
        this.view = view;
        this.repository = repository;
        entryCachedTags = new ArrayList<>();
    }

    @Override public void start() {
        entryCachedTags.clear();

        repository.getTags(new DataSource.GetHistoryCallback() {
            @Override public void onDataLoaded(List<TagModel> data) {
                entryCachedTags.addAll(new ArrayList<>(data));
                view.setItems(data, false);
            }

            @Override public void onDataNotAvailable() {
                view.showEmptyState();
            }
        });
    }

    @Override public void filterHistory(String value) {
        List<TagModel> filteredList = new ArrayList<>();
        for (TagModel t : entryCachedTags) {
            final String text = t.getTitle().toLowerCase();
            if (text.contains(value.toLowerCase())) {
                filteredList.add(t);
            }
        }

        view.setItems(filteredList, !value.isEmpty());
    }

    @Override public void clearHistory() {
        entryCachedTags.clear();
        repository.deleteAllTags();

        view.setItems(new ArrayList<TagModel>(), false);
        view.showEmptyState();
    }

    @Override public void removeFromHistory(TagModel tag) {
        repository.deleteTag(tag);
        entryCachedTags.remove(tag);

        view.setItems(entryCachedTags, false);
    }
}
