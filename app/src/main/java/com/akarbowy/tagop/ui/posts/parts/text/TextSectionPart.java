package com.akarbowy.tagop.ui.posts.parts.text;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class TextSectionPart implements SinglePartDefinition<PostModel, TextSectionView> {
    @Override public int getViewType() {
        return ViewType.TEXT_SECTION;
    }

    @Override public Binder<TextSectionView> createBinder(PostModel viewObject) {
        return new TextSectionBinder(viewObject);
    }

    @Override public boolean isNeeded(PostModel model) {
        return true;
    }
}
