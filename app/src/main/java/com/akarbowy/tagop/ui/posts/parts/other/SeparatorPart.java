package com.akarbowy.tagop.ui.posts.parts.other;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.PartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class SeparatorPart implements PartDefinition<TagEntry, SeparatorView> {
    @Override public int getViewType() {
        return ViewType.SEPARATOR;
    }

    @Override public Binder<SeparatorView> createBinder(TagEntry viewObject) {
        return new SeparatorBinder();
    }

    @Override public boolean isNeeded(TagEntry viewObject) {
        return true;
    }
}
