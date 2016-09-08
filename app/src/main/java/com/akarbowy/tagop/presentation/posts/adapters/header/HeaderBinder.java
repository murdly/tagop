package com.akarbowy.tagop.presentation.posts.adapters.header;

import com.akarbowy.tagop.model.TagEntry;
import com.akarbowy.tagop.presentation.posts.adapters.Binder;

public class HeaderBinder implements Binder<HeaderView> {
    private TagEntry tagEntry;

    public HeaderBinder(TagEntry viewObject) {
        tagEntry = viewObject;
    }

    @Override public void prepare(HeaderView view) {

    }

    @Override public void bind(HeaderView view) {
        view.setTitle(tagEntry.author);
        view.setDate(tagEntry.date);
    }
}
