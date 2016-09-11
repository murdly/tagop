package com.akarbowy.tagop.ui.posts.parts.text;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;

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
