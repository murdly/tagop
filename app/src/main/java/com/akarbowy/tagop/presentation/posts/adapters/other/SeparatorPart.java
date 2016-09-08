package com.akarbowy.tagop.presentation.posts.adapters.other;

import com.akarbowy.tagop.model.TagEntry;
import com.akarbowy.tagop.presentation.posts.adapters.Binder;
import com.akarbowy.tagop.presentation.posts.adapters.PartDefinition;
import com.akarbowy.tagop.presentation.posts.adapters.ViewType;

public class SeparatorPart implements PartDefinition<TagEntry, SeparatorView> {
    @Override public ViewType getViewType() {
        return ViewType.SEPARATOR;
    }

    @Override public Binder<SeparatorView> createBinder(TagEntry viewObject) {
        return new SeparatorBinder();
    }

    @Override public boolean isNeeded(TagEntry viewObject) {
        return true;
    }
}
