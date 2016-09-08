package com.akarbowy.tagop.presentation.posts.adapters.text;

import com.akarbowy.tagop.model.TagEntry;
import com.akarbowy.tagop.presentation.posts.adapters.Binder;
import com.akarbowy.tagop.presentation.posts.adapters.PartDefinition;
import com.akarbowy.tagop.presentation.posts.adapters.ViewType;

public class TextSectionPart implements PartDefinition<TagEntry, TextSectionView> {
    @Override public ViewType getViewType() {
        return ViewType.TEXT_SECTION;
    }

    @Override public Binder<TextSectionView> createBinder(TagEntry viewObject) {
        return new TextSectionBinder(viewObject);
    }

    @Override public boolean isNeeded(TagEntry viewObject) {
        return true;
    }
}
