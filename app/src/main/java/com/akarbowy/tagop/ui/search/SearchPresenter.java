package com.akarbowy.tagop.ui.search;


import com.akarbowy.tagop.data.database.PostsRepository;
import com.akarbowy.tagop.data.database.model.TagModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class SearchPresenter implements SearchContract.Presenter {

    public static final Comparator<TagModel> ALPHABETICAL_COMPARATOR = new Comparator<TagModel>() {
        @Override
        public int compare(TagModel a, TagModel b) {
            return a.getName().compareTo(b.getName());
        }
    };

    private SearchContract.View view;
    private PostsRepository repository;

    private List<TagModel> entryCachedTags;

    @Inject public SearchPresenter(SearchContract.View view, PostsRepository repository) {
        this.view = view;
        this.repository = repository;
        entryCachedTags = new ArrayList<>();
    }

    @Override public void start() {
        entryCachedTags.clear();

        repository.getHistory(new PostsRepository.GetHistoryCallback() {
            @Override public void onDataLoaded(List<TagModel> data) {
                entryCachedTags.addAll(new ArrayList<>(data));
                updateTags(data, false);
            }

            @Override public void onDataNotAvailable() {
                view.setState(MainSearchActivity.State.HISTORY_EMPTY);
            }
        });
    }

    @Override public void filterHistory(String value) {
        List<TagModel> filteredList = new ArrayList<>();
        for (TagModel t : entryCachedTags) {
            final String text = t.getName().toLowerCase();
            if (text.contains(value.toLowerCase())) {
                filteredList.add(t);
            }
        }

        updateTags(filteredList, true);
    }

    private void updateTags(List<TagModel> tags, boolean filtered) {
        view.setItems(tags);

        if (!tags.isEmpty()) {
            view.setState(MainSearchActivity.State.CONTENT);
        } else if (filtered) {
            view.setState(MainSearchActivity.State.FILTER_NO_RESULTS);
        } else {
            view.setState(MainSearchActivity.State.HISTORY_EMPTY);
        }
    }

    @Override public void clearHistory() {
        entryCachedTags.clear();
        repository.deleteSearchHistory();
        view.setState(MainSearchActivity.State.HISTORY_EMPTY);
    }
}
