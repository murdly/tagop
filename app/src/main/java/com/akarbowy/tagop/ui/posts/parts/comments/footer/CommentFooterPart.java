package com.akarbowy.tagop.ui.posts.parts.comments.footer;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.model.CommentModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CommentFooterPart implements SinglePartDefinition<CommentModel, CommentFooterView> {
    @Override public int getViewType() {
        return ViewType.COMMENT_FOOTER;
    }

    @Override public Binder<CommentFooterView> createBinder(CommentModel model) {
        return new CommentFooterBinder(model);
    }

    @Override public boolean isNeeded(CommentModel model) {
        return true;
    }
}
