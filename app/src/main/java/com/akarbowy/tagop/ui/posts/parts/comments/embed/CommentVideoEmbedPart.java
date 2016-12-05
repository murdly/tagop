package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.database.model.CommentModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CommentVideoEmbedPart implements SinglePartDefinition<CommentModel, CommentVideoEmbedView> {
    @Override public int getViewType() {
        return ViewType.EMBED_VIDEO;
    }

    @Override public Binder<CommentVideoEmbedView> createBinder(CommentModel model) {
        return new CommentVideoEmbedBinder(model);
    }

    @Override public boolean isNeeded(CommentModel model) {
        return model.getEmbed().getType().equals(CommentEmbedsPart.VIDEO);
    }
}