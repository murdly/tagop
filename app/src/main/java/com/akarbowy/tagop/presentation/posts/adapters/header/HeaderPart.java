package com.akarbowy.tagop.presentation.posts.adapters.header;

import com.akarbowy.tagop.model.TagEntry;
import com.akarbowy.tagop.presentation.posts.adapters.Binder;
import com.akarbowy.tagop.presentation.posts.adapters.PartDefinition;
import com.akarbowy.tagop.presentation.posts.adapters.ViewType;

public class HeaderPart implements PartDefinition<TagEntry, HeaderView> {
    @Override public ViewType getViewType() {
        return ViewType.HEADER;
    }

    @Override public Binder<HeaderView> createBinder(TagEntry viewObject) {
        return new HeaderBinder(viewObject);
    }

    @Override public boolean isNeeded(TagEntry viewObject) {
        return true;
    }
}
