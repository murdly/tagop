package com.akarbowy.tagop.ui.posts.parts.comments.footer;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.SinglePartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CommentFooterPart implements SinglePartDefinition<Comment, CommentFooterView> {
    @Override public int getViewType() {
        return ViewType.COMMENT_FOOTER;
    }

    @Override public Binder<CommentFooterView> createBinder(Comment model) {
        return new CommentFooterBinder(model);
    }

    @Override public boolean isNeeded(Comment model) {
        return true;
    }
}
