package com.akarbowy.tagop.ui.posts.parts.comments.content;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.SinglePartDefinition;
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
