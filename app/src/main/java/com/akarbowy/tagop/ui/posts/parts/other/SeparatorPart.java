package com.akarbowy.tagop.ui.posts.parts.other;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.network.model.TagEntry;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class SeparatorPart implements SinglePartDefinition<TagEntry, SeparatorView> {
    @Override public int getViewType() {
        return ViewType.SEPARATOR;
    }

    @Override public Binder<SeparatorView> createBinder(TagEntry viewObject) {
        return new SeparatorBinder();
    }

    @Override public boolean isNeeded(TagEntry model) {
        return true;
    }
}
