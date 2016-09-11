package com.akarbowy.tagop.ui.posts.parts.header;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.PartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class HeaderPart implements PartDefinition<TagEntry, HeaderView> {
    @Override public int getViewType() {
        return ViewType.HEADER;
    }

    @Override public Binder<HeaderView> createBinder(TagEntry viewObject) {
        return new HeaderBinder(viewObject);
    }

    @Override public boolean isNeeded(TagEntry viewObject) {
        return true;
    }
}
