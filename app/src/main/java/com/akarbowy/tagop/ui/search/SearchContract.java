package com.akarbowy.tagop.ui.search;


import com.akarbowy.tagop.data.model.TagModel;

import java.util.List;

public class SearchContract {

    interface View {
        void setItems(List<TagModel> items, boolean filtered);

        void showEmptyState();
    }

    interface Presenter {
        void start();

        void filterHistory(String value);

        void clearHistory();

        void removeFromHistory(TagModel tag);
    }
}
