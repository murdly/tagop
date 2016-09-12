package com.akarbowy.tagop.ui.posts.parts.counters.comments;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.PartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CommentPart implements PartDefinition<Comment, CommentView> {
    @Override public int getViewType() {
        return ViewType.COMMENT;
    }

    @Override public Binder<CommentView> createBinder(Comment viewObject) {
        return new CommentBinder(viewObject);
    }

    @Override public boolean isNeeded(Comment viewObject) {
        return !viewObject.body.isEmpty();
    }
}
