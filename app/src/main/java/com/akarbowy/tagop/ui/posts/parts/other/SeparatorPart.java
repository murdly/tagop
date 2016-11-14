package com.akarbowy.tagop.ui.posts.parts.other;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class SeparatorPart implements SinglePartDefinition<PostModel, SeparatorView> {
    @Override public int getViewType() {
        return ViewType.SEPARATOR;
    }

    @Override public Binder<SeparatorView> createBinder(PostModel viewObject) {
        return new SeparatorBinder();
    }

    @Override public boolean isNeeded(PostModel model) {
        return true;
    }
}
