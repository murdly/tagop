package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.database.model.CommentModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CommentImageEmbedPart implements SinglePartDefinition<CommentModel, CommentImageEmbedView> {
    @Override public int getViewType() {
        return ViewType.EMBED_IMAGE;
    }

    @Override public Binder<CommentImageEmbedView> createBinder(CommentModel model) {
        return new CommentImageEmbedBinder(model);
    }

    @Override public boolean isNeeded(CommentModel model) {
        return model.getEmbed().getType().equals(CommentEmbedsPart.IMAGE);
    }
}
