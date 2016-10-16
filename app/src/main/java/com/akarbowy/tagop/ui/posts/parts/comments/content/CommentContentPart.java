package com.akarbowy.tagop.ui.posts.parts.comments.content;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.network.model.Comment;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CommentContentPart implements SinglePartDefinition<Comment, CommentContentView> {
    @Override public int getViewType() {
        return ViewType.COMMENT_CONTENT;
    }

    @Override public Binder<CommentContentView> createBinder(Comment model) {
        return new CommentContentBinder(model);
    }

    @Override public boolean isNeeded(Comment model) {
        return !model.body.isEmpty();
    }
}
