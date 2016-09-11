package com.akarbowy.tagop.ui.posts.parts.text;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.PartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class TextSectionPart implements PartDefinition<TagEntry, TextSectionView> {
    @Override public int getViewType() {
        return ViewType.TEXT_SECTION;
    }

    @Override public Binder<TextSectionView> createBinder(TagEntry viewObject) {
        return new TextSectionBinder(viewObject);
    }

    @Override public boolean isNeeded(TagEntry viewObject) {
        return true;
    }
}
