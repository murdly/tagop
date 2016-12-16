package com.akarbowy.tagop.ui.posts.parts.header;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class HeaderPart implements SinglePartDefinition<PostModel, HeaderView> {
    @Override public int getViewType() {
        return ViewType.HEADER;
    }

    @Override public Binder<HeaderView> createBinder(PostModel viewObject) {
        return new HeaderBinder(viewObject);
    }

    @Override public boolean isNeeded(PostModel model) {
        return true;
    }
}
