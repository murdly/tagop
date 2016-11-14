package com.akarbowy.tagop.ui.search;


import com.akarbowy.tagop.data.database.model.TagModel;

import java.util.List;

public class SearchContract {

    interface View {
        void setItems(List<TagModel> items);

        void setState(int state);
    }

    interface Presenter {
        void start();

        void filterHistory(String value);

        void clearHistory();
    }
}
