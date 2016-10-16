package com.akarbowy.tagop.ui.posts.parts.text;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.network.model.TagEntry;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class TextSectionPart implements SinglePartDefinition<TagEntry, TextSectionView> {
    @Override public int getViewType() {
        return ViewType.TEXT_SECTION;
    }

    @Override public Binder<TextSectionView> createBinder(TagEntry viewObject) {
        return new TextSectionBinder(viewObject);
    }

    @Override public boolean isNeeded(TagEntry model) {
        return true;
    }
}
