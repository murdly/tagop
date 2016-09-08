package com.akarbowy.tagop.presentation.posts.adapters.text;

import com.akarbowy.tagop.model.TagEntry;
import com.akarbowy.tagop.presentation.posts.adapters.Binder;

public class TextSectionBinder implements Binder<TextSectionView> {
    private TagEntry tagEntry;

    public TextSectionBinder(TagEntry viewObject) {
        tagEntry = viewObject;
    }

    @Override public void prepare(TextSectionView view) {

    }

    @Override public void bind(TextSectionView view) {
        view.setContent(tagEntry.body);
    }
}
