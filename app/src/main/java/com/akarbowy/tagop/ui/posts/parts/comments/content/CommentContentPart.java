package com.akarbowy.tagop.ui.posts.parts.comments.content;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.database.model.CommentModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CommentContentPart implements SinglePartDefinition<CommentModel, CommentContentView> {
    @Override public int getViewType() {
        return ViewType.COMMENT_CONTENT;
    }

    @Override public Binder<CommentContentView> createBinder(CommentModel model) {
        return new CommentContentBinder(model);
    }

    @Override public boolean isNeeded(CommentModel model) {
        return !model.body.isEmpty();
    }
}
